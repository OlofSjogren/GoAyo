package com.goayo.debtify.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IPaymentData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Oscar Sanner, Alex Phu
 * @date 2020-09-22
 * <p>
 * RecyclerView adapter for the tranaction cardviews. Ensures that the correct information are shown on each cardItem.
 *
 * 2020-09-28 Modified by Yenan: add debt description to the cardviews
 */
public class TransactionCardAdapter extends RecyclerView.Adapter<TransactionCardAdapter.TransactionCardViewHolder> {

    private final Context context;
    private TransactionData[] transactionData;

    /**
     * Contructor for TransactionCardAdapter.
     *
     * @param context  The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
     * @param debtData The data to be displayed.
     */
    public TransactionCardAdapter(Context context, IDebtData[] debtData) {
        this.context = context;
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
        final LayoutInflater inflater = LayoutInflater.from(context);
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
        holder.setTransactionTypeData(context, transactionData[position]);
        if (transactionData[position].transactionType.equals("Payment")) {
            holder.setCardViewColor(context.getResources().getColor(R.color.greenTransactionCard));
            //TODO: DOES THIS WORK? IF COLORS WONT CHANGE, DEBUG FROM HERE.
        } else {
            holder.setCardViewColor(context.getResources().getColor(R.color.redTransactionCard));
        }
    }

    @Override
    public int getItemCount() {
        return transactionData.length;
    }

    private TransactionData[] createTransactionDataSet(IDebtData[] debtDataArray) {
        List<TransactionData> transactionDataList = new ArrayList<>();
        for (IDebtData debtData : debtDataArray) {
            transactionDataList.add(new TransactionData(debtData.getDate(), debtData.getDescription(), "Debt", debtData.getLender().getName() + " owes " + debtData.getBorrower().getName(), debtData.getOriginalDebt()));
            for (IPaymentData paymentData : debtData.getPaymentHistory()) {
                transactionDataList.add(new TransactionData(paymentData.getDate(), debtData.getDescription(), "Payment", debtData.getBorrower().getName() + " payed " + debtData.getLender().getName(), paymentData.getPaidAmount()));
            }
        }

        TransactionData[] transactionData = new TransactionData[transactionDataList.size()];
        transactionDataList.toArray(transactionData);
        return transactionData;
    }

    /**
     * @author Alex Phu, Oscar Sanner
     * @date 2020-09-18
     * <p>
     * ViewHolder for TransactionCardViewHolder
     *
     * 2020-09-28 Modified by Alex: Substituted cardview variable with ConstraintLayout (to set background colour)
     */
    class TransactionCardViewHolder extends RecyclerView.ViewHolder {
        private TextView transactionType;
        private TextView lenderBorrowerDescription;
        private TextView date;
        private TextView balance;
        private ConstraintLayout layout;

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
         * @param context     The context which is linked to the Activity (in our case MainActivity) and its lifecycle.
         * @param transaction Current transaction data
         */
        public void setTransactionTypeData(Context context, TransactionData transaction) {
            transactionType.setText(transaction.transactionType + ": " + transaction.description);
            lenderBorrowerDescription.setText(transaction.lenderBorrowerDescription);
            date.setText(convertDateToString(transaction.date));
            balance.setText(transaction.balance + "kr");

        }

        private String convertDateToString(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        }

        private void setCardViewColor(int color) {
            layout.setBackgroundColor(color);
        }
    }

    private class TransactionData {
        Date date;
        String description;
        String transactionType;
        String lenderBorrowerDescription;
        double balance;

        public TransactionData(Date date, String description, String transactionType, String lenderBorrowerDescription, double balance) {
            this.description = description;
            this.date = date;
            this.transactionType = transactionType;
            this.lenderBorrowerDescription = lenderBorrowerDescription;
            this.balance = balance;
        }
    }
}
