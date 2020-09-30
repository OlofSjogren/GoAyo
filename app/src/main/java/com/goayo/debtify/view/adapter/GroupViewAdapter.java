package com.goayo.debtify.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IGroupData;

import java.util.List;

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

    private List<IGroupData> groupData;
    private View.OnClickListener commonClickListener;
    private IGroupData clickedGroup;
    /**
     * Constructor for GroupViewAdapter
     * @param groupData The data to be displayed.
     */
    public GroupViewAdapter(List<IGroupData> groupData) {
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
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_groups_cardview, parent, false);
        return new GroupViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder
     * @param holder ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, final int position) {
        holder.setGroupData(groupData.get(position));
        holder.setCardViewListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClickedGroup(groupData.get(position));
                commonClickListener.onClick(view);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupData.size();
    }

    /**
     * Updates the RecyclerView with new data.
     * @param groupData Groups to display
     */
    public void update(List<IGroupData> groupData) {
        this.groupData.clear();
        this.groupData.addAll(groupData);
        notifyItemRangeChanged(0, groupData.size());
    }

    private void setClickedGroup(IGroupData clickedGroup) {
        this.clickedGroup = clickedGroup;
    }

    public IGroupData getClickedGroup() {
        return clickedGroup;
    }

    /**
     * set a common click listener for all items
     *
     * @param commonClickListener the listener to be added
     */
    public void setCommonClickListener(View.OnClickListener commonClickListener) {
        this.commonClickListener = commonClickListener;
        notifyDataSetChanged();
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
            groupName = itemView.findViewById(R.id.pickuser_card_name_textview);
            balance = itemView.findViewById(R.id.group_card_balance_textview);
            cardView = itemView.findViewById(R.id.pickuser_cardView);
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
         * @param onClickListener Listener.
         */
        public void setCardViewListener(View.OnClickListener onClickListener) {
            cardView.setOnClickListener(onClickListener);
        }
    }
}
