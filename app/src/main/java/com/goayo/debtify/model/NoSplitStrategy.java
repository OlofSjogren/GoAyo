package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date   2020-10-09
 *
 * Strategy where each user owes the owedTotal.
 */
public class NoSplitStrategy implements IDebtSplitStrategy {

    /**
     * Each borrower owes by the given owedTotal.
     * @param borrowers The selected borrowers.
     * @param owedTotal Amount that the borrowers are owed.
     * @return A map with users and their respective owedTotal.
     */
    @Override
    public Map<User, BigDecimal> splitDebt(Set<User> borrowers, BigDecimal owedTotal) {
        Map<User, BigDecimal> tempMap = new HashMap<>();
        for (User borrower : borrowers) {
            tempMap.put(borrower, new BigDecimal(owedTotal.toString()));
        }
        return tempMap;
    }
}
