package com.goayo.debtify.view;

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
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.view.adapter.GroupViewAdapter;
import com.goayo.debtify.viewmodel.MyGroupsViewModel;

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
 *
 * 2020/10/08 Modified by Alex Phu: Injected currentLoggedInUsersPhoneNumber to GroupViewAdapter.
 * 2020-10-12 Modified by Alex Phu: Implemented RefreshLayout, to be able to fetch new data from database.
 */
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

        final GroupViewAdapter groupViewAdapter = new GroupViewAdapter(groupData, viewModel.getCurrentLoggedInUsersPhoneNumber());
        initRecyclerView(groupViewAdapter);

        //Updates RecyclerView when LiveData is changed.
        viewModel.getGroupsData().observe(getViewLifecycleOwner(), new Observer<Set<IGroupData>>() {
            @Override
            public void onChanged(Set<IGroupData> iGroupData) {
                groupViewAdapter.update(new ArrayList<IGroupData>(iGroupData));
            }
        });
        //Fetches clicked group and sends it away.
        groupViewAdapter.setCommonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                groupCardViewOnClick(groupViewAdapter);
            }
        });

        initRefreshLayout(viewModel);

        return binding.getRoot();
    }

    private void groupCardViewOnClick(GroupViewAdapter groupViewAdapter) {
        Intent intent = new Intent(getContext(), DetailedGroupActivity.class);
        intent.putExtra("GROUP_ID", groupViewAdapter.getClickedGroup().getGroupID());
        startActivity(intent);
    }

    /**
     * Initializes RecyclerView in MyGroups.
     *
     */
    private void initRecyclerView(GroupViewAdapter groupViewAdapter) {
        RecyclerView recyclerView = binding.groupRecyclerView;
        recyclerView.setAdapter(groupViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
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

    private void initRefreshLayout(MyGroupsViewModel viewModel){
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
