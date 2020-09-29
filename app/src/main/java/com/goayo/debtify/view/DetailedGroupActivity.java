package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ActivityDetailedGroupBinding;
import com.goayo.debtify.viewmodel.GroupViewModelFactory;
import com.goayo.debtify.viewmodel.GroupsViewModel;

/**
 * @author Alex Phu, Oscar Sanner
 * @date 2020-09-22
 * <p>
 * Activity for the detailed view of a group.
 *
 * 25-09-2020 Modified by Alex: Refactored bottom-buttons to GroupFragment.
 *
 * 2020/09/25 Modified by Oscar Sanner, Alex Phu and Olof Sjögren: Removed duplicate "setContentView".
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
        switch (item.getItemId()){
            case R.id.action_add_members :
                getIntent().putExtra("BASE_CLASS", "DetailedGroupActivity.class");
                String groupID = ViewModelProviders.of(this, new GroupViewModelFactory()).get(GroupsViewModel.class).getCurrentGroupData().getValue().getGroupID();
                getIntent().putExtra("GROUP_ID", groupID);

                Navigation.findNavController(this, R.id.group_nav_host).navigate(R.id.action_groupFragment_to_pickUsersFragment);
                break;
            case R.id.action_show_group_informaion :
                Navigation.findNavController(this, R.id.group_nav_host).navigate(R.id.action_groupFragment_to_showMembersFragment);
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void initToolBar(ActivityDetailedGroupBinding binding) {
        setSupportActionBar(binding.detailedGroupToolbar);
    }
}