package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.PickUsersFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.PickUserAdapter;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.List;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * Selecting users page.
 * <p>
 * 2020-09-29 Modified by Alex Phu: Implementation of RecyclerView, continueButton, differentiating which Activity started PickUserFragment.
 * Connected with PickUserViewModel.
 * Disabled OptionsMenu.
 */
public class PickUsersFragment extends Fragment {

    private PickUserAdapter pickUserAdapter;
    private PickUserViewModel pickUserViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PickUsersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pick_users_fragment, container, false);

        pickUserViewModel = ViewModelProviders.of(requireActivity()).get(PickUserViewModel.class);
        pickUserAdapter = new PickUserAdapter(pickUserViewModel.getInitialUsers(), pickUserViewModel.getIsMultipleChoice());

        initRecyclerView(binding);
        initContinueButton(binding);
        //To disable optionsMenu
        setHasOptionsMenu(true);

        return binding.getRoot();
    }

    private void initRecyclerView(PickUsersFragmentBinding binding) {
        RecyclerView recyclerView = binding.pickuserRecyclerView;
        recyclerView.setAdapter(pickUserAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * Initializes the continue button to navigate to the next view.
     *
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initContinueButton(PickUsersFragmentBinding binding) {
        binding.pickuserContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<IUserData> userList = pickUserAdapter.getSelectedUser();
                if (userList.size() != 0) {
                    pickUserViewModel.setSelectedUsersData(userList);
                    Navigation.findNavController(view).popBackStack();
                } else {
                    Toast.makeText(getContext(), "Please select at least a user!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //To remove OptionMenu from
        menu.clear();
    }
}
