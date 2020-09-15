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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * RecyclerView adapter for the group cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 */
public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewAdapter.GroupViewHolder> {

    private final Context context;
    private Set<IGroupData> groupData = new HashSet<>();

    /**
     * Constructor for GroupViewAdapter
     * @param context The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
     * @param groupData The data to be displayed.
     */
    public GroupViewAdapter(Context context, HashSet<IGroupData> groupData) {
        this.context = context;
        this.groupData = groupData;
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     * @param parent Parent-view
     * @param viewType View type
     * @return A new instance of GroupViewHolder
     */
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_groups_cardview, parent, false);
        return new GroupViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder
     * @param holder ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        //Temporary solution of converting the HashSet to Array, so that we can index it.
        IGroupData[] tempData = new IGroupData[groupData.size()];
        groupData.toArray(tempData);

        holder.setGroupData(context, tempData[position]);
    }

    @Override
    public int getItemCount() {
        return groupData.size();
    }

    /**
     * @author Alex Phu, Olof Sjögren
     * @date 2020-09-15
     * <p>
     * ViewHolder for group
     */
    class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private TextView balance;
        private CardView cardView;

        /**
         * Binds the elements in the layout file to a variable
         * @param itemView In this case, my_groups_cardview
         */
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_card_name_textview);
            balance = itemView.findViewById(R.id.group_card_balance_textview);
            cardView = itemView.findViewById(R.id.group_cardView);
        }

        /**
         * Sets the values of the layout's elements.
         * @param context The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
         * @param group Current group data
         */
        public void setGroupData(Context context, IGroupData group) {
            groupName.setText(group.getGroupName());
            balance.setText("YET_TO_BE_SET");
        }

        /**
         * Sets a listener to the cardView
         */
        public void setCardViewListener() {
            //TODO ("TO BE IMPLEMENTED")
        }
    }
}
