package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.CreateGroupFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.List;

/**
 * @author Alex Phu
 * @date 2020-09-16
 * <p>
 * Create-a-group page.
 *
 * 2020-09-18 Modified by Alex Phu and Olof Sjögren: Added listener for create button. Logic will be implemented later.
 */
public class CreateGroupFragment extends Fragment {

    private PickUserViewModel pickUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CreateGroupFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.create_group_fragment, container, false);
        pickUserViewModel = ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class);
        pickUserViewModel.getSelectedUsersData().observe(getViewLifecycleOwner(), new Observer<List<IUserData>>() {
            @Override
            public void onChanged(List<IUserData> iUserData) {
                if (iUserData.isEmpty()) {
                    pickUser();
                } else {
                    // update list
                }
            }
        });

        return binding.getRoot();
    }

    private void pickUser() {
        NavHostFragment.findNavController(this).navigate(R.id.action_createGroupFragment_to_pickUsersFragment);
    }

    /**
     * Initializes the create-group button.
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initContinueButton(CreateGroupFragmentBinding binding) {
        binding.createGroupCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ("IMPLEMENT NAVIGATION FOR CREATE BUTTON")
            }
        });
    }
}
