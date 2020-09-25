package com.goayo.debtify.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.view.DetailedGroupActivity;
import com.goayo.debtify.viewModel.GroupsViewModel;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * RecyclerView adapter for the group cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 *
 * 2020/09/18 Modified by Alex Phu and Olof Sjögren. Changed type Set to Array, conversion is done outside instead.
 *
 * 2020/09/25 Modified by Alex Phu, Oscar Sanner and Olof Sjögren: Injected viewModel through constructor in order to
 * set currentGroupData.
 */
public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewAdapter.GroupViewHolder> {

    private final Context context;
    private IGroupData[] groupData;
    private GroupsViewModel viewModel;
    /**
     * Constructor for GroupViewAdapter
     * @param context The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
     * @param groupData The data to be displayed.
     */
    public GroupViewAdapter(Context context, IGroupData[] groupData, GroupsViewModel viewModel) {
        this.context = context;
        this.groupData = groupData;
        this.viewModel = viewModel;
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
        return new GroupViewHolder(view, viewModel);
    }

    /**
     * Binds the data to the ViewHolder
     * @param holder ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, final int position) {
        //TODO ("Change set to array for userData (constructor)")
        //Temporary solution of converting the HashSet to Array, so that we can index it.
        holder.setGroupData(groupData[position]);
        holder.setCardViewListener(groupData[position].getGroupID(), viewModel, context);
    }

    @Override
    public int getItemCount() {
        return groupData.length;
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
        public GroupViewHolder(@NonNull View itemView, GroupsViewModel viewModel) {
            super(itemView);
            groupName = itemView.findViewById(R.id.pickuser_card_name_textview);
            balance = itemView.findViewById(R.id.group_card_balance_textview);
            cardView = itemView.findViewById(R.id.group_cardView);
        }

        /**
         * Sets the values of the layout's elements.
         * @param group Current group data
         */
        public void setGroupData(IGroupData group) {
            groupName.setText(group.getGroupName());
            //TODO ("SET BALANCE IN GROUP_CARDVIEW")
            balance.setText("YET_TO_BE_SET");
        }

        /**
         * Sets a listener to the cardView
         * @param groupID
         * @param viewModel
         */
        public void setCardViewListener(final String groupID, final GroupsViewModel viewModel, final Context context) {
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    viewModel.setCurrentGroup(groupID);
                    Intent intent = new Intent(context, DetailedGroupActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }
}
