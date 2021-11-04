package net.umaass_user.app.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;

import net.umaass_user.app.R;
import net.umaass_user.app.utils.TouchImageView;

import java.util.ArrayList;
import java.util.List;

public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
    List<String> mResources = new ArrayList<>();

    public CustomPagerAdapter(Context context, List<String> imageAddresses) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mResources = imageAddresses;
    }

    @Override
    public int getCount() {
        return mResources.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.cell_album, container, false);
        TouchImageView imageView = itemView.findViewById(R.id.img);
        Picasso.get()
               .load(mResources.get(position) == null ? "ddd" : mResources.get(position))
               //  .transform(new RoundedCornersTransformation(Utils.dp(8), 0))
               .placeholder(R.drawable.holder)
               .error(R.drawable.holder)
               .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((TouchImageView) object);
    }


}
