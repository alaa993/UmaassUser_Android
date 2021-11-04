package net.umaass_user.app.ui;

import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import net.umaass_user.app.R;
import net.umaass_user.app.ui.adapter.CustomPagerAdapter;
import net.umaass_user.app.ui.base.BaseActivity;
import net.umaass_user.app.utils.Utils;

import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class ActivityShowImage extends BaseActivity {

    ViewPager viewPager;
    private CustomPagerAdapter adapter;
    private List<String> images;
    private CircleIndicator stepper;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.setFillWindowAndTransparetStatusBar(this);
        setContentView(R.layout.activity_show_image);
        images = getIntent().getStringArrayListExtra("list");
        index = getIntent().getIntExtra("index", 0);
        readView();
        functionView();
        initViewModel();
    }

    @Override
    public void readView() {
        super.readView();
        viewPager = findViewById(R.id.viewPager);
        stepper = findViewById(R.id.stepper);
    }

    @Override
    public void functionView() {
        super.functionView();
        adapter = new CustomPagerAdapter(this, images);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(index);
        stepper.setViewPager(viewPager);
    }


}
