package com.goayo.debtify.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityDetailedGroupBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DetailedGroupActivity extends AppCompatActivity {

    Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_group);
        ActivityDetailedGroupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_group);

    }

    private void initToolBar(ActivityDetailedGroupBinding binding){
        setSupportActionBar(binding.detailedGroupToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detailed_group_bottom_navigation_menu, menu);
        this.menu = menu;
        return super.onCreateOptionsMenu(menu);
    }

    private void initBottomNav(ActivityDetailedGroupBinding binding){
        BottomNavigationView view = binding.detailedGroupBottomNavigation;
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case menu.findItem(R.id.add)
                        break;
                }
                return false;
            }
        });
    }
}