package net.umaass_user.app.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import net.umaass_user.app.ui.FragmentBooksDeleted;
import net.umaass_user.app.ui.FragmentBooksFuture;
import net.umaass_user.app.ui.FragmentBooksPast;

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentBooksFuture();
            case 1:
                return new FragmentBooksPast();
            case 2:
                return new FragmentBooksDeleted();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}