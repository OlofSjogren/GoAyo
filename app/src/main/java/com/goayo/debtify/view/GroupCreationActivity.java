package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.goayo.debtify.databinding.ActivityGroupCreationBinding;
import com.goayo.debtify.R;

/**
 * @author Alex Phu
 * @date 2020-09-16
 * <p>
 * Entry point of creating-a-group.
 */
public class GroupCreationActivity extends AppCompatActivity {

    private ActivityGroupCreationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_creation);

        initToolbar();
    }

    /**
     * Initialises toolbar.
     */
    private void initToolbar() {
        setSupportActionBar(binding.groupCreationToolbar);
    }
}