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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.GroupFragmentBinding;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IDebtData;
import com.goayo.debtify.model.IUserData;
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
 * 2020/09/30 Modified by Alex Phu, Yenan Wang: Refactored entire class.
 * <p>
 * 2020-10-09 Modified by Yenan Wang & Alex Phu: Add observer to ViewModel so view updates correctly
 * <p>
 * 2020-10-12 Modified by Alex Phu: Implemented initRefreshLayout() to be able to refresh data from database
 * <p>
 * 2020-10-13 Modified by Alex Phu: Total balance now changes depending on the amount of debt. Refactored code out from the gigantic onCreateView() method.
 * <p>
 * 2020-10-13 Modified by Alex Phu: Made RecyclerView unfocusable.
 */
public class GroupFragment extends Fragment {
    private DetailedGroupViewModel viewModel;
    private TransactionCardAdapter adapter;
    private GroupFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.group_fragment, container, false);

        viewModel = ViewModelProviders.of(requireActivity()).get(DetailedGroupViewModel.class);
        try {
            viewModel.setCurrentGroup(getCurrentGroupID());
        } catch (UserNotFoundException e) {
            // if user not found then this error could not get resolved in any way,
            // kill everything
            e.printStackTrace();
            android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // observe the selectedUserData and add the selected users
        ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class).getSelectedUsersData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> iUserData) {
                viewModel.addSelectedMembersToCurrentGroup(new HashSet<>(iUserData));
            }
        });

        initHeader();
        initAdapter();
        initRecyclerView();
        initRefreshLayout();
        initBottomNavigation();

        return binding.getRoot();
    }

    private void initHeader() {
        //Sets group name label
        binding.detailedGroupGroupNameTextView.setText(viewModel.getCurrentGroup().getValue().getGroupName());

        //CardView below header
        // observe the GroupBalance to update them after change
        viewModel.getCurrentGroupBalance().observe(getViewLifecycleOwner(), new Observer<BigDecimal>() {
            @Override
            public void onChanged(BigDecimal bigDecimal) {
                //Sets colour depending on total balance and amount
                switch (bigDecimal.compareTo(new BigDecimal(0))) {
                    case 0:
                        binding.detailedGroupBalanceTextView.setTextColor(binding.detailedGroupBalanceTextView.getResources().getColor(R.color.dividerGrey));
                        break;
                    case -1:
                        binding.detailedGroupBalanceTextView.setTextColor(binding.detailedGroupBalanceTextView.getResources().getColor(R.color.negativeDebtRed));
                        break;
                    case 1:
                        binding.detailedGroupBalanceTextView.setTextColor(binding.detailedGroupBalanceTextView.getResources().getColor(R.color.positiveDebtGreen));
                        break;
                }
                binding.detailedGroupBalanceTextView.setText(bigDecimal.toString() + "kr");
            }
        });
    }

    private void initAdapter() {
        // initialize the adapter for the debts and payments
        try {
            adapter = new TransactionCardAdapter(viewModel.getCurrentGroupDebts());
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
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = binding.detailedGroupRecyclerView;
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        binding.detailedGroupRecyclerView.setFocusable(false);
    }

    private void initBottomNavigation() {
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

    private void initRefreshLayout() {
        binding.detailedGroupRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.updateDataFromDatabase();
                binding.detailedGroupRefreshLayout.setRefreshing(false);
            }
        });
    }

    private String getCurrentGroupID() {
        return requireActivity().getIntent().getStringExtra("GROUP_ID");
    }
}
