package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-10-09
 * <p>
 * Strategy where the owedTotal is split evenly among the borrowers.
 */
public class EvenSplitStrategy implements IDebtSplitStrategy {

    /**
     * owedTotal is split evenly among the borrowers.
     *
     * @param borrowers The selected borrowers.
     * @param owedTotal Amount that the borrowers are owed.
     * @return A map with users and their respective owedTotal.
     */
    @Override
    public Map<User, BigDecimal> splitDebt(Set<User> borrowers, BigDecimal owedTotal) {
        // Splits the owedTotal by the number of borrowers and rounds it to 2 decimals
        BigDecimal splitAmount = owedTotal
                .divide(BigDecimal.valueOf(borrowers.size()), RoundingMode.HALF_UP)
                .round(new MathContext(2));
        Map<User, BigDecimal> tempMap = new HashMap<>();
        for (User borrower : borrowers) {
            tempMap.put(borrower, new BigDecimal(splitAmount.toString()));
        }
        return tempMap;
    }
}
