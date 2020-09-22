package com.goayo.debtify.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.goayo.debtify.R;
import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IPaymentData;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionCardAdapter extends RecyclerView.Adapter<TransactionCardAdapter.TransactionCardViewHolder> {

    private final Context context;
    private TransactionData[] transactionData;

    public TransactionCardAdapter(Context context, IDebtData[] debtData) {
        this.context = context;
        transactionData = createTransactionDataSet(debtData);
    }

    @NonNull
    @Override
    public TransactionCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.detailed_group_transaction_cardview, parent, false);
        return new TransactionCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionCardViewHolder holder, int position) {
        holder.setTransactionTypeData(context, transactionData[position]);
        if(transactionData[position].transactionType.equals("Payment")){
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

    private TransactionData[] createTransactionDataSet(IDebtData[] debtDataArray){
        List<TransactionData> transactionDataList = new ArrayList<>();
        for(IDebtData debtData : debtDataArray){
            transactionDataList.add(new TransactionData(debtData.getDate(), "Debt", debtData.getLender() + " owes " + debtData.getBorrower(), debtData.getOriginalDebt()));
            for(IPaymentData paymentData : debtData.getPaymentHistory()){
                transactionDataList.add(new TransactionData(paymentData.getDate(), "Payment", debtData.getBorrower() + " payed " + debtData.getLender(), paymentData.getPaidAmount()));
            }
        }

        TransactionData[] transactionData = new TransactionData[transactionDataList.size()];
        transactionDataList.toArray(transactionData);
        return transactionData;
    }

    class TransactionCardViewHolder extends RecyclerView.ViewHolder{
        private TextView transactionType;
        private TextView lenderBorrowerDescription;
        private TextView date;
        private TextView balance;
        private CardView cardView;

        //TODO (CONVERT DATE, balance)

        public TransactionCardViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionType = itemView.findViewById(R.id.detailed_group_card_transaction_type_textView);
            lenderBorrowerDescription = itemView.findViewById(R.id.detailed_group_card_lender_borrower_description_textView);
            date = itemView.findViewById(R.id.detailed_group_card_date_textView);
            balance = itemView.findViewById(R.id.detailed_group_card_balance);
            cardView = itemView.findViewById(R.id.detailed_group_cardView);
        }

        public void setTransactionTypeData(Context context, TransactionData transaction) {
            transactionType.setText(transaction.transactionType);
            lenderBorrowerDescription.setText(transaction.lenderBorrowerDescription);
            date.setText(convertDateToString(transaction.date));
            balance.setText(transaction.balance + "kr");

        }

        private String convertDateToString(Date date) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            return format.format(date);
        }

        private void setCardViewColor(int color){
            cardView.setBackgroundColor(color);
        }
    }

    private class TransactionData {
        Date date;
        String transactionType;
        String lenderBorrowerDescription;
        double balance;

        public TransactionData(Date date, String transactionType, String lenderBorrowerDescription, double balance) {
            this.date = date;
            this.transactionType = transactionType;
            this.lenderBorrowerDescription = lenderBorrowerDescription;
            this.balance = balance;
        }
    }
}
