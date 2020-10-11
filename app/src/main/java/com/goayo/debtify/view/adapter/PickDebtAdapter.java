package com.goayo.debtify.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IDebtData;

import java.text.SimpleDateFormat;

/**
 * @author Gabriel Brattgård & Yenan Wang
 * @date 2020-09-25
 * <p>
 * RecyclerView adapter for pick_debt_cardview. Ensures that the correct information are shown on each cardItem and its respective listeners.
 * <p>
 * 2020-09-28 Modified by Yenan Wang: Add debt description to the cardview
 * <p>
 * 2020-10-08 Modified by Alex Phu: Refactored setDebtData and added configureName() method.
 *
 * 2020-10-11 Modified by AlexPhu: Fixed bug in configureName() which would crash if user has not entered a surname. Now also handles lots of edge cases which shouldn't even occur in the first place.
 */

public class PickDebtAdapter extends RecyclerView.Adapter<PickDebtAdapter.PickDebtViewHolder> {

    private IDebtData[] debtData;
    private int mSelectedDebt = -1;

    /**
     * Constructor for PickDebtAdapter
     *
     * @param debtData array of IDebtData
     */
    public PickDebtAdapter(IDebtData[] debtData) {
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
     * @param holder   ViewHolder
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

    public IDebtData getSelectedDebt() {
        return debtData[mSelectedDebt];
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
        private TextView description;

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
            description = itemView.findViewById(R.id.debtDesciption);

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

        @SuppressLint("SetTextI18n")
        public void setDebtData(IDebtData debtData) {
            //Formats the date
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            date.setText(format.format(debtData.getDate()));
            lender.setText(configureName(debtData.getLender().getName()));
            borrower.setText(configureName(debtData.getBorrower().getName()));
            amount.setText(debtData.getAmountOwed() + " kr");
            description.setText(debtData.getDescription());
        }

        private String configureName(String name) {
            //Trims name and removes multiple spaces in between name and surname
            String temporaryNameHolder = name.trim().replaceAll("\\s+", " ");
            if(temporaryNameHolder.contains(" ")){
                //If First name and surname exists
                String[] nameArray = temporaryNameHolder.split(" ");
                StringBuilder sb = new StringBuilder();
                String firstLetterOfSurname = nameArray[1].substring(0, 1);
                sb.append(nameArray[0]).append(" ").append(firstLetterOfSurname);
                return sb.toString();
            }
            return temporaryNameHolder;
        }
    }
}
