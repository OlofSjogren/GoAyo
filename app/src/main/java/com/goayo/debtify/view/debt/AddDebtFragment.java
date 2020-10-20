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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.AddDebtFragmentBinding;
import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.AddDebtViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page for adding a debt to a group.
 * <p>
 * 2020-09-18 Modified by Yenan & Gabriel: Added AddDebt view, tested with hard-coded value
 * 2020-09-22 Modified by Yenan & Gabriel: Removed hard-coded values, added defaults.
 * 2020-09-29 Modified by Yenan: Connected with AddDebtViewModel
 * 2020-09-30 Modified by Yenan & Alex: Made it compatible with PickUser
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-09 Modified by Yenan Wang, Alex Phu: Added radio button
 */
public class AddDebtFragment extends Fragment {
    private final String LENDER_DATA = "LENDER_DATA";
    private final String BORROWER_DATA = "BORROWER_DATA";
    // the binding object which preloads all its xml components
    private AddDebtFragmentBinding binding;
    // the ViewModel for this class
    private AddDebtViewModel addDebtViewModel;
    // the shared ViewModel for data retrieval
    private PickUserViewModel pickUserViewModel;
    // the two adapters which will observe the ViewModel
    private UserCardViewAdapter lenderAdapter;
    private UserCardViewAdapter borrowersAdapter;
    // the variable will decide what data it has retrieved
    private String dataRetrieved = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_debt_fragment, container, false);
        addDebtViewModel = ViewModelProviders.of(requireActivity()).get(AddDebtViewModel.class);
        pickUserViewModel = ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class);

        initAdapters();
        initRecyclerView();
        // set a click listener to all items in the adapter
        lenderAdapter.setCommonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickUser(false);
                dataRetrieved = LENDER_DATA;
            }
        });

        // set a click listener to all items in the adapter
        borrowersAdapter.setCommonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPickUser(true);
                dataRetrieved = BORROWER_DATA;
            }
        });

        // set a observer to notify the recyclerview adapter whenever the lender data has changed
        addDebtViewModel.getSelectedLenderData().observe(getViewLifecycleOwner(), new Observer<Set<IUserData>>() {
            @Override
            public void onChanged(Set<IUserData> userDataSet) {
                lenderAdapter.updateList(new ArrayList<>(userDataSet));
            }
        });

        // set a observer to notify the recyclerview adapter whenever the borrowers data has changed
        addDebtViewModel.getSelectedBorrowersData().observe(getViewLifecycleOwner(), new Observer<Set<IUserData>>() {
            @Override
            public void onChanged(Set<IUserData> userDataSet) {
                borrowersAdapter.updateList(new ArrayList<>(userDataSet));
            }
        });

        // this observer sets data to AddDebtViewModel whenever something is selected
        pickUserViewModel.getSelectedUsersData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> iUserData) {
                // ignore it if the list is empty
                if (iUserData.size() != 0) {
                    if (dataRetrieved.equals(LENDER_DATA)) {
                        addDebtViewModel.setSelectedLenderData(new HashSet<>(iUserData));
                    } else if (dataRetrieved.equals(BORROWER_DATA)) {
                        addDebtViewModel.setSelectedBorrowersData(new HashSet<>(iUserData));
                    }
                }
            }
        });

        // set a click listener for the button to create the debt and finish activity
        binding.continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDebt();
            }
        });

        return binding.getRoot();
    }

    private void initAdapters() {
        lenderAdapter = new UserCardViewAdapter(new ArrayList<>());
        borrowersAdapter = new UserCardViewAdapter(new ArrayList<>());
    }

    private void initRecyclerView() {
        RecyclerView lenderRecyclerView = binding.lenderRecyclerView;
        RecyclerView borrowerRecyclerView = binding.borrowerRecyclerView;
        // initialize lenderRecyclerView
        lenderRecyclerView.setAdapter(lenderAdapter);
        lenderRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // initialize borrowerRecyclerView
        borrowerRecyclerView.setAdapter(borrowersAdapter);
        borrowerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void openPickUser(boolean isMultipleChoice) {
        pickUserViewModel.setIsMultipleChoice(isMultipleChoice);
        try {
            pickUserViewModel.setInitialUsers(new ArrayList<>(addDebtViewModel.getGroupMembers(getCurrentGroupID())));
            NavHostFragment.findNavController(this)
                    .navigate(R.id.action_addDebtFragment_to_pickUsersFragment);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Oops, seems like our code monkeys made an woopsies daisy, please try again UwU", Toast.LENGTH_LONG).show();
            requireActivity().finish();
        }
    }

    private void createDebt() {
        try {
            // the parameters needed to create a debt
            String groupID = getCurrentGroupID();
            Set<IUserData> lenderSet = new HashSet<>(lenderAdapter.getUserList());
            Set<IUserData> borrowerSet = new HashSet<>(borrowersAdapter.getUserList());
            BigDecimal amount = new BigDecimal(binding.editTextNumberDecimal.getText().toString());
            String description = binding.editTextDebtDescription.getText().toString();

            // create debt
            addDebtViewModel.createDebt(groupID, lenderSet, borrowerSet, amount, description, binding.addDebtNoSplitRadiobutton.isChecked());
            // once the debt is created, the activity is therefore useless and needs to be killed
            requireActivity().finish();
        } catch (Exception e) {
            // send the error message
            Toast.makeText(getContext(), "Please enter valid input", Toast.LENGTH_LONG).show();
        }
    }

    private String getCurrentGroupID() {
        return requireActivity().getIntent().getStringExtra("GROUP_ID");
    }
}
