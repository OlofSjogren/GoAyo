package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityContactsBinding;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 * <p>
 * Activity for managing the logged in user's contacts
 * <p>
 * 2020-10-08 Modified by Yenan: removed lines that cause errors
 */
public class ContactsActivity extends AppCompatActivity {

    private ActivityContactsBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts);

        // TODO: add an actionbar here
    }
}