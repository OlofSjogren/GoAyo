package com.goayo.debtify.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IUserData;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


/**
 * @author Yenan Wang
 * @date 2020-09-22
 * <p>
 * RecyclerView adapter for the user cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 *
 * 2020-09-23 Modified by Gabriel Brattgård, Yenan Wang: Implemented.
 */
public class UserCardViewAdapter extends RecyclerView.Adapter<UserCardViewAdapter.UserCardViewHolder> {

    private IUserData[] dataSet;

    /**
     * Constructor for UserCardViewAdapter.
     *
     * @param dataSet Takes in a list of IUserData.
     */
    public UserCardViewAdapter(IUserData[] dataSet) {
        this.dataSet = dataSet;
    }

    /**
     * Creates a new ViewHolder when it is needed.
     *
     * @param parent
     * @param viewType
     * @return A new instance of UserCardViewHolder
     */
    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.user_cardview, parent, false);

        UserCardViewHolder vh = new UserCardViewHolder(v);
        return vh;
    }

    /**
     * Binds the data to the ViewHolder
     *
     * @param holder ViewHolder to bind data to.
     * @param position Position in the dataSet array.
     */
    @Override
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {
        holder.setUserData(dataSet[position]);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }


    /**
     * @author Yenan Wang, Gabriel Brattgård
     * @date 2020-09-23
     * <p>
     * ViewHolder for UserCardView.
     */
    static class UserCardViewHolder extends RecyclerView.ViewHolder {

        private TextView username;
        private TextView phoneNumber;

        /**
         * Constructor for UserCardViewHolder
         *
         * @param itemView The UserCardView to bind data to.
         */
        UserCardViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.usernameTextView);
            phoneNumber = itemView.findViewById(R.id.userPhoneNumberTextView);
        }

        void setUserData(IUserData user) {
            username.setText(user.getName());
            phoneNumber.setText(user.getPhoneNumber());
        }

        void setCardViewListener(){
         //Todo: Implement.
        }

    }
}
