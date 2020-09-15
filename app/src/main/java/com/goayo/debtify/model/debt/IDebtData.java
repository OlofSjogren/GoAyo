package com.goayo.debtify.model.debt;

import java.util.Date;
import java.util.List;


/**
 * @author Gabriel Brattgård, Yenan Wang
 * @date 2020-09-15
 * <p>
 * Interface for accessing debt data
 */
public interface IDebtData {
    String getDebtID();
    //TODO: create IUserData
    IUserData getLender();
    IUserData getBorrower();
    double getOwed();
    List<IPaymentData> getPaymentHistory();
    Date getDate();
}
