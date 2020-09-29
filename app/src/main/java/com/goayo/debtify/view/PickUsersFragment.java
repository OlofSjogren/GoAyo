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
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.PickUsersFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.PickUserAdapter;
import com.goayo.debtify.viewmodel.DetailedGroupViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.List;
import java.util.Objects;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * Selecting users page.
 *
 * 2020-09-29 Modified by Alex Phu: -----------
 */
public class PickUsersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PickUsersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pick_users_fragment, container, false);
        PickUserViewModel viewModel = ViewModelProviders.of(this).get(PickUserViewModel.class);

        //TODO ("Implement logic for Settle and add debt in switch")
        switch (Objects.requireNonNull(requireActivity().getIntent().getStringExtra("BASE_CLASS"))) {
            case "DetailedGroupActivity.class":
                viewModel.setCurrentGroup(getActivity().getIntent().getStringExtra("GROUP_ID"));
                break;
            case "AddDebtFragment.class":
                //TODO ("TO BE IMPLEMENTED")
                break;
            default:

        }
        //TODO ("Identifier for which data to fetch from ViewModel (Through Intent)")
        List<IUserData> userData = getUserData(viewModel);

        initRecyclerView(binding, userData, viewModel);
        initContinueButton(binding);

        return binding.getRoot();
    }

    /**
     * Initializes RecyclerView in PickUsers.
     *
     * @param userData Set of userdata to be displayed.
     * @param binding  Variable which can access the elements in the layout file.
     */
    private void initRecyclerView(PickUsersFragmentBinding binding, List<IUserData> userData, PickUserViewModel viewModel) {
        RecyclerView recyclerView = binding.pickuserRecyclerView;
        //TODO ("ADD DATA TO PickUserAdapter")
        PickUserAdapter pickUserAdapter = new PickUserAdapter(viewModel, userData);
        recyclerView.setAdapter(pickUserAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private List<IUserData> getUserData(PickUserViewModel viewModel) {
        viewModel.setPotentialUsersData();
        return viewModel.getPotentialUsersData().getValue();
    }

    /**
     * Initializes the continue button to navigate to the next view.
     *
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initContinueButton(PickUsersFragmentBinding binding) {
        final DetailedGroupViewModel detailedGroupViewModel = ViewModelProviders.of(this).get(DetailedGroupViewModel.class);
        final PickUserViewModel pickUserViewModel = ViewModelProviders.of(this).get(PickUserViewModel.class);


        binding.pickuserContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ("INSERT IDENTIFIER TO configureGroupMembers() to be able to distinguish debt or group")
                detailedGroupViewModel.setCurrentGroup(getActivity().getIntent().getStringExtra("GROUP_ID"));
                detailedGroupViewModel.setUsersToBeAdded(pickUserViewModel.getSelectedUsersData().getValue());
                if (!detailedGroupViewModel.addSelectedMembersToCurrentGroup()) {
                    Toast.makeText(view.getContext(), "Please select at least a user", Toast.LENGTH_SHORT).show();
                } else {
                    Navigation.findNavController(getActivity(), R.id.group_nav_host).navigate(R.id.action_pickUsersFragment_to_groupFragment);
                }
            }
        });
    }
}
