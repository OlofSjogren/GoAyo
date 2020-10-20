package com.goayo.debtify.view.group;

import android.annotation.SuppressLint;
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
import com.goayo.debtify.model.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewmodel.DetailedGroupViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
 *
 * 2020-09-30 Modified by Alex, Yenan: Small changes to make it use DetailedGroupViewModel.
 */
public class ShowMembersFragment extends Fragment {
    DetailedGroupViewModel detailedGroupViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ShowMembersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_members_fragment, container, false);
        detailedGroupViewModel  = ViewModelProviders.of(requireActivity()).get(DetailedGroupViewModel.class);

        initTextView(binding);
        initRecyclerView(binding);
        onBackButtonPressed();
        //To disable optionsMenu
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    private void initTextView(ShowMembersFragmentBinding binding) {
        String groupName = Objects.requireNonNull(detailedGroupViewModel.getCurrentGroup().getValue()).getGroupName();
        binding.showMembersGroupnameTextview.setText("Members of " + groupName);
    }

    private void onBackButtonPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                Navigation.findNavController(requireActivity(), R.id.group_nav_host).navigate(R.id.action_showMembersFragment_to_groupFragment);
            }
        });
    }

    private void initRecyclerView(ShowMembersFragmentBinding binding) {
        RecyclerView recyclerView = binding.showMembersRecyclerView;

        //Temporary conversion from set to array
        Set<IUserData> userDataSet = detailedGroupViewModel.getCurrentGroup().getValue().getIUserDataSet();
        List<IUserData> data = new ArrayList<>(userDataSet);

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
