package com.goayo.debtify.view.factory;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.goayo.debtify.view.ActivityFragment;
import com.goayo.debtify.view.MyGroupsFragment;
import com.goayo.debtify.view.HistoryFragment;

/**
 * @author Alex Phu, Yenan Wang
 * <p>
 * Factory class for the main three fragments.
 */
public class TabsFactory {

    /**
     * Create fragment for the main tabs.
     *
     * @param index Tab position.
     * @return Return a new instance of the corresponding fragment.
     */
    public static Fragment createTabs(int index) {
        switch (index) {
            case 0:
                return new ActivityFragment();
            case 1:
                return new MyGroupsFragment();
            case 2:
                return new HistoryFragment();
            default:
                Log.d("TabsFactory", "Illegal argument in function instantiate");
                throw ((new IllegalArgumentException()));
        }
    }
}
