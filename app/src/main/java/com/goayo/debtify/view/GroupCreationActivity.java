package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityGroupCreationBinding;
import com.goayo.debtify.viewmodel.GroupCreationViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

/**
 * @author Alex Phu
 * @date 2020-09-16
 * <p>
 * Entry point of creating-a-group.
 */
public class GroupCreationActivity extends AppCompatActivity {

    private ActivityGroupCreationBinding binding;
    private GroupCreationViewModel model;
    private PickUserViewModel pickUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_group_creation);
        model = ViewModelProviders.of(this).get(GroupCreationViewModel.class);
        pickUserViewModel = ViewModelProviders.of(this).get(PickUserViewModel.class);

        pickUserViewModel.setInitialUsers(model.getContacts());
    }

}
