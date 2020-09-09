package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.goayo.debtify.R;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for paying off debt.
 */
public class SettleUpFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.settle_up_fragment, container, false);
    }
}
