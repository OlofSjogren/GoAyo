package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-10-09
 * <p>
 * Strategy where the owedTotal is split evenly among the borrowers.
 *
 * 2020-10-12 Modified by GoAyo: Changed RoundingMode from HALF_UP to HALF_EVEN
 * Modified by Oscar Sanner: Changed the parameters of the function to become generic for IUserData
 * It's now usable outside of the Model. Also returns a tuple with an ID and the total amount for the user.
 * 2020-10-13 Modified by Alex Phu: Changed to package private.
 */
class EvenSplitStrategy implements IDebtSplitStrategy {

    /**
     * owedTotal is split evenly among the borrowers.
     *
     * @param borrowers A map. Each entry contains one borrower and a corresponding debt id for the
     *                  debt to be created.
     * @param owedTotal Amount that the borrowers are owed.
     * @return A map with IUserData and a tuple with the total and the ID.
     */
    @Override
    public <T extends IUserData> Map<T, Tuple<BigDecimal, String>> splitDebt(Map<T, String> borrowers, BigDecimal owedTotal) {
        // Splits the owedTotal by the number of borrowers
        BigDecimal splitAmount = owedTotal
                .divide(BigDecimal.valueOf(borrowers.size()), 10, RoundingMode.HALF_EVEN);

        Map<T, Tuple<BigDecimal, String>> tempMap = new HashMap<>();

        for (Map.Entry<T, String> entry : borrowers.entrySet()) {
            tempMap.put(entry.getKey(), new Tuple<>((splitAmount), entry.getValue()));
        }
        return tempMap;
    }
}
