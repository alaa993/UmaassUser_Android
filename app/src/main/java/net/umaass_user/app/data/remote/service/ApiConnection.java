package net.umaass_user.app.data.remote.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.umaass_user.app.application.BuildVars;
import net.umaass_user.app.application.G;
import net.umaass_user.app.application.Preference;
import net.umaass_user.app.data.MockServiceData;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

public class ApiConnection {

    private static volatile ApiConnection INSTANCE;
    private ServiceApi serviceApi;
    private Retrofit retrofit;
    private MockServiceData serviceData;

    private long connectTimeout;
    private long readTimeout;
    private long writeTimeout;

    /*public static ApiConnection getInstance(long connectTimeout, long readTimeout, long writeTimeout) {
        if (INSTANCE == null) {
            synchronized (ApiConnection.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ApiConnection(connectTimeout, readTimeout, writeTimeout);
                }
            }
        }
        return INSTANCE;
    }*/

    public ApiConnection(long connectTimeout, long readTimeout, long writeTimeout) {
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        this.writeTimeout = writeTimeout;
        retrofit = create();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public ServiceApi getService() {
        if (serviceApi == null) {
            NetworkBehavior behavior = NetworkBehavior.create();
            MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                    .networkBehavior(behavior)
                    .build();

            BehaviorDelegate<ServiceApi> delegate = mockRetrofit.create(ServiceApi.class);
            MockServiceData.MockService service = new MockServiceData.MockService(delegate);
            behavior.setDelay(1000, TimeUnit.MILLISECONDS);

            serviceApi = retrofit.create(ServiceApi.class)/*service*/;
        }
        return serviceApi;
    }

    private Retrofit create() {
        int cacheSize = 20 * 1024 * 1024;
        Cache cache = new Cache(G.getInstance().getCacheDir(), cacheSize);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        if (BuildVars.DEBUG_VERSION) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client.addInterceptor(loggingInterceptor);
        }

        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                                          .addHeader("Authorization", "Bearer " + Preference.getToken())
                                          .addHeader("Content-Type", "application/json")
                                          .addHeader("Accept", "application/json")
                                          .build();
                return chain.proceed(newRequest);
            }
        });

        client.connectTimeout(connectTimeout, TimeUnit.SECONDS)
              .readTimeout(readTimeout, TimeUnit.SECONDS)
              .writeTimeout(writeTimeout, TimeUnit.SECONDS)
              .retryOnConnectionFailure(true)
              .cache(cache)
              .build();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();
        retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(BuildVars.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit;

    }


    public void clear() {
        INSTANCE = null;
        retrofit = null;
        serviceApi = null;
    }
}
