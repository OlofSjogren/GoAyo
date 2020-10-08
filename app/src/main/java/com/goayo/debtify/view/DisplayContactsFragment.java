package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.DisplayContactsFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewModel.ContactsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 * <p>
 * Fragment class to display a list of personal contacts to the logged in user.
 * Has buttons to add or remove contacts to the list.
 * <p>
 * 2020-10-08 Modified by Yenan: removed the dependency to model
 */
public class DisplayContactsFragment extends Fragment {

    private DisplayContactsFragmentBinding binding;
    private ContactsViewModel model;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.display_contacts_fragment, container, false);
        model = ViewModelProviders.of(requireActivity()).get(ContactsViewModel.class);

        initRecyclerView(new ArrayList<>(model.getContactsData().getValue()));
        return binding.getRoot();
    }

    private void initRecyclerView(List<IUserData> userData) {
        RecyclerView displayContactsRecyclerView = binding.displayContactsRecyclerView;

        UserCardViewAdapter adapter = new UserCardViewAdapter(userData);
        displayContactsRecyclerView.setAdapter(adapter);
        displayContactsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }
}