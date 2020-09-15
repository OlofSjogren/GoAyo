package com.goayo.debtify.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityMainBinding;
import com.goayo.debtify.view.handler.TabsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Entry point of the application.
 *
 * 2020-09-15 Modified by Alex Phu and Olof Sjögren. Removed default floatingActionButton.
 */
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

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
        Toast.makeText(this, "MENU_ITEM_CLICKED", Toast.LENGTH_SHORT).show();
        return false;
    }
}