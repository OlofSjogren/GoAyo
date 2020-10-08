package com.goayo.debtify.Database;

import com.google.gson.Gson;

import java.math.BigDecimal;

public class DbPaymentFetcher {
    public boolean addPayment(String groupID, String debtID, BigDecimal amount) {
        DbObject.Payment payment = new DbObject.Payment(amount.toString(), "");
        DbObject.PaymentPost post = new DbObject.PaymentPost(groupID, debtID, payment);
        Gson gson = new Gson();
        String data = gson.toJson(post);
        return DatabaseConnector.getInstance().postData(data, "payments");
    }
}
