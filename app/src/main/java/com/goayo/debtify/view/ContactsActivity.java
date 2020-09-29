package com.goayo.debtify.view;

import android.os.Bundle;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityContactsBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.View;

public class ContactsActivity extends AppCompatActivity {

    private ActivityContactsBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_contacts);
        navController = Navigation.findNavController(this, R.id.contactsNavHostFragment);

        navController.setGraph(R.navigation.contacts_navigation_graph);
    }
}