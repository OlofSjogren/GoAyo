package com.goayo.debtify.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

/**
 * @author Gabriel Brattgård
 * @date 2020-09-28
 *
 * RecyclerView adapter for UserCardViews. Ensures that the correct information is shown on each
 * cardItem and its respective listeners.
 *
 */
public class ContactsViewAdapter extends RecyclerView.Adapter<ContactsViewAdapter.ContactsViewHolder> {

    private final Context context;
    private IUserData[] userData;

    /**
     * Constructor for ContactsViewAdapter
     * @param context The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
     * @param contactsData The data to be displayed.
     */
    public ContactsViewAdapter(Context context, IUserData[] contactsData) {
        this.context = context;
        this.userData = contactsData;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     * @param parent Parent-view
     * @param viewType View type
     * @return A new instance of ContactsViewHolder
     */
    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.user_cardview, parent, false);
        return new ContactsViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder
     * @param holder ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        //Temporary solution of converting the HashSet to Array, so that we can index it.
        holder.setContactData(userData[position]);
    }

    @Override
    public int getItemCount() {
        return userData.length;
    }

    /**
     * @author Gabriel Brattgård
     * @date 2020-09-28
     * <p>
     * ViewHolder for a contact
     */
    static class ContactsViewHolder extends RecyclerView.ViewHolder {
        private TextView userName;
        private TextView phoneNumber;

        /**
         * Binds the elements in the layout file to a variable
         * @param itemView In this case, user_cardview
         */
        public ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.usernameTextView);
            phoneNumber = itemView.findViewById(R.id.userPhoneNumberTextView);

            //Don't know if this one is needed.
            CardView cardView = itemView.findViewById(R.id.userCardView);
        }

        /**
         * Sets the values of the layout's elements.
         * @param contact Data of the currently selected user in the dataArray.
         */
        public void setContactData(IUserData contact) {
            userName.setText(contact.getName());
            phoneNumber.setText(contact.getPhoneNumber());
        }

        /**
         * Sets a listener to the cardView
         */
        public void setCardViewListener() {
            //TODO ("TO BE IMPLEMENTED")
        }
    }
}
