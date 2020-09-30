package com.goayo.debtify.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.SignInFragmentBinding;
import com.goayo.debtify.viewModel.SignInAndOutViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign in page.
 */
public class SignInFragment extends Fragment {

    SignInAndOutViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SignInFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false);
        viewModel = new SignInAndOutViewModel();
        initLogInButton(binding);
        initRegisterButton(binding);
        return binding.getRoot();
    }

    private void initRegisterButton(SignInFragmentBinding binding){
        binding.signInFragmentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
    }

    private void initLogInButton(final SignInFragmentBinding binding){
        binding.signInFragmentSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText pnField = binding.signInFragmentPhoneNumberFieldEditText;
                EditText pwField = binding.signInFragmentPasswordFieldEditText;
                boolean attempt = viewModel.logInUser(pnField.getText().toString(), pwField.getText().toString());
                if(attempt){
                    Intent intent = new Intent(binding.getRoot().getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    pnField.setText("");
                    pwField.setText("");
                }
            }
        });
    }
}
