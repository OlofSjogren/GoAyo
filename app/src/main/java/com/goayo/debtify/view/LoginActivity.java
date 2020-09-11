package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private NavController navController;
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        navController = Navigation.findNavController(this, R.id.loginNavHostFragment);

        this.getSupportActionBar().hide();

    }
}
