package com.goayo.debtify.view.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.model.IDebtData;
import com.goayo.debtify.model.IPaymentData;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oscar Sanner, Alex Phu
 * @date 2020-09-22
 * <p>
 * RecyclerView adapter for the tranaction cardviews. Ensures that the correct information are shown on each cardItem.
 * <p>
 * 2020-09-28 Modified by Yenan Wang: add debt description to the cardviews
 * <p>
 * 2020-09-30 Modified by Alex Phu, Yenan Wang: Refactored adapter.
 * <p>
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * <p>
 * 2020-10-09 Modified by Yenan Wang, Alex Phu: Rounded decimals to 2 in balance
 * <p>
 * 2020-09-30 Modified by Alex, Yenan: Refactored adapter.
 * <p>
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * <p>
 * 2020-10-09 Modified by Yenan & Alex: add method updateData(...)
 * <p>
 * 2020-10-14 Modified by Alex Phu: Changed string "owes" to "lends".
 */
public class TransactionCardAdapter extends RecyclerView.Adapter<TransactionCardAdapter.TransactionCardViewHolder> {

    private final List<TransactionData> transactionData;

    /**
     * Contructor for TransactionCardAdapter.
     *
     * @param debtData The data to be displayed.
     */
    public TransactionCardAdapter(List<IDebtData> debtData) {
        transactionData = createTransactionDataSet(debtData);
    }

    /**
     * Creates a new ViewHolder object whenever the RecyclerView needs a new one.
     *
     * @param parent   Parent-view
     * @param viewType View type
     * @return A new instance of TransactionCardViewHolder.
     */
    @NonNull
    @Override
    public TransactionCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.detailed_group_transaction_cardview, parent, false);
        return new TransactionCardViewHolder(view);
    }

    /**
     * Binds the data to the ViewHolder
     *
     * @param holder   ViewHolder
     * @param position Position in the dataArray.
     */
    @Override
    public void onBindViewHolder(@NonNull TransactionCardViewHolder holder, int position) {
        holder.setTransactionTypeData(transactionData.get(position));
        if (transactionData.get(position).transactionType.equals("Payment")) {
            holder.setCardViewColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.greenTransactionCard));
            //TODO: DOES THIS WORK? IF COLORS WONT CHANGE, DEBUG FROM HERE.
        } else {
            holder.setCardViewColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.redTransactionCard));
        }
    }

    @Override
    public int getItemCount() {
        return transactionData.size();
    }

    public void updateData(List<IDebtData> debtData) {
        transactionData.clear();
        transactionData.addAll(createTransactionDataSet(debtData));
        notifyItemRangeChanged(0, transactionData.size());
    }

    private List<TransactionData> createTransactionDataSet(List<IDebtData> debtDataArray) {
        List<TransactionData> transactionDataList = new ArrayList<>();
        for (IDebtData debtData : debtDataArray) {
            transactionDataList.add(new TransactionData(debtData.getDate(), debtData.getDescription(), "Debt", debtData.getLender().getName() + " lent " + debtData.getBorrower().getName(), debtData.getOriginalDebt()));
            for (IPaymentData paymentData : debtData.getPaymentHistory()) {
                transactionDataList.add(new TransactionData(paymentData.getDate(), debtData.getDescription(), "Payment", debtData.getBorrower().getName() + " paid " + debtData.getLender().getName(), paymentData.getPaidAmount()));
            }
        }
        return transactionDataList;
    }

    /**
     * @author Alex Phu, Oscar Sanner
     * @date 2020-09-18
     * <p>
     * ViewHolder for TransactionCardViewHolder
     * <p>
     * 2020-09-28 Modified by Alex: Substituted cardview variable with ConstraintLayout (to set background colour)
     */
    static class TransactionCardViewHolder extends RecyclerView.ViewHolder {
        private final TextView transactionType;
        private final TextView lenderBorrowerDescription;
        private final TextView date;
        private final TextView balance;
        private final ConstraintLayout layout;

        /**
         * Binds the elements in the layout file to a variable
         *
         * @param itemView In this case, TransactionCardView
         */
        public TransactionCardViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionType = itemView.findViewById(R.id.detailed_group_card_transaction_type_textView);
            lenderBorrowerDescription = itemView.findViewById(R.id.detailed_group_card_lender_borrower_description_textView);
            date = itemView.findViewById(R.id.detailed_group_card_date_textView);
            balance = itemView.findViewById(R.id.detailed_group_card_balance);
            layout = itemView.findViewById(R.id.detailed_group_card_constraintLayout);
        }

        /**
         * Sets the values of the layout's elements.
         *
         * @param transaction Current transaction data
         */
        public void setTransactionTypeData(TransactionData transaction) {
            transactionType.setText(transaction.transactionType + ": " + transaction.description);
            lenderBorrowerDescription.setText(transaction.lenderBorrowerDescription);
            date.setText(convertDateToString(transaction.date));
            balance.setText(transaction.balance + "kr");

        }

        private String convertDateToString(Date date) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        }

        private void setCardViewColor(int color) {
            layout.setBackgroundColor(color);
        }
    }

    private static class TransactionData {
        final Date date;
        final String description;
        final String transactionType;
        final String lenderBorrowerDescription;
        final BigDecimal balance;

        public TransactionData(Date date, String description, String transactionType, String lenderBorrowerDescription, BigDecimal balance) {
            this.description = description;
            this.date = date;
            this.transactionType = transactionType;
            this.lenderBorrowerDescription = lenderBorrowerDescription;
            this.balance = balance.setScale(2, RoundingMode.HALF_UP);
        }
    }
}
