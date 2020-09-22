package com.goayo.debtify.view;

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
import com.goayo.debtify.databinding.GroupFragmentBinding;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.view.adapter.TransactionCardAdapter;

import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign up page.
 *
 * 2020-09-22 Modified by Oscar Sanner and Alex Phu: Added binding methods
 * and initializer for recycler view.
 */
public class GroupFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        GroupFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.group_fragment, container,false);

        return binding.getRoot();
    }

    private void initRecyclerView(GroupFragmentBinding binding, Set<IDebtData> debtData){
        RecyclerView recyclerView = binding.detailedGroupRecyclerView;
        TransactionCardAdapter adapter = new TransactionCardAdapter(getContext(),convertSetToArray(debtData));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private IDebtData[] convertSetToArray(Set<IDebtData> debtDataSet) {
        IDebtData[] tempData = new IDebtData[debtDataSet.size()];
        debtDataSet.toArray(tempData);
        return tempData;
    }
}
