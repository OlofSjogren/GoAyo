package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityMainBinding;
import com.goayo.debtify.view.handler.TabsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Entry point of the application.
 */
public class MainActivity extends AppCompatActivity {

    private NavController navController;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.nav_host);
        //Todo ("Nav-drawer")

        initTabs();
        initFloatingActionButton();
    }

    /**
     * Initializes tabs.
     */
    private void initTabs(){
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(tabsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
    }

    /**
     * Initializes floating action button.
     */
    private void initFloatingActionButton(){
        FloatingActionButton fab = binding.fab;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}