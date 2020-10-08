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
import com.goayo.debtify.databinding.DisplayContactsFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.ContactsViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 * <p>
 * Fragment class to display a list of personal contacts to the logged in user.
 * Has buttons to add or remove contacts to the list.
 * <p>
 * 2020-10-08 Modified by Yenan: removed the dependency to model, implemented add/remove contacts
 */
public class DisplayContactsFragment extends Fragment {
    // binding object that generates all widgets belong to this class's xml file
    private DisplayContactsFragmentBinding binding;
    // viewmodel unique to this class
    private ContactsViewModel model;
    // shared viewmodel for retrieving selected users' data
    private PickUserViewModel pickUserViewModel;
    // the adapter that will be used to update contact list
    private UserCardViewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.display_contacts_fragment, container, false);
        model = ViewModelProviders.of(requireActivity()).get(ContactsViewModel.class);
        pickUserViewModel = ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class);

        initRecyclerView(new ArrayList<>(Objects.requireNonNull(model.getContactsData().getValue())));

        // observe the result from selecting users and remove then accordingly
        pickUserViewModel.getSelectedUsersData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> userData) {
                if (userData.size() != 0) {
                    try {
                        model.removeContacts(userData);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        // update the view whenever the data updates
        model.getContactsData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> userData) {
                adapter.updateList(userData);
                showRecyclerView(userData.size() == 0);
            }
        });

        binding.removeContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeContacts();
            }
        });

        binding.addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addContacts();
            }
        });

        return binding.getRoot();
    }

    private void showRecyclerView(boolean isEmpty) {
        if (isEmpty) {
            binding.displayContactsRecyclerView.setVisibility(View.GONE);
        } else {
            binding.displayContactsRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    private void removeContacts() {
        // send the users to be selected from to PickUserFragment
        pickUserViewModel.setInitialUsers(model.getContactsData().getValue());
        // navigate to PickUserFragment
        NavHostFragment.findNavController(this).navigate(R.id.action_displayContactsFragment_to_pickUserFragment);
    }

    private void addContacts() {
        // navigate to AddContactFragment
        NavHostFragment.findNavController(this).navigate(R.id.action_displayContactsFragment_to_addContactFragment);
    }

    private void initRecyclerView(List<IUserData> userData) {
        RecyclerView contactsRecyclerView = binding.displayContactsRecyclerView;
        adapter = new UserCardViewAdapter(userData);

        contactsRecyclerView.setAdapter(adapter);
        contactsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}