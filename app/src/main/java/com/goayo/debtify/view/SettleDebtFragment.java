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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.SettleDebtFragmentBinding;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.view.adapter.PickDebtAdapter;

import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for paying off debt.
 *
 * 2020-09-18 Modified by Yenan & Gabriel: Added SettleDebt view, tested with hard-coded value
 * 2020-09-25 Modified by Yenan & Gabriel: Implemented initRecyclerView
 */
public class SettleDebtFragment extends Fragment {

    private SettleDebtFragmentBinding binding;

    //TODO: Replace this model instance with GroupViewModel.
    private ModelEngine model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.settle_debt_fragment, container, false);
        model = ModelEngine.getInstance();

        //TODO: Add data from GroupViewModel
        //initRecyclerView();

        return binding.getRoot();
    }

    private void initRecyclerView(List<IDebtData> dataList){
        //Maybe fix this explicit conversion.
        IDebtData[] dataArray = (IDebtData[]) dataList.toArray();
        RecyclerView recyclerView = binding.settleDebtRecyclerView;
        recyclerView.setAdapter(new PickDebtAdapter(dataArray));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }
}
