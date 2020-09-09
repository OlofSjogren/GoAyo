package com.goayo.debtify.view.handler;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.goayo.debtify.R;
import com.goayo.debtify.view.factory.TabsFactory;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * * one of the tabs.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public TabsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     *
     * @param position Tab position.
     * @return Returns an instance of the fragment.
     */
    @Override
    public Fragment getItem(int position) {
        return TabsFactory.createTabs(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}