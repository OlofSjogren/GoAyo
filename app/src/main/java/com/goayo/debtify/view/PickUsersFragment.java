package com.goayo.debtify.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.PickUsersFragmentBinding;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.PickUserAdapter;

import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * Selecting users page.
 */
public class PickUsersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        PickUsersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.pick_users_fragment, container, false);

        return binding.getRoot();
    }

    /**
     * Initializes RecyclerView in PickUsers.
     *
     * @param userData Set of userdata to be displayed.
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initRecyclerView(PickUsersFragmentBinding binding, Set<IUserData> userData) {
        RecyclerView recyclerView = binding.pickuserRecyclerView;
        //TODO ("ADD DATA TO PickUserAdapter")
        PickUserAdapter pickUserAdapter = new PickUserAdapter(getContext(), convertSetToArray(userData));
        recyclerView.setAdapter(pickUserAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    /**
     * Converts a Set to an Array.
     * @param userDataSet Set of Users.
     * @return Returns an Array of Users.
     */
    private IUserData[] convertSetToArray(Set<IUserData> userDataSet) {
        IUserData[] tempData = new IUserData[userDataSet.size()];
        userDataSet.toArray(tempData);
        return tempData;
    }
}
