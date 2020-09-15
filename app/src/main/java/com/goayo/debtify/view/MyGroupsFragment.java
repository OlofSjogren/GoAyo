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
import com.goayo.debtify.databinding.MyGroupsFragmentBinding;
import com.google.android.material.snackbar.Snackbar;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-09-09
 * <p>
 * Second tab of the main screen.
 * <p>
 * 2020/09/15 Modified by Alex Phu. Added init function for RecyclerView. Will be activated when backend is resolved.
 */
public class MyGroupsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Binding instead of relying on findViewById
        MyGroupsFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.my_groups_fragment, container, false);

        //Testing binding on fab
        binding.addGroupFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TESTING BINDING", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        return binding.getRoot();
    }

    /**
     * Initializes RecyclerView in MyGroups.
     * @param binding Variable which can access the elements in the layout file.
     */
    private void initRecyclerView(MyGroupsFragmentBinding binding) {
        RecyclerView recyclerView = binding.groupRecyclerView;
        //GroupViewAdapter groupViewAdapter = new GroupViewAdapter(getContext(), DATA);
        //recyclerView.setAdapter(groupViewAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
    }
}
