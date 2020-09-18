package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityGroupCreationBinding;

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

    /**
     * TRY WITHOUT FIRST
     *
     * Returns to the MainActivity when the back button, in toolbar, is pressed.
     * @param item
     * @return
     *//*
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case android.R.id.home:
                    // todo: goto back activity from here

                    Intent intent = new Intent(this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }*/
    }
