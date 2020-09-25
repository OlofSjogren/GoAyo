package com.goayo.debtify.view;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.goayo.debtify.R;
import com.goayo.debtify.databinding.ShowMembersFragmentBinding;

/**
 * @author Alex Phu, Oscar Sanner
 * @date 2020-09-23
 * <p>
 * Show members fragment.
 */
public class ShowMembersFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("SIGNUP", "DO YOU EXIST?");
        ShowMembersFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.show_members_fragment, container,false);
        return binding.getRoot();
    }

    private void initRecyclerView(ShowMembersFragmentBinding binding){
        //Todo, fix this. Wait for Yenan and Gabriel's user adapter.
    }
}
