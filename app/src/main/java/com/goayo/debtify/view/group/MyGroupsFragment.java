package com.goayo.debtify.view.group;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.MyGroupsFragmentBinding;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IGroupData;
import com.goayo.debtify.view.adapter.GroupViewAdapter;
import com.goayo.debtify.viewmodel.MyGroupsViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Second tab of the main screen.
 * <p>
 * 2020/09/15 Modified by Alex Phu. Added init function for RecyclerView. Will be activated when backend is resolved.
 * <p>
 * 2020/09/16 Modified by Alex Phu. Added listener for FloatingActionButton.
 * <p>
 * 2020/09/25 Modified by Oscar Sanner, Alex Phu and Olof Sjögren: Added factory to ViewModelProvider.
 * <p>
 * 2020/09/30 Modified by Alex Phu and Yenan Wang: Refactored entire class.
 * <p>
 * 2020/10/08 Modified by Alex Phu: Injected currentLoggedInUsersPhoneNumber to GroupViewAdapter.
 * <p>
 * 2020/10/12 Modified by Olof Sjögren: Created initHeader() for initializing header name, phone number and total debt.
 * 2020-10-12 Modified by Alex Phu: Implemented RefreshLayout, to be able to fetch new data from database.
 * <p>
 * 2020/10/12 Modified by Olof Sjögren: Created initHeader() for initializing header name, phone number and total debt.
 * <p>
 * 2020-10-12 Modified by Alex Phu: Implemented RefreshLayout, to be able to fetch new data from database.
 * <p>
 * 2020-10-13 Modified by Alex Phu: Made RecyclerView unfocusable.
 **/

public class MyGroupsFragment extends Fragment {
    MyGroupsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Binding instead of relying on findViewById
        binding = DataBindingUtil.inflate(inflater, R.layout.my_groups_fragment, container, false);
        addListenerToFloatingActionButton();

        MyGroupsViewModel viewModel = ViewModelProviders.of(this).get(MyGroupsViewModel.class);

        List<IGroupData> groupData = new ArrayList<>(viewModel.getGroupsData().getValue());

        final GroupViewAdapter groupViewAdapter = new GroupViewAdapter(groupData, viewModel.getLoggedInUser().getPhoneNumber());
        initRecyclerView(groupViewAdapter);

        //Updates RecyclerView when LiveData is changed.
        viewModel.getGroupsData().observe(getViewLifecycleOwner(), new Observer<Set<IGroupData>>() {
            @Override
            public void onChanged(Set<IGroupData> iGroupData) {
                groupViewAdapter.update(new ArrayList<>(iGroupData));
                initHeader(binding, viewModel);
            }
        });
        //Fetches clicked group and sends it away.
        groupViewAdapter.setCommonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCardViewOnClick(groupViewAdapter);
            }
        });

        initHeader(binding, viewModel);
        initRefreshLayout(viewModel);

        return binding.getRoot();
    }

    private void groupCardViewOnClick(GroupViewAdapter groupViewAdapter) {
        Intent intent = new Intent(getContext(), DetailedGroupActivity.class);
        intent.putExtra("GROUP_ID", groupViewAdapter.getClickedGroup().getGroupID());
        startActivity(intent);
    }


    private void initHeader(MyGroupsFragmentBinding binding, MyGroupsViewModel viewModel) {
        StringBuilder sb = new StringBuilder();
        sb.append(viewModel.getLoggedInUser().getPhoneNumber());
        sb.insert(7, " ");
        sb.insert(5, " ");
        sb.insert(3, "-");

        binding.welcomeBannerNameTextView.setText(viewModel.getLoggedInUser().getName());
        binding.welcomeBannerPhoneNumberTextView.setText(sb.toString());

        BigDecimal total = new BigDecimal(0);
        for (IGroupData g : viewModel.getGroupsData().getValue()) {
            try {
                total = total.add(g.getUserTotal(viewModel.getLoggedInUser().getPhoneNumber()));
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }

        switch (total.compareTo(new BigDecimal(0))) {
            case 0:
                binding.totalBalanceTextView.setTextColor(binding.totalBalanceTextView.getResources().getColor(R.color.dividerGrey));
                break;
            case -1:
                binding.totalBalanceTextView.setTextColor(binding.totalBalanceTextView.getResources().getColor(R.color.negativeDebtRed));
                break;
            case 1:
                binding.totalBalanceTextView.setTextColor(binding.totalBalanceTextView.getResources().getColor(R.color.positiveDebtGreen));
                break;
        }

        binding.totalBalanceTextView.setText(total.toString() + " kr");
    }


    /**
     * Initializes RecyclerView in MyGroups.
     */
    private void initRecyclerView(GroupViewAdapter groupViewAdapter) {
        RecyclerView recyclerView = binding.groupRecyclerView;
        recyclerView.setAdapter(groupViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.groupRecyclerView.setFocusable(false);
    }

    /**
     * Sets listener to the floatingActionButton.
     */
    private void addListenerToFloatingActionButton() {
        binding.addGroupFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(getContext(), GroupCreationActivity.class);
                startActivity(createGroupIntent);
            }
        });
    }

    private void initRefreshLayout(MyGroupsViewModel viewModel) {
        //Refreshlayout
        binding.myGroupsRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.updateGroupsFromDatabase();
                binding.myGroupsRefreshLayout.setRefreshing(false);
            }
        });
    }
}
