package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityDetailedGroupBinding;

/**
 * @author Alex Phu, Oscar Sanner
 * @date 2020-09-22
 * <p>
 * Activity for the detailed view of a group.
 */
public class DetailedGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_group);
        ActivityDetailedGroupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_group);

        initToolBar(binding);
        initBottomNavigation(binding);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_members :
                //Todo: Add something
            case R.id.action_show_group_informaion :
                //Todo: Show something
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void initToolBar(ActivityDetailedGroupBinding binding) {
        setSupportActionBar(binding.detailedGroupToolbar);
    }

    private void initBottomNavigation(ActivityDetailedGroupBinding binding) {
        //Navigation
        binding.detailedGroupAddDebtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ("Navigate to the add-debt-view")
            }
        });
        binding.detailedGroupAddPaymentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO ("Navigate to the settle-up-view")
            }
        });
    }
}