package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.View;

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