package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Alex Phu, Yenan Wang
 * @date   2020-10-09
 *
 * Strategy interface for splitting up debts.
 *
 * Modified by Oscar Sanner: Changed the parameters of the function to become generic for IUserData
 * It's now usable outside of the Model. Also returns a tuple with an ID and the total amount for the user.
 */
public interface IDebtSplitStrategy {
    /**
     * Splits the debts to the borrowers depending on which strategy.
     * @param borrowers The selected borrowers.
     * @param owedTotal Amount that the borrowers are owed.
     * @return
     */
    <T extends IUserData> Map<T, Tuple<BigDecimal, String>> splitDebt(Map<T, String> borrowers, BigDecimal owedTotal);
}
