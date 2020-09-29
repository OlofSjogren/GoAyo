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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.PickUsersFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.PickUserAdapter;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.List;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * Selecting users page.
 */
public class PickUsersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PickUsersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pick_users_fragment, container, false);
        PickUserViewModel viewModel = ViewModelProviders.of(this).get(PickUserViewModel.class);

        //TODO("Set current group!")
        viewModel.setCurrentGroup("1003");

        //TODO ("Identifier for which data to fetch from ViewModel (Through Intent)")
        List<IUserData> userData = getUserData(0, viewModel);

        initRecyclerView(binding, userData, viewModel);
        initContinueButton(binding, viewModel);


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

    private List<IUserData> getUserData(int typeOfData, PickUserViewModel viewModel) {
        switch (typeOfData) {
            case 0:
                //Maybe don't call it here, let getUsersToBeAdded call it.
                viewModel.setPotentialUsersData();
                return viewModel.getPotentialUsersData().getValue();
            case 1:
                //TODO ("DEBTDATA")
                break;
            default:
                return null;
        }
        return null;
    }


    /**
     * Initializes the continue button to navigate to the next view.
     *
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initContinueButton(PickUsersFragmentBinding binding, final PickUserViewModel viewModel) {
        binding.pickuserContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!viewModel.configureGroupMembers()){
                    Toast.makeText(view.getContext(), "Please select at least a user", Toast.LENGTH_SHORT).show();
                }else{
                    getParentFragmentManager().popBackStack();
                }
            }
        });
    }
}
