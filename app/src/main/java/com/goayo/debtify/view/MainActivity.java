package com.goayo.debtify.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityMainBinding;
import com.goayo.debtify.view.contact.ContactsActivity;
import com.goayo.debtify.view.login.LoginActivity;
import com.goayo.debtify.viewmodel.SignInAndOutViewModel;
import com.google.android.material.navigation.NavigationView;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Entry point of the application.
 * <p>
 * 2020-09-15 Modified by Alex Phu and Olof Sjögren : Removed default floatingActionButton.
 * 2020-09-30 Modified by Olof Sjögren and Oscar Sanner : Added logOut functionality in onNavigationItemSelected() and
 * also added SignInAndOutViewModel.
 * 2020-10-08 Modified by Yenan: add menu click handler for Contacts
 * 2020-10-08 Modified by Alex Phu: Minor bug fix where user could get back to MainActivity after logout
 * 2020-10-12 Modified by Alex Phu: Removed Tabs functionality.
 * 2020-10-12 Modified by Olof Sjögren: Attribute signInAndOutViewModel is now created by ViewModelProvider.
 * Also removed temporary sign in method.
 * 2020-10-14 Modified by Alex Phu: Implemented initNavHeader() and cleaned up comments.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private SignInAndOutViewModel signInAndOutViewModel;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        signInAndOutViewModel = ViewModelProviders.of(this).get(SignInAndOutViewModel.class);

        initToolbar();
        initNavHeader();
        setNavigationViewListener();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerlayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerlayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    private void initNavHeader() {
        NavigationView navigationView = binding.navView;
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.nav_header_username);
        navUsername.setText(signInAndOutViewModel.getCurrentLoggedInUsersName());
    }

    private void setNavigationViewListener() {
        binding.navView.setNavigationItemSelectedListener(this);
    }

    /**
     * Listeners for menuItems in drawerLayout
     *
     * @param item The menu item that is clicked by the user.
     * @return Returns a boolean value depending on if the navigation was successfully executed.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.logout_menu_item:
                signInAndOutViewModel.logOutUser();
                intent = new Intent(this.binding.getRoot().getContext(), LoginActivity.class);
                startActivity(intent);
                //Finishes activity so that the user can't popBackStack to MainActivity after logOut
                finish();
                break;
            case R.id.contacts_menu_item:
                intent = new Intent(this.binding.getRoot().getContext(), ContactsActivity.class);
                startActivity(intent);
                break;
            default:
        }
        return false;
    }
}