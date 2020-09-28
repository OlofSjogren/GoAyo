package com.goayo.debtify.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.GroupFragmentBinding;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.view.adapter.TransactionCardAdapter;
import com.goayo.debtify.viewModel.GroupViewModelFactory;
import com.goayo.debtify.viewModel.GroupsViewModel;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign up page.
 * <p>
 * 2020-09-22 Modified by Oscar Sanner and Alex Phu: Added binding methods
 * and initializer for recycler view.
 * <p>
 * 2020-09-23 Modified by Alex: Added fetchDebtData method. Will probably be changed later depending on
 * if IDebtData's type will be changed to Set from List.
 * <p>
 * 25-09-2020 Modified by Alex: Refactored bottom-buttons to GroupFragment from DetailedGroupActivity.
 * <p>
 * 25-09-2020 Modified by Alex Phu, Oscar Sanner, Olof Sjögren: Setup communication with ViewModel instead ModelEngine.
 * <p>
 * 2020/09/25 Modified bt Oscar Sanner, Alex Phu and Olof Sjögren: Added factory to ViewModelProvider.
 */
public class GroupFragment extends Fragment {

    private GroupsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GroupFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.group_fragment, container, false);

        viewModel = ViewModelProviders.of(this, new GroupViewModelFactory()).get(GroupsViewModel.class);
        IGroupData groupData = viewModel.getCurrentGroupData().getValue();

        initTextViews(binding, groupData);
        initBottomNavigation(binding);
        initRecyclerView(binding, viewModel.getDebt().getValue());

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void initTextViews(GroupFragmentBinding binding, IGroupData group) {
        binding.detailedGroupGroupNameTextView.setText(group.getGroupName());

        try {
            //TODO change dependency on ModelEngine to dependency on a "UserViewModel".
            binding.detailedGroupBalanceTextView.setText(group.getUserTotal(ModelEngine.getInstance().getLoggedInUser().getPhoneNumber()) + "kr");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initRecyclerView(GroupFragmentBinding binding, IDebtData[] debtData) {
        RecyclerView recyclerView = binding.detailedGroupRecyclerView;
        TransactionCardAdapter adapter = new TransactionCardAdapter(getContext(), debtData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private void initBottomNavigation(GroupFragmentBinding binding) {
        //Navigation
        binding.detailedGroupAddDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DebtActivity.class).putExtra("DEBT_CREATE", "ADD_DEBT");
                startActivity(intent);
            }
        });
        //TODO: Refactor name to "Settle Debt" instead of "Add Payment"
        binding.detailedGroupAddPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), DebtActivity.class).putExtra("DEBT_CREATE", "SETTLE_DEBT");
                startActivity(intent);
            }
        });
    }

    //TODO ("Keep depending on if IDebtData will be kept as a Set")
    /*
    private IDebtData[] convertSetToArray(Set<IDebtData> debtDataSet) {
        IDebtData[] tempData = new IDebtData[debtDataSet.size()];
        debtDataSet.toArray(tempData);
        return tempData;
    }*/
}
