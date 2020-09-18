package com.goayo.debtify.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IUserData;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * RecyclerView adapter for the pickUsers cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 */
public class PickUserAdapter extends RecyclerView.Adapter<PickUserAdapter.PickUserViewHolder> {
    private final Context context;
    private IUserData[] userData;

    /**
     * Constructor for GroupViewAdapter
     *
     * @param context  The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
     * @param userData The data to be displayed.
     */
    public PickUserAdapter(Context context, IUserData[] userData) {
        this.context = context;
        this.userData = userData;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     *
     * @param parent   Parent-view
     * @param viewType View type
     * @return A new instance of GroupViewHolder
     */
    @NonNull
    @Override
    public PickUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pick_user_cardview, parent, false);
        return new PickUserViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder
     *
     * @param holder   ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull PickUserViewHolder holder, int position) {
        holder.setUserData(context, userData[position]);
    }

    @Override
    public int getItemCount() {
        return userData.length;
    }

    /**
     * @author Alex Phu, Olof Sjögren
     * @date 2020-09-18
     * <p>
     * ViewHolder for PickUser
     */
    class PickUserViewHolder extends RecyclerView.ViewHolder {
        private TextView username;
        private TextView phoneNumber;
        private CheckBox checkBox;

        /**
         * Binds the elements in the layout file to a variable
         *
         * @param itemView In this case, pick_user_cardview
         */
        public PickUserViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.pickuser_card_name_textview);
            phoneNumber = itemView.findViewById(R.id.pickuser_card_phoneNumber_textview);
            checkBox = itemView.findViewById(R.id.pickuser_card_checkbox);
        }

        /**
         * Sets the values of the layout's elements.
         *
         * @param context The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
         * @param user    Current user data
         */
        public void setUserData(Context context, IUserData user) {
            username.setText(user.getName());
            phoneNumber.setText(user.getPhoneNumber());
            checkBox.setChecked(false);
        }

        /**
         * Sets a listener to the cardView
         */
        public void setCardViewListener() {
            //TODO ("TO BE IMPLEMENTED")
        }
    }
}
