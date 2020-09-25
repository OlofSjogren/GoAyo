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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityDetailedGroupBinding;
import com.goayo.debtify.databinding.GroupFragmentBinding;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.view.adapter.TransactionCardAdapter;

import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign up page.
 * <p>
 * 2020-09-22 Modified by Oscar Sanner and Alex Phu: Added binding methods
 * and initializer for recycler view.
 *
 * 2020-09-23 Modified by Alex: Added fetchDebtData method. Will probably be changed later depending on
 * if IDebtData's type will be changed to Set from List.
 *
 * 25-09-2020 Modified by Alex: Refactored bottom-buttons to GroupFragment from DetailedGroupActivity.
 */
public class GroupFragment extends Fragment {

    //TODO ("Has to store data from the currentGroup before opening this view")
    // * Transfer data between Activities (MainActivity --> DetailedGroupActivity)
    // OBS! Is currently null, not initialized
    IGroupData currentGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GroupFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.group_fragment, container, false);

        initBottomNavigation(binding);
        initRecyclerView(binding, fetchDebtData(fetchGroup(currentGroup.getGroupID())));

        return binding.getRoot();
    }

    private IDebtData[] fetchDebtData(IGroupData groupData) {
        //TODO ("IGroupData currently has the type List, change to Set or keep?")
        // If kept, change remove convertSetToArray() method.
        IDebtData[] debtData = new IDebtData[groupData.getDebts().size()];
        return groupData.getDebts().toArray(debtData);
    }

    //This method will probably be moved to ModelEngine. See issue: https://github.com/OlofSjogren/GoAyo/issues/48
    private IGroupData fetchGroup(String groupID) {
        for (IGroupData gd : ModelEngine.getInstance().getGroups()) {
            if (gd.getGroupID().equals(groupID)) {
                return gd;
            }
        }
        //Exception?
        return null;
    }

    private void initRecyclerView(GroupFragmentBinding binding, IDebtData[] debtData) {
        RecyclerView recyclerView = binding.detailedGroupRecyclerView;
        TransactionCardAdapter adapter = new TransactionCardAdapter(getContext(), debtData);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    //TODO ("Move bottommenu to GroupFragment?")
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
