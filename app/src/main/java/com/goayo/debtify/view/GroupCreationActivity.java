package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.goayo.debtify.R;
import com.goayo.debtify.viewmodel.GroupCreationViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

/**
 * @author Alex Phu
 * @date 2020-09-16
 * <p>
 * Entry point of creating-a-group.
 *
 * 2020-10-05 Modified by ALex & Yenan: changed the implementation so that it initializes the ViewModels
 */
public class GroupCreationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DataBindingUtil.setContentView(this, R.layout.activity_group_creation);
        GroupCreationViewModel model = ViewModelProviders.of(this).get(GroupCreationViewModel.class);
        PickUserViewModel pickUserViewModel = ViewModelProviders.of(this).get(PickUserViewModel.class);

        pickUserViewModel.setInitialUsers(model.getContacts());
        pickUserViewModel.setDestination(R.id.action_pickUsersFragment_to_createGroupFragment);
    }

}
