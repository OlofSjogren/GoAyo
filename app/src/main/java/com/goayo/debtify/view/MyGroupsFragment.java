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
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.MyGroupsFragmentBinding;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.view.adapter.GroupViewAdapter;
import com.goayo.debtify.viewmodel.GroupViewModelFactory;
import com.goayo.debtify.viewmodel.MyGroupsViewModel;

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
 */
public class MyGroupsFragment extends Fragment {

    MyGroupsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Binding instead of relying on findViewById
        MyGroupsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.my_groups_fragment, container, false);
        addListenerToFloatingActionButton(binding);
        viewModel = ViewModelProviders.of(this, new GroupViewModelFactory()).get(MyGroupsViewModel.class);
        initRecyclerView(binding, viewModel.getAllGroupsData().getValue());
        return binding.getRoot();
    }

    /**
     * Initializes RecyclerView in MyGroups.
     *
     * @param groupData Set of groupdata to be displayed.
     * @param binding   Variable which can access the elements in the layout file.
     */
    private void initRecyclerView(MyGroupsFragmentBinding binding, Set<IGroupData> groupData) {
        RecyclerView recyclerView = binding.groupRecyclerView;
        GroupViewAdapter groupViewAdapter = new GroupViewAdapter(getContext(), convertSetToArray(groupData), viewModel);
        recyclerView.setAdapter(groupViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * Sets listener to the floatingActionButton.
     *
     * @param binding Variable which can access the elements in the layout file.
     */
    private void addListenerToFloatingActionButton(MyGroupsFragmentBinding binding) {
        binding.addGroupFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createGroupIntent = new Intent(getContext(), GroupCreationActivity.class);
                startActivity(createGroupIntent);
            }
        });
    }

    /**
     * Converts a Set to an Array.
     *
     * @param groupDataSet Set of Groups.
     * @return Returns an Array of Groups.
     */
    private IGroupData[] convertSetToArray(Set<IGroupData> groupDataSet) {
        IGroupData[] tempData = new IGroupData[groupDataSet.size()];
        groupDataSet.toArray(tempData);
        return tempData;
    }
}
