package com.goayo.debtify.view;

import android.content.Intent;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.GroupFragmentBinding;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.TransactionCardAdapter;
import com.goayo.debtify.viewmodel.DetailedGroupViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Page representing a Group
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
 * 2020/09/25 Modified by Oscar Sanner, Alex Phu and Olof Sjögren: Added factory to ViewModelProvider.
 * <p>
 * 2020/09/30 Modified by Alex, Yenan: Refactored entire class.
 * <p>
 * 2020-10-09 Modified by Yenan & Alex: Add observer to ViewModel so view updates correctly
 */
public class GroupFragment extends Fragment {
    private DetailedGroupViewModel viewModel;
    private TransactionCardAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final GroupFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.group_fragment, container, false);

        viewModel = ViewModelProviders.of(requireActivity()).get(DetailedGroupViewModel.class);
        try {
            viewModel.setCurrentGroup(getCurrentGroupID());
        } catch (UserNotFoundException e) {
            // if user not found then this error could not get resolved in any way,
            // kill everything
            e.printStackTrace();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception ignored) {
            // i don't know what this is
        }

        // observe the selectedUserData and add the selected users
        ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class).getSelectedUsersData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> iUserData) {
                viewModel.addSelectedMembersToCurrentGroup(new HashSet<>(iUserData));
            }
        });

        // initialize the adapter for the debts and payments
        try {
            adapter = new TransactionCardAdapter(viewModel.getCurrentGroupDebts(getCurrentGroupID()));
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        // observe the DebtData to update them after change
        viewModel.getCurrentGroupDebtsData().observe(getViewLifecycleOwner(), new Observer<List<IDebtData>>() {
            @Override
            public void onChanged(List<IDebtData> iDebtData) {
                adapter.updateData(iDebtData);
            }
        });

        // observe the GroupBalance to update them after change
        viewModel.getCurrentGroupBalance().observe(getViewLifecycleOwner(), new Observer<BigDecimal>() {
            @Override
            public void onChanged(BigDecimal bigDecimal) {
                binding.detailedGroupBalanceTextView.setText(bigDecimal.toString() + "kr");
            }
        });

        initBottomNavigation(binding);
        initRecyclerView(binding);

        return binding.getRoot();
    }

    private void initRecyclerView(GroupFragmentBinding binding) {
        RecyclerView recyclerView = binding.detailedGroupRecyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private void initBottomNavigation(GroupFragmentBinding binding) {
        //Navigation
        final Intent intent = new Intent(getContext(), DebtActivity.class);
        intent.putExtra("GROUP_ID", viewModel.getCurrentGroup().getValue().getGroupID());
        binding.detailedGroupAddDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("DEBT_CREATE", "ADD_DEBT");
                startActivity(intent);
            }
        });
        binding.detailedGroupAddPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent.putExtra("DEBT_CREATE", "SETTLE_DEBT");
                startActivity(intent);
            }
        });
    }

    private String getCurrentGroupID() {
        return requireActivity().getIntent().getStringExtra("GROUP_ID");
    }
}
