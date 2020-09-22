package com.goayo.debtify.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Yenan Wang
 * @date 2020-09-22
 * <p>
 * RecyclerView adapter for the user cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 */
public class UserCardViewAdapter extends RecyclerView.Adapter<UserCardViewAdapter.UserCardViewHolder> {

    @NonNull
    @Override
    public UserCardViewAdapter.UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardViewAdapter.UserCardViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    static class UserCardViewHolder extends RecyclerView.ViewHolder {

        public UserCardViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
