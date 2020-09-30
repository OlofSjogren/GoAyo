package com.goayo.debtify.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IUserData;
import com.goayo.debtify.viewmodel.PickUserViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-18
 * <p>
 * RecyclerView adapter for the pickUsers cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 * <p>
 * 2020-09-29 Modified by Alex Phu: Changed userData type from Array to ArrayList.
 * Removed Context, and userData from constructor. Added ViewModel in constructor. Gets data from viewModel instead.
 */
public class PickUserAdapter extends RecyclerView.Adapter<PickUserAdapter.PickUserViewHolder> {
    private List<IUserData> userData;
    private boolean isMultipleChoice;
    private List<Integer> selectedUserPosList;

    /**
     * Default constructor
     *
     * @param userData the userData to adapt
     */
    public PickUserAdapter(List<IUserData> userData) {
        this.userData = userData;
        this.isMultipleChoice = true;
    }

    /**
     * Constructor allowing setting isMultipleChoice from start
     *
     * @param userData the userData to adapt
     * @param isMultipleChoice decides whether to use radiobutton or checkbox
     */
    public PickUserAdapter(List<IUserData> userData, boolean isMultipleChoice) {
        this.userData = userData;
        this.isMultipleChoice = isMultipleChoice;
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
        holder.setMultipleChoice(isMultipleChoice);

        if (!isMultipleChoice) {
            holder.radioButton.setChecked(position == selectedUserPosList.get(0));
        }
    }

    @Override
    public int getItemCount() {
        return userData.size();
    }

    public void setMultipleChoice(boolean isMultipleChoice) {
        this.isMultipleChoice = isMultipleChoice;
    }

    public List<IUserData> getSelectedUser() {
        List<IUserData> userList = new ArrayList<>();
        for (int i : selectedUserPosList) {
            userList.add(userData.get(i));
        }
        return userList;
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
        private RadioButton radioButton;

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
            radioButton = itemView.findViewById(R.id.pickuser_card_radiobutton);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isMultipleChoice) { // if checkbox
                        boolean isChecked = checkBox.isChecked();
                        if (isChecked) {
                            // if the checkbox is already checked
                            // remove the position from the posList
                            selectedUserPosList.remove(getAdapterPosition());
                        } else {
                            // else add the position to the posList
                            selectedUserPosList.add(getAdapterPosition());
                        }
                        // invert the checked status
                        checkBox.setChecked(!checkBox.isChecked());
                    } else { // or if radiobutton
                        // clear whole list so that it may only hold one element
                        selectedUserPosList.clear();
                        selectedUserPosList.add(getAdapterPosition());
                        // update the items to reflect the change
                        notifyItemRangeChanged(0, userData.size());
                    }
                }
            });
        }

        /**
         * Sets the values of the layout's elements.
         *
         * @param user Current user data
         */
        public void setUserData(IUserData user) {
            username.setText(user.getName());
            phoneNumber.setText(user.getPhoneNumber());
            checkBox.setChecked(false);
        }

        public void setMultipleChoice(boolean isMultipleChoice) {
            if (isMultipleChoice) {
                checkBox.setVisibility(View.VISIBLE);
                radioButton.setVisibility(View.INVISIBLE);
            } else {
                checkBox.setVisibility(View.INVISIBLE);
                radioButton.setVisibility(View.VISIBLE);
            }
        }
    }
}
