package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.AddDebtFragmentBinding;
import com.goayo.debtify.model.ModelEngine;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for adding a debt to a group.
 * <p>
 * 2020-09-18 Modified by Yenan & Gabriel: Added AddDebt view, tested with hard-coded value
 * 2020-09-22 Modified by Yenan & Gabriel: Removed hard-coded values
 */
public class AddDebtFragment extends Fragment {

    private AddDebtFragmentBinding binding;
    private ModelEngine model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_debt_fragment, container, false);
        model = ModelEngine.getInstance();

        return binding.getRoot();
    }

    private void openPickUser() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_addDebtFragment_to_pickUsersFragment);
    }
}
