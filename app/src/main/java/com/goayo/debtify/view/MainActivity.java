package com.goayo.debtify.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityMainBinding;
import com.goayo.debtify.view.handler.TabsPagerAdapter;
import com.goayo.debtify.viewmodel.SignInAndOutViewModel;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Entry point of the application.
 *
 * 2020-09-15 Modified by Alex Phu and Olof Sjögren : Removed default floatingActionButton.
 * 2020-09-30 Modified by Olof Sjögren and Oscar Sanner : Added logOut functionality in onNavigationItemSelected() and
 * also added SignInAndOutViewModel.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    SignInAndOutViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = new SignInAndOutViewModel();

        initTabs();
        initToolbar();
        setNavigationViewListener();
    }

    /**
     * Temporary way of starting login activity.
     * <p>
     * TODO: Create a class that handles activities.
     */
    private void startLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
    }

    //================================================================================
    // Initialization
    //================================================================================

    /**
     * Initializes toolbar.
     */
    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.drawerlayout, binding.toolbar, R.string.open, R.string.close);
        binding.drawerlayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();
    }

    /**
     * Initializes tabs.
     */
    private void initTabs() {
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    //================================================================================
    // DrawerLayout
    //================================================================================

    /**
     * Sets custom listener for the DrawerLayout
     */
    private void setNavigationViewListener() {
        binding.navView.setNavigationItemSelectedListener(this);
    }

    /**
     * TODO ____________________________________________________________WHAT DOES IT DO?____________________________________________________________
     *
     * @param item The menu item that is clicked by the user.
     * @return Returns a boolean value depending on if the navigation was successfully executed.
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //Todo ("Logic for when menu item is selected")
        //Toast.makeText(this, "MENU_ITEM_CLICKED", Toast.LENGTH_SHORT).show();
        switch (item.getItemId()){
            case R.id.logout_menu_item:
                viewModel.logOutUser();
                Intent intent = new Intent(this.binding.getRoot().getContext(), LoginActivity.class);
                startActivity(intent);
            default:
        }
        return false;
    }
}