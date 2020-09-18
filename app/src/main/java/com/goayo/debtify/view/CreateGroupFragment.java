package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.CreateGroupFragmentBinding;

/**
 * @author Alex Phu
 * @date 2020-09-16
 * <p>
 * Create-a-group page.
 *
 * 2020-09-18 Modified by Alex Phu and Olof Sjögren: Added listener for create button. Logic will be implemented later.
 */
public class CreateGroupFragment extends Fragment {

    //TODO ("Create a createAGroupActivity, create another fragment for choosing participants")

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        CreateGroupFragmentBinding binding = DataBindingUtil.inflate(inflater, R.id.createGroupFragment, container, false);
        return binding.getRoot();
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
