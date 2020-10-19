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
import com.goayo.debtify.model.IDebtData;

import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author Gabriel Brattgård & Yenan Wang
 * @date 2020-09-25
 * <p>
 * RecyclerView adapter for pick_debt_cardview. Ensures that the correct information are shown on each cardItem and its respective listeners.
 * <p>
 * 2020-09-28 Modified by Yenan Wang: Add debt description to the cardview
 * <p>
 * 2020-10-08 Modified by Alex Phu: Refactored setDebtData and added configureName() method.
 * <p>
 * 2020-10-09 Modified by Yenan Wang, Alex Phu: Rounded decimals to 2 in balance
 * <p>
 * 2020-10-11 Modified by Alex Phu: Fixed bug in configureName() which would crash if user has not entered a surname. Now also handles lots of edge cases which shouldn't even occur in the first place.
 * Ignores middle names.
 *
 * 2020-10-15 Modified by Yenan Wang & Alex Phu: Adapter now sorts its items
 */

public class PickDebtAdapter extends RecyclerView.Adapter<PickDebtAdapter.PickDebtViewHolder> {

    private final List<IDebtData> debtData;
    private int mSelectedDebt = -1;

    /**
     * Constructor for PickDebtAdapter
     *
     * @param debtData array of IDebtData
     */
    public PickDebtAdapter(List<IDebtData> debtData) {
        this.debtData = debtData;

        Collections.sort(this.debtData);
        Collections.reverse(this.debtData);
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
        holder.setDebtData(debtData.get(position));
        holder.debtRadioButton.setChecked(position == mSelectedDebt);
    }

    @Override
    public int getItemCount() {
        return debtData.size();
    }

    public IDebtData getSelectedDebt() {
        return debtData.get(mSelectedDebt);
    }


    /**
     * Constructor for the internal class PickDebtViewHolder
     */
    class PickDebtViewHolder extends RecyclerView.ViewHolder {
        private final RadioButton debtRadioButton;
        private final TextView date;
        private final TextView lender;
        private final TextView borrower;
        private final TextView amount;
        private final TextView description;

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
                    notifyItemRangeChanged(0, debtData.size());
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
            amount.setText(debtData.getAmountOwed().setScale(2, RoundingMode.HALF_UP) + " kr");
            description.setText(debtData.getDescription());
        }

        private String configureName(String name) {
          //Trims name and removes multiple spaces in between name and surname
          String temporaryNameHolder = name.trim().replaceAll("\\s+", " ");
          if(temporaryNameHolder.contains(" ")){
              //If First name and surname exists
              String[] nameArray = temporaryNameHolder.split(" ");
              StringBuilder sb = new StringBuilder();
              String firstLetterOfSurname = nameArray[nameArray.length-1].substring(0, 1);
              sb.append(nameArray[0]).append(" ").append(firstLetterOfSurname);
              return sb.toString();
          }
          return temporaryNameHolder;
        }
    }
}
