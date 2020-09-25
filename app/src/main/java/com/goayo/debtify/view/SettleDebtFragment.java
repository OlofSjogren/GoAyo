package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.SettleDebtFragmentBinding;
import com.goayo.debtify.model.ModelEngine;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for paying off debt.
 *
 * 2020-09-18 Modified by Yenan & Gabriel: Added SettleDebt view, tested with hard-coded value
 */
public class SettleDebtFragment extends Fragment {

    private SettleDebtFragmentBinding binding;
    private ModelEngine model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.settle_debt_fragment, container, false);
        model = ModelEngine.getInstance();

        

        return binding.getRoot();
    }
}
