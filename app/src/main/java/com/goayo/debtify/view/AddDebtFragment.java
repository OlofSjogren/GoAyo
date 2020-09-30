package com.goayo.debtify.view;

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
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.AddDebtViewModel;
import com.goayo.debtify.viewmodel.GroupViewModelFactory;
import com.goayo.debtify.viewmodel.GroupsViewModel;

import java.util.ArrayList;
import java.util.HashSet;
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
 */
public class AddDebtFragment extends Fragment {
    // the binding object which preloads all its xml components
    private AddDebtFragmentBinding binding;
    // the ViewModel for this class
    private AddDebtViewModel model;

    // the two adapters which will observe the ViewModel
    private UserCardViewAdapter lenderAdapter;
    private UserCardViewAdapter borrowersAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_debt_fragment, container, false);
        model = ViewModelProviders.of(requireActivity()).get(AddDebtViewModel.class);

        initAdapters();
        initRecyclerView();
        // set a click listener to all items in the adapter
        lenderAdapter.setCommonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getIntent().putExtra("BASE_CLASS", "AddDebtFragment.class_Lender");
                String groupID = ViewModelProviders.of(requireActivity(), new GroupViewModelFactory()).get(GroupsViewModel.class).getCurrentGroupData().getValue().getGroupID();
                requireActivity().getIntent().putExtra("GROUP_ID", groupID);
                openPickUser();
            }
        });

        // set a click listener to all items in the adapter
        borrowersAdapter.setCommonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getIntent().putExtra("BASE_CLASS", "AddDebtFragment.class_Borrower");
                String groupID = ViewModelProviders.of(requireActivity(), new GroupViewModelFactory()).get(GroupsViewModel.class).getCurrentGroupData().getValue().getGroupID();
                requireActivity().getIntent().putExtra("GROUP_ID", groupID);
                openPickUser();
            }
        });

        // set a observer to notify the recyclerview adapter whenever the lender data has changed
        model.getSelectedLenderData().observe(getViewLifecycleOwner(), new Observer<Set<IUserData>>() {
            @Override
            public void onChanged(Set<IUserData> userDataSet) {
                lenderAdapter.updateList(new ArrayList<>(userDataSet));
            }
        });

        // set a observer to notify the recyclerview adapter whenever the borrowers data has changed
        model.getSelectedBorrowersData().observe(getViewLifecycleOwner(), new Observer<Set<IUserData>>() {

            @Override
            public void onChanged(Set<IUserData> userDataSet) {
                borrowersAdapter.updateList(new ArrayList<>(userDataSet));
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
        lenderAdapter = new UserCardViewAdapter(new ArrayList<IUserData>());
        borrowersAdapter = new UserCardViewAdapter(new ArrayList<IUserData>());
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

    private void openPickUser() {
        // TODO change so that it also send data to PickUser
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_addDebtFragment_to_pickUsersFragment);
    }

    private void createDebt() {
        try {
            // the parameters needed to create a debt
            String groupID = getCurrentGroupID();
            Set<IUserData> lenderSet = new HashSet<>(lenderAdapter.getUserList());
            Set<IUserData> borrowerSet = new HashSet<>(borrowersAdapter.getUserList());
            double amount = Double.parseDouble(binding.editTextNumberDecimal.getText().toString());
            String description = binding.editTextDebtDescription.getText().toString();

            // create debt
            model.createDebt(groupID, lenderSet, borrowerSet, amount, description);
            // once the debt is created, the activity is therefore useless and needs to be killed
            requireActivity().finish();
        } catch (Exception e) {
            // send the error message
            // TODO probably also highlight invalid text field if possible
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // TODO implement this
    private String getCurrentGroupID() {
        return "";
    }
}
