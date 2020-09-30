package com.goayo.debtify.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

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
 *
 * 2020-09-30 Added all functionality: editable text-input fields, registration and login buttons as well as fragment's initialisation.
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

    /**
     * Method for initializing an on-click listener to the registration button which navigates to the SignUpFragment.
     * @param binding Variable which can access the elements in the layout file, sign_in_fragment.xml.
     */
    private void initRegisterButton(SignInFragmentBinding binding){
        binding.signInFragmentSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_signUpFragment);
            }
        });
    }

    /**
     * Initializes on-click listener for the login button which reads the input fields and attempts a login through the dedicated ViewModel.
     * Will start the MainActivity (through an intent) if the login was successful in model, will otherwise clear all input fields and display an error message.
     * @param binding Variable which can access the elements in the layout file, sign_up_fragment.xml.
     */
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
                    Toast.makeText (binding.getRoot().getContext(), "Invalid phone number or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
