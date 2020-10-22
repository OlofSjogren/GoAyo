package com.goayo.debtify.view.debt;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.SettleDebtFragmentBinding;
import com.goayo.debtify.model.IDebtData;
import com.goayo.debtify.view.adapter.PickDebtAdapter;
import com.goayo.debtify.viewmodel.SettleDebtViewModel;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for paying off debt.
 * <p>
 * 2020-09-18 Modified by Yenan Wang & Gabriel Brattgård: Added SettleDebt view, tested with hard-coded value
 * 2020-09-25 Modified by Yenan Wang & Gabriel Brattgård: Implemented initRecyclerView
 * 2020-09-29 Modified by Yenan Wang: Connected with SettleDebtFragment
 * 2020-10-05 Modified by Oscar Sanner & Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-15 Modified by Yenan Wang & Alex Phu: Removed conversion to array in initRecyclerView()
 * 2020-10-22 Modified by Yenan Wang: Updated code formatting
 */
public class SettleDebtFragment extends Fragment {
    // the ViewModel for this fragment
    private SettleDebtViewModel model;
    // the binding object which preloads all its xml components
    private SettleDebtFragmentBinding binding;
    // the adapter for recyclerview which will be used to get the selected item
    private PickDebtAdapter pickDebtAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.settle_debt_fragment, container, false);
        model = new ViewModelProvider(this).get(SettleDebtViewModel.class);
        // load in the debt data by retrieving the group from groupID
        // no further data retrieval is necessary, everything needed has already been loaded
        model.retrieveData(getCurrentGroupID());
        // init the recyclerview with the hopefully initialised debtList
        initRecyclerView(model.getDebtList());

        binding.settleDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settleDebt();
            }
        });

        return binding.getRoot();
    }

    private void initRecyclerView(List<IDebtData> debtList) {
        // retrieve the recyclerview
        RecyclerView recyclerView = binding.settleDebtRecyclerView;
        // initialise the recyclerview and adapter
        pickDebtAdapter = new PickDebtAdapter(debtList);
        recyclerView.setAdapter(pickDebtAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private String getCurrentGroupID() {
        return requireActivity().getIntent().getStringExtra("GROUP_ID");
    }

    private void settleDebt() {
        try {
            // the parameters needed to settle a debt
            BigDecimal amount = new BigDecimal(binding.settleAmountEditText.getText().toString());
            String debtID = pickDebtAdapter.getSelectedDebt().getDebtID();
            String groupID = getCurrentGroupID();

            model.settleDebt(amount, debtID, groupID);
            // if the debt is settled successfully, then finish the activity
            requireActivity().finish();
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please enter a valid input", Toast.LENGTH_LONG).show();
        }
    }
}
