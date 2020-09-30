package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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
import com.goayo.debtify.viewmodel.AddDebtViewModel;
import com.goayo.debtify.viewmodel.DetailedGroupViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * Selecting users page.
 * <p>
 * 2020-09-29 Modified by Alex Phu: Implementation of RecyclerView, continueButton, differentiating which Activity started PickUserFragment.
 * Connected with PickUserViewModel.
 * Disabled OptionsMenu.
 */
public class PickUsersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PickUsersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pick_users_fragment, container, false);

        PickUserViewModel pickUserViewModel = ViewModelProviders.of(getActivity()).get(PickUserViewModel.class);
        DetailedGroupViewModel detailedGroupViewModel = ViewModelProviders.of(getActivity()).get(DetailedGroupViewModel.class);

        //Set current group in PickUserViewModel
        pickUserViewModel.setCurrentGroup(getActivity().getIntent().getStringExtra("GROUP_ID"));

        List<IUserData> userData = new ArrayList<>();
        //TODO ("Implement logic for Settle and add debt in switch")
        switch (Objects.requireNonNull(requireActivity().getIntent().getStringExtra("BASE_CLASS"))) {
            case "DetailedGroupActivity.class":
                //Set current group in DetailedGroupViewModel
                detailedGroupViewModel.setCurrentGroup(getActivity().getIntent().getStringExtra("GROUP_ID"));

                //Init initialUsers (Users to be displayed on RecyclerView)
                pickUserViewModel.setInitialUsersToExcludeCurrentGroupMembers();

                //OnBackButtonPressed
                requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(getActivity(), R.id.group_nav_host).navigate(R.id.action_pickUsersFragment_to_groupFragment);
                    }
                });
                break;
            case "AddDebtFragment.class":
                //TODO ("TO BE IMPLEMENTED")
                //Init initalUsers with all group members
            case "AddDebtFragment.class_Lender":
            case "AddDebtFragment.class_Borrower":
                pickUserViewModel.setInitialUsersToAllGroupMembers();
                break;
        }
        //Fetches initialUsers after init in switch.
        userData = pickUserViewModel.getInitialUsers().getValue();

        initRecyclerView(binding, pickUserViewModel, userData);

        initContinueButton(binding);

        //To disable optionsMenu
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    private void initRecyclerView(PickUsersFragmentBinding binding, PickUserViewModel pickUserViewModel, List<IUserData> userData) {
        RecyclerView recyclerView = binding.pickuserRecyclerView;
        PickUserAdapter pickUserAdapter = new PickUserAdapter(pickUserViewModel, userData);
        recyclerView.setAdapter(pickUserAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * Initializes the continue button to navigate to the next view.
     *
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initContinueButton(PickUsersFragmentBinding binding) {
        final PickUserViewModel pickUserViewModel = ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class);
        //final AddDebtViewModel debtViewModel = new ViewModelProvider(this).get(AddDebtViewModel.class);
        final AddDebtViewModel debtViewModel = ViewModelProviders.of(requireActivity()).get(AddDebtViewModel.class);

        binding.pickuserContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Identifier to know where and what to do at the end of PickUsersFragment's lifecycle
                switch (Objects.requireNonNull(requireActivity().getIntent().getStringExtra("BASE_CLASS"))) {
                    case "DetailedGroupActivity.class":
                        final DetailedGroupViewModel detailedGroupViewModel = ViewModelProviders.of(requireActivity()).get(DetailedGroupViewModel.class);
                        //Add selected users from pickUserViewModel to the group.
                        if (!detailedGroupViewModel.addSelectedMembersToCurrentGroup(pickUserViewModel.getSelectedUsersData().getValue())) {
                            //--> No user's selected
                            Toast.makeText(view.getContext(), "Please select at least a user", Toast.LENGTH_SHORT).show();
                        } else {
                            Navigation.findNavController(getActivity(), R.id.group_nav_host).navigate(R.id.action_pickUsersFragment_to_groupFragment);
                        }
                        break;
                    case "AddDebtFragment.class_Lender":
                        //TODO ("TO BE IMPLEMENTED")
                        debtViewModel.setSelectedLender(new HashSet<IUserData>(pickUserViewModel.getSelectedUsersData().getValue()));
                        pickUserViewModel.resetSelectedData();
                        Navigation.findNavController(requireActivity(), R.id.debtNavHostFragment).navigate(R.id.action_pickUsersFragment_to_addDebtFragment);
                        break;
                    case "AddDebtFragment.class_Borrower":
                        //TODO ("TO BE IMPLEMENTED")
                        debtViewModel.setSelectedBorrowersData(new HashSet<IUserData>(pickUserViewModel.getSelectedUsersData().getValue()));

                        pickUserViewModel.resetSelectedData();
                        Navigation.findNavController(requireActivity(), R.id.debtNavHostFragment).navigate(R.id.action_pickUsersFragment_to_addDebtFragment);
                        break;
                }
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //To remove OptionMenu from
        menu.clear();
    }
}
