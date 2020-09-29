package com.goayo.debtify.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.List;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * RecyclerView adapter for the pickUsers cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 *
 * 2020-09-29 Modified by Alex Phu: Changed userData type from Array to ArrayList.
 * Removed Context, and userData from constructor. Added ViewModel in constructor. Gets data from viewModel instead.
 */
public class PickUserAdapter extends RecyclerView.Adapter<PickUserAdapter.PickUserViewHolder> {
    private List<IUserData> userData;
    private final PickUserViewModel viewModel;

    /**
     * Constructor for GroupViewAdapter
     * @param viewModel Handles data fetching from Model.
     */
    public PickUserAdapter(PickUserViewModel viewModel, List<IUserData> userData) {
        this.viewModel = viewModel;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_user_cardview, parent, false);
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
        holder.setUserData(userData.get(position));

        holder.setCardViewListener(viewModel, userData.get(position));
    }

    @Override
    public int getItemCount() {
        return userData.size();
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
         * @param user    Current user data
         */
        public void setUserData(IUserData user) {
            username.setText(user.getName());
            phoneNumber.setText(user.getPhoneNumber());
            checkBox.setChecked(false);
        }

        /**
         * Listener for cardview. Adds the user to the viewModel.
         */
        public void setCardViewListener(PickUserViewModel viewModel, IUserData user) {
            viewModel.setSelectedUsersData(user);
        }
    }
}
