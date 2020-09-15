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


    public GroupViewAdapter(Context context, HashSet<IGroupData> groupData) {
        this.context = context;
        this.groupData = groupData;
    }
    
    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_groups_cardview, parent, false);
        return new GroupViewHolder(view);
    }

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

    class GroupViewHolder extends RecyclerView.ViewHolder {
        private TextView groupName;
        private TextView balance;
        private CardView cardView;

        public GroupViewHolder(@NonNull View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.group_card_name_textview);
            balance = itemView.findViewById(R.id.group_card_balance_textview);
            cardView = itemView.findViewById(R.id.group_cardView);
        }

        public void setGroupData(Context context, IGroupData group) {
            groupName.setText(group.getGroupName());
            balance.setText("YET_TO_BE_SET");
        }

        public void setCardViewListener() {
            //TODO ("TO BE IMPLEMENTED")
        }
    }
}
