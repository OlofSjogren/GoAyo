package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.AddContactFragmentBinding;

/**
 * @author Yenan Wang
 * @date 2020-10-08
 * <p>
 * Fragment class to add a user to the logged in user's contacts
 */
public class AddContactFragment extends Fragment {

    private AddContactFragmentBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.add_contact_fragment, container, false);



        return binding.getRoot();
    }
}