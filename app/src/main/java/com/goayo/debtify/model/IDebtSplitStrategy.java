package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-10-09
 * <p>
 * Strategy interface for splitting up debts.
 * Meant primarily to be used as a wrapper for model objects fetched from outside the model.
 * <p>
 * 2020-10-13 Modified by Oscar Sanner: Changed the parameters of the function to become generic for IUserData
 * It's now usable outside of the Model. Also returns a tuple with an ID and the total amount for the user.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
public interface IDebtSplitStrategy {

    /**
     * Method for splitting a debt between users. Concrete implementation determines the actual split strategy.
     *
     * @param borrowers the borrowers which the debt will be split among.
     * @param owedTotal the debt amount which is to be split among the users.
     * @param <T>       is a subtype of IUserData (or just IUserData) and represents the user among which the debt will be split.
     * @return a Map with the user as the key and a tuple with an ID and the specific user total as the map value.
     */
    <T extends IUserData> Map<T, Tuple<BigDecimal, String>> splitDebt(Map<T, String> borrowers, BigDecimal owedTotal);
}
