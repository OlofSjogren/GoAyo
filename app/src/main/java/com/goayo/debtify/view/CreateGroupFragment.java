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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.CreateGroupFragmentBinding;
import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.GroupCreationViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Alex Phu
 * @date 2020-09-16
 * <p>
 * Create-a-group page.
 * <p>
 * 2020-09-18 Modified by Alex Phu and Olof Sjögren: Added listener for create button. Logic will be implemented later.
 * 2020-10-05 Modified by ALex & Yenan: implemented create group button and now it actually creates a group
 * 2020-10-12 Modified by Alex Phu: Moved clicklistener logic to the empty initContinueButton() method
 */
public class CreateGroupFragment extends Fragment {

    private CreateGroupFragmentBinding binding;
    private GroupCreationViewModel model;
    private UserCardViewAdapter userCardViewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_group_fragment, container, false);

        model = ViewModelProviders.of(requireActivity()).get(GroupCreationViewModel.class);
        PickUserViewModel pickUserViewModel = ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class);

        initRecyclerView(binding);

        pickUserViewModel.getSelectedUsersData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> iUserData) {
                userCardViewAdapter.updateList(iUserData);
                binding.createGroupNumTextView.setText(iUserData.size() + " participants");
            }
        });
        initContinueButton();

        return binding.getRoot();
    }

    private void initRecyclerView(CreateGroupFragmentBinding binding) {
        RecyclerView recyclerView = binding.recyclerView;
        userCardViewAdapter = new UserCardViewAdapter(new ArrayList<IUserData>());
        recyclerView.setAdapter(userCardViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void createGroup() {
        String groupName = binding.createGroupGroupNameTextView.getText().toString();
        List<IUserData> userData = userCardViewAdapter.getUserList();

        try {
            if (groupName.isEmpty()) {
                throw new Exception("Group name cannot be empty!");
            } else {
                model.createGroup(groupName, new HashSet(userData));
                requireActivity().finish();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Initializes the create-group button.
     */
    private void initContinueButton() {
        binding.createGroupCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createGroup();
            }
        });
    }
}
