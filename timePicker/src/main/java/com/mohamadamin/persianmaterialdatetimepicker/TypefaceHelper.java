package com.mohamadamin.persianmaterialdatetimepicker;


import android.content.Context;
import android.graphics.Typeface;
import androidx.collection.SimpleArrayMap;

/*
    Each call to Typeface.createFromAsset will load a new instance of the typeface into memory,
    and this memory is not consistently get garbage collected
    http://code.google.com/p/android/issues/detail?id=9904
    (It states released but even on Lollipop you can see the typefaces accumulate even after
    multiple GC passes)
    You can detect this by running:
    adb shell dumpsys meminfo com.your.packagenage
    You will see output like:
     Asset Allocations
*/
public class TypefaceHelper {

    private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

    public static Typeface get(Context c, String name) {
        synchronized (cache) {
            if (!cache.containsKey(name)) {
                Typeface t = Typeface.createFromAsset(
                        c.getAssets(), String.format("fonts/iran-sans-light_baz.ttf", name));
                cache.put(name, t);
                return t;
            }
            return cache.get(name);
        }
    }
}
