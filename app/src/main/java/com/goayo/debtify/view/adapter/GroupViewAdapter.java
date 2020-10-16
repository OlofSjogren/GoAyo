package com.goayo.debtify.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IGroupData;
import com.goayo.debtify.model.IUserData;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * RecyclerView adapter for the group cardviews. Ensures that the correct information are shown on each cardItem and its respective listeners.
 * <p>
 * 2020/09/18 Modified by Alex Phu and Olof Sjögren. Changed type Set to Array, conversion is done outside instead.
 * <p>
 * 2020/09/25 Modified by Alex Phu, Oscar Sanner and Olof Sjögren: Injected viewModel through constructor in order to
 * set currentGroupData.
 * <p>
 * 2020-09-30 Modified by Alex Phu & Yenan Wang: Add setCommonClickListener and update method
 * <p>
 * 2020-10-08 Modified by Alex Phu: Group-total in cardview now shows actual total debt.
<<<<<<< HEAD
 *
 * 2020-10-10 Modified by Olof Sjögren: Added property for groupmembers on card as well as init for it in setGroupData.
 * Also added switch-statement for coloring text depending on debt situation.
=======
 * <p>
 * 2020-10-09 Modified by Alex Phu, Yenan Wang: Changed notifyItemRangedChanged() to notifyDataSetChanged(), ugliest bug in Android
>>>>>>> a0d9a3992b77d0dd1661aeb4189280403281d5d9
 */
public class GroupViewAdapter extends RecyclerView.Adapter<GroupViewAdapter.GroupViewHolder> {

    private final List<IGroupData> groupData;
    private View.OnClickListener commonClickListener;
    private IGroupData clickedGroup;
    private final String currentLoggedInUsersPhoneNumber;

    /**
     * Constructor for GroupViewAdapter
     *
     * @param groupData The data to be displayed.
     */
    public GroupViewAdapter(List<IGroupData> groupData, String currentLoggedInUsersPhoneNumber) {
        this.groupData = groupData;
        this.currentLoggedInUsersPhoneNumber = currentLoggedInUsersPhoneNumber;
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
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.my_groups_cardview, parent, false);
        return new GroupViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder
     *
     * @param holder   ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, final int position) {
        holder.setGroupData(groupData.get(position), currentLoggedInUsersPhoneNumber);
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
     *
     * @param groupData Groups to display
     */
    public void update(List<IGroupData> groupData) {
        this.groupData.clear();
        this.groupData.addAll(groupData);
        notifyDataSetChanged();
    }

    public IGroupData getClickedGroup() {
        return clickedGroup;
    }

    private void setClickedGroup(IGroupData clickedGroup) {
        this.clickedGroup = clickedGroup;
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
    static class GroupViewHolder extends RecyclerView.ViewHolder {
        private final TextView groupName;
        private final TextView balance;
        private final CardView cardView;
        private final TextView groupMembers;

        /**
         * Binds the elements in the layout file to a variable
         *
         * @param itemView In this case, my_groups_cardview
         */
        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.pickuser_card_name_textview);
            balance = itemView.findViewById(R.id.group_card_balance_textview);
            cardView = itemView.findViewById(R.id.pickuser_cardView);
            groupMembers = itemView.findViewById(R.id.pickuser_card_member_textview);
        }

        /**
         * Sets the values of the layout's elements.
         *
         * @param group Current group data
         */
        @SuppressLint("SetTextI18n")
        public void setGroupData(IGroupData group, String currentLoggedInUsersPhoneNumber) {
            groupName.setText(group.getGroupName());
            StringBuilder sb = new StringBuilder();

            for (IUserData iu : group.getIUserDataSet()){
                sb.append(iu.getName());
                sb.append(", ");
            }

            groupMembers.setText(sb.substring(0, sb.length()-2)); //Make substring of whole string except the last added ", ".
            try {
                int i = group.getUserTotal(currentLoggedInUsersPhoneNumber).compareTo(new BigDecimal(0));
                balance.setText(group.getUserTotal(currentLoggedInUsersPhoneNumber) + " kr");
                switch (i){
                    case 0:
                        balance.setTextColor(balance.getResources().getColor(R.color.dividerGrey));
                        break;
                    case -1:
                        balance.setTextColor(balance.getResources().getColor(R.color.negativeDebtRed));
                        break;
                    case 1:
                        balance.setTextColor(balance.getResources().getColor(R.color.positiveDebtGreen));
                        break;
                }

            } catch (UserNotFoundException ignore) {}
        }

        /**
         * Sets a listener to the cardView
         *
         * @param onClickListener Listener.
         */
        public void setCardViewListener(View.OnClickListener onClickListener) {
            cardView.setOnClickListener(onClickListener);
        }
    }
}
