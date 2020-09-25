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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.AddDebtFragmentBinding;
import com.goayo.debtify.databinding.PickUsersFragmentBinding;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.PickUserAdapter;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;

import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for adding a debt to a group.
 * <p>
 * 2020-09-18 Modified by Yenan & Gabriel: Added AddDebt view, tested with hard-coded value
 * 2020-09-22 Modified by Yenan & Gabriel: Removed hard-coded values, added defaults.
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

    //TODO: Change implementation. Only made to get defaults values.
    private void initRecyclerView(Set<IUserData> userData) {
        RecyclerView borrowerRecyclerView = binding.borrowerRecyclerView;
        RecyclerView lenderRecyclerView = binding.lenderRecyclerView;

        UserCardViewAdapter borrowerAdapter = new UserCardViewAdapter("Select borrower", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickUser();
            }
        });
        borrowerRecyclerView.setAdapter(borrowerAdapter);
        borrowerRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));

        UserCardViewAdapter lenderAdapter = new UserCardViewAdapter("Select lender", "", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickUser();
            }
        });
        lenderRecyclerView.setAdapter(lenderAdapter);
        lenderRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }


    /**
     * Navigates to the PickUsersFragment to select User as lender or borrower.
     */
    private void openPickUser() {
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_addDebtFragment_to_pickUsersFragment);
    }
}
