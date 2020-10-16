package com.goayo.debtify.database;

import com.google.gson.Gson;

import java.math.BigDecimal;

 class DbPaymentFetcher {
    public void addPayment(String groupID, String debtID, BigDecimal amount, String id) {
        DbObject.Payment payment = new DbObject.Payment(amount.toString(), id);
        DbObject.PaymentPost post = new DbObject.PaymentPost(groupID, debtID, payment);
        Gson gson = new Gson();
        String data = gson.toJson(post);
        DatabaseConnector.getInstance().postData(data, "payments");
    }
}
