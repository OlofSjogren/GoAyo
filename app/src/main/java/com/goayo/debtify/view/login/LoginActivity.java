package com.goayo.debtify.view.login;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;

import com.goayo.debtify.R;

/**
 * @author Olof Sjögren, Oscar Sanner
 * @date 2020-09-30
 * <p>
 * Activity for the login and register page.
 * <p>
 * 2020-10-22 Modified by Yenan Wang: Updated code formatting and add JavaDoc
 */
public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_login);
        Navigation.findNavController(this, R.id.loginNavHostFragment);
    }
}
