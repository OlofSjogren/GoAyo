package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ShowMembersFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.GroupViewModelFactory;
import com.goayo.debtify.viewmodel.GroupsViewModel;

import java.util.Set;

/**
 * @author Alex Phu, Oscar Sanner
 * @date 2020-09-23
 * <p>
 * Show members fragment.
 * <p>
 * 2020-09-29 Modified by Alex: Implemented RecyclerView and connected it with GroupsViewModel.
 * BackbuttonPressed now won't exit activity.
 * Disabled OptionsMenu.
 */
public class ShowMembersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowMembersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_members_fragment, container, false);
        initRecyclerView(binding);
        onBackButtonPressed();

        //To disable optionsMenu
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    private void onBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(getActivity(), R.id.group_nav_host).navigate(R.id.action_showMembersFragment_to_groupFragment);
            }
        });
    }

    private void initRecyclerView(ShowMembersFragmentBinding binding) {
        RecyclerView recyclerView = binding.showMembersRecyclerView;
        GroupsViewModel viewModel = ViewModelProviders.of(this, new GroupViewModelFactory()).get(GroupsViewModel.class);

        //Temporary conversion from set to array
        Set<IUserData> userDataSet = viewModel.getCurrentGroupData().getValue().getIUserDataSet();
        IUserData[] data = new IUserData[userDataSet.size()];
        userDataSet.toArray(data);

        UserCardViewAdapter userCardViewAdapter = new UserCardViewAdapter(data);
        recyclerView.setAdapter(userCardViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //To remove OptionMenu from
        menu.clear();
    }
}
