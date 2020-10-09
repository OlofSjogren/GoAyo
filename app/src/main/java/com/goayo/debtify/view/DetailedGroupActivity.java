package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityDetailedGroupBinding;
import com.goayo.debtify.viewmodel.DetailedGroupViewModel;
import com.goayo.debtify.viewmodel.PickUserViewModel;

/**
 * @author Alex Phu, Oscar Sanner
 * @date 2020-09-22
 * <p>
 * Activity for the detailed view of a group.
 * <p>
 * 25-09-2020 Modified by Alex Phu: Refactored bottom-buttons to GroupFragment.
 * <p>
 * 2020/09/25 Modified by Oscar Sanner, Alex Phu and Olof Sjögren: Removed duplicate "setContentView".
 * <p>
 * 2020-09-30 Modified by Yenan Wang & Alex Phu: Refactored so that it now uses PickUserViewModel and
 * DetailedGroupViewModel to manage data
 *
 * 2020-10-08 Modified by Alex Phu: Added third menu item (leave group) and its implementation.
 */
public class DetailedGroupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDetailedGroupBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detailed_group);

        initToolBar(binding);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailed_group_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        PickUserViewModel pickUserViewModel = ViewModelProviders.of(this).get(PickUserViewModel.class);
        DetailedGroupViewModel detailedGroupViewModel = ViewModelProviders.of(this).get(DetailedGroupViewModel.class);
        switch (item.getItemId()) {
            case R.id.action_add_members:
                pickUserViewModel.setInitialUsers(detailedGroupViewModel.getAddableUsers());
                pickUserViewModel.setIsMultipleChoice(true);

                Navigation.findNavController(this, R.id.group_nav_host).navigate(R.id.action_groupFragment_to_pickUsersFragment);
                break;
            case R.id.action_show_group_informaion:
                Navigation.findNavController(this, R.id.group_nav_host).navigate(R.id.action_groupFragment_to_showMembersFragment);
                break;
            case R.id.action_leave_group:
                detailedGroupViewModel.leaveCurrentGroup();
                //Finish activity
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initToolBar(ActivityDetailedGroupBinding binding) {
        setSupportActionBar(binding.detailedGroupToolbar);
    }
}