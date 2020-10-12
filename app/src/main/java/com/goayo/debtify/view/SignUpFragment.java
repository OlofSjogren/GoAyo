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
import androidx.navigation.Navigation;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.SignUpFragmentBinding;
import com.goayo.debtify.viewmodel.SignUpViewModel;
import com.google.android.material.textfield.TextInputEditText;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign up page.
 *
 * 2020-09-30 Modified by Olof Sjögren and Oscar Sanner : Added all functionality: editable text-input fields, registration button and fragment's initialisation.
 * 2020-10-11 Modified by Alex Phu: Trimmed TextFields before sending registration-data to viewModel.
 */
public class SignUpFragment extends Fragment {

    SignUpViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SignUpFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false);
        viewModel = new SignUpViewModel();
        initRegisterButton(binding);

        return binding.getRoot();
    }

    /**
     * Initializes on-click listener for registration button which reads the input fields and attempts a registration through the dedicated ViewModel.
     * Will direct the user back to the SignInFragment if the registrations was successful in model, will otherwise clear all input fields and display an error message.
     * @param binding Variable which can access the elements in the layout file, sign_up_fragment.xml.
     */
    private void initRegisterButton(final SignUpFragmentBinding binding) {
        binding.signUpFragmentRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText nameField = binding.signUpFragmentNameTextInputEditText;
                TextInputEditText numberField = binding.signUpFragmentPhoneNumberTextInputEditText;
                TextInputEditText passwordField = binding.signUpFragmentPasswordTextInputEditText;
                String trimmedName = nameField.getText().toString().trim().replaceAll("\\s+", " ");
                boolean attempt = viewModel.registerUser(numberField.getText().toString().trim(), trimmedName, passwordField.getText().toString());
                if(attempt){
                    Toast.makeText (binding.getRoot().getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(view).navigate(R.id.action_signUpFragment_to_signInFragment);
                } else {
                    nameField.setText("");
                    numberField.setText("");
                    passwordField.setText("");
                    Toast.makeText (binding.getRoot().getContext(), "Registration Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
