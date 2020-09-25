package com.goayo.debtify.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IDebtData;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author Gabriel Brattgård & Yenan Wang
 * @date 2020-09-25
 * <p>
 * RecyclerView adapter for pick_debt_cardview. Ensures that the correct information are shown on each cardItem and its respective listeners.
 *
 */

public class PickDebtAdapter extends RecyclerView.Adapter<PickDebtAdapter.PickDebtViewHolder> {

    private IDebtData[] debtData;
    private int mSelectedDebt = -1;

    /**
     * Constructor for PickDebtAdapter
     *
     * @param debtData array of IDebtData
     */
    public PickDebtAdapter(IDebtData[] debtData){
        this.debtData = debtData;
    }

    @NonNull
    @Override
    public PickDebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.pick_debt_cardview, parent, false);

        return new PickDebtViewHolder(v);
    }

    /**
     * Binds the data to the ViewHolder.
     *
     * @param holder ViewHolder
     * @param position Current position in the debtData array
     */
    @Override
    public void onBindViewHolder(@NonNull PickDebtViewHolder holder, int position) {
        holder.setDebtData(debtData[position]);
        holder.debtRadioButton.setChecked(position == mSelectedDebt);
    }

    @Override
    public int getItemCount() {
        return debtData.length;
    }


    /**
     * Constructor for the internal class PickDebtViewHolder
     */
    class PickDebtViewHolder extends RecyclerView.ViewHolder {
        private RadioButton debtRadioButton;
        private TextView date;
        private TextView lender;
        private TextView borrower;
        private TextView amount;

        /**
         * Binds the elements in the layout file to variables in this ViewHolder
         *
         * @param itemView In this case, pick_debt_cardview
         */
        public PickDebtViewHolder(@NonNull View itemView) {
            super(itemView);
            debtRadioButton = itemView.findViewById(R.id.debtRadioButton);
            date = itemView.findViewById(R.id.debtDate);
            lender = itemView.findViewById(R.id.debtLender);
            borrower = itemView.findViewById(R.id.debtBorrower);
            amount = itemView.findViewById(R.id.debtAmount);

            //Makes the radiobutton update when pressed.
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSelectedDebt = getAdapterPosition();
                    notifyItemRangeChanged(0, debtData.length);
                }
            };
            itemView.setOnClickListener(listener);
            debtRadioButton.setOnClickListener(listener);
        }

        public void setDebtData(IDebtData debtData) {
            date.setText(debtData.getDate().toString());
            lender.setText(debtData.getLender().getName());
            borrower.setText(debtData.getBorrower().getName());
            amount.setText(String.valueOf(debtData.getAmountOwed()));
        }
    }
}
