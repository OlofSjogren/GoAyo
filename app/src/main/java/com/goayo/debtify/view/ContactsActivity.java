package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_contacts);
        setOnBackPressed();


        // TODO: add an actionbar here
    }

    // forces the back button to pop the backstack instead of doing whatever it was doing
    private void setOnBackPressed() {
        final NavController navController = Navigation.findNavController(this, R.id.contactsNavHostFragment);
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                navController.popBackStack();
            }
        };
        this.getOnBackPressedDispatcher().addCallback(this, callback);
    }
}