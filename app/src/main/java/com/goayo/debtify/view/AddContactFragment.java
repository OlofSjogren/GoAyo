package com.goayo.debtify.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.AddContactFragmentBinding;
import com.goayo.debtify.model.UserAlreadyExistsException;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.AddContactViewModel;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Yenan Wang
 * @date 2020-10-08
 * <p>
 * Fragment class to add a user to the logged in user's contacts
 */
public class AddContactFragment extends Fragment {
    // binding object that generates all widgets that belong to this class's xml file
    private AddContactFragmentBinding binding;
    // ViewModel specific to this class
    private AddContactViewModel model;
    // adapter that displays the user that matches the phone number
    private UserCardViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_contact_fragment, container, false);
        model = ViewModelProviders.of(requireActivity()).get(AddContactViewModel.class);

        // observe the viewmodel and update the list accordingly
        model.getUserData().observe(getViewLifecycleOwner(), new Observer<IUserData>() {
            @Override
            public void onChanged(IUserData iUserData) {
                if (iUserData != null) {
                    List<IUserData> userData = new ArrayList<>();
                    userData.add(iUserData);
                    adapter.updateList(userData);
                }
            }
        });

        // search for user as input updates, and updates the viewmodel accordingly
        binding.phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignored
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignored
            }

            @Override
            public void afterTextChanged(Editable s) {
                searchUser(s.toString());
            }
        });

        binding.addContactButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserToContact();
            }
        });

        setOnBackPressed();
        initRecyclerView();

        return binding.getRoot();
    }

    private void searchUser(String phoneNumber) {
        try {
            IUserData user = model.findUser(phoneNumber);
            model.setUserData(user);
            setUserFound(true);
        } catch (UserNotFoundException | ConnectException e) {
            setUserFound(false);
        }
    }

    private void setUserFound(boolean userFound) {
        if (!userFound) {
            binding.selectedUserRecyclerView.setVisibility(View.INVISIBLE);
            binding.noUserFoundTextView.setVisibility(View.VISIBLE);
        } else {
            binding.selectedUserRecyclerView.setVisibility(View.VISIBLE);
            binding.noUserFoundTextView.setVisibility(View.INVISIBLE);
        }
    }

    private void addUserToContact() {
        try {
            String phoneNumber = binding.phoneEditText.getText().toString();
            model.addUserToContacts(phoneNumber);
            stepBack();
        } catch (ConnectException | UserNotFoundException | UserAlreadyExistsException e) {
            // dump the error message to the user
           Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    // navigate back to previous screen inside this activity
    private void stepBack() {
        final NavController navController = NavHostFragment.findNavController(this);
        navController.popBackStack();
    }

    private void initRecyclerView() {
        RecyclerView rv = binding.selectedUserRecyclerView;
        adapter = new UserCardViewAdapter(new ArrayList<IUserData>());

        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    // forces the back button to pop the backstack instead of doing whatever it was doing
    private void setOnBackPressed() {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                stepBack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }
}