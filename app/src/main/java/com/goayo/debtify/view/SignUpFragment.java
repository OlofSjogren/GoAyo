package com.goayo.debtify.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.SignUpFragmentBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign up page.
 */
public class SignUpFragment extends Fragment {



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SignUpFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false);
        initRegisterButton(binding);

        return binding.getRoot();
    }

    private void initRegisterButton(final SignUpFragmentBinding binding) {
        binding.signUpFragmentRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText nameField = binding.signUpFragmentNameTextInputEditText;
                TextInputEditText numberField = binding.signUpFragmentPhoneNumberTextInputEditText;
                TextInputEditText passwordField = binding.signUpFragmentPasswordTextInputEditText;

            }
        });
    }
}
