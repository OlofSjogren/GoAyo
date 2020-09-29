package com.goayo.debtify.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.DisplayContactsFragmentBinding;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.UserCardViewAdapter;
import com.goayo.debtify.viewModel.ContactsViewModel;
import com.goayo.debtify.viewModel.ContactsViewModelFactory;

import java.util.Set;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 *
 * Fragment class to display a list of personal contacts to the logged in user.
 * Has buttons to add or remove contacts to the list.
 *
 *
 */
public class DisplayContactsFragment extends Fragment {

    DisplayContactsFragmentBinding binding;

    //Temporary to test without viewModel.
    ModelEngine model = ModelEngine.getInstance();

    //Do we need this?
    public DisplayContactsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.display_contacts_fragment, container, false);

        //ContactsViewModel viewModel = new ContactsViewModel();
        //initRecyclerView(viewModel.getContactsData().getValue());

        initRecyclerView(model.getContacts());
        return binding.getRoot();
    }

    private void initRecyclerView(Set<IUserData> userData){
        RecyclerView displayContactsRecyclerView = binding.displayContactsRecyclerView;

        UserCardViewAdapter adapter = new UserCardViewAdapter((IUserData[]) userData.toArray());
        displayContactsRecyclerView.setAdapter(adapter);
        displayContactsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }
}