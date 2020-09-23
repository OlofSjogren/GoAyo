package com.goayo.debtify.view;

import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
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
import com.goayo.debtify.databinding.AddMembersFragmentBinding;
import com.goayo.debtify.model.ModelEngine;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.view.adapter.PickUserAdapter;
import com.goayo.debtify.view.adapter.TransactionCardAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Sign up page.
 */
public class AddMembersFragment extends Fragment {

    IGroupData currentGroup;

    //TODO: Initialize currentGroup.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SIGNUP", "DO YOU EXIST?");
        AddMembersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.add_members_fragment, container, false);
        return binding.getRoot();
    }

    private void initRecyclerView(AddMembersFragmentBinding binding){
        RecyclerView recyclerView = binding.addMembersRecyclerView;
        PickUserAdapter adapter = new PickUserAdapter(getContext(), getAddableMembers());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }

    private IUserData[] getAddableMembers(){
        List<IUserData> userDataToBeReturned = new ArrayList<>();

        for(IUserData user : ModelEngine.getInstance().getContacts()){
            if(!currentGroup.getIUserDataSet().contains(user)){
                userDataToBeReturned.add(user);
            }
        }

        IUserData[] retArr = new IUserData[userDataToBeReturned.size()];
        return userDataToBeReturned.toArray(retArr);
    }
}
