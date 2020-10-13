package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Phu, Yenan Wang
 * @date 2020-10-09
 * <p>
 * Strategy where each user owes the owedTotal.
 * <p>
 * 2020-10-12 Modified by Oscar Sanner: Changed the parameters of the function to become generic for IUserData
 * It's now usable outside of the Model. Also returns a tuple with an ID and the total amount for the user.
 * 2020-10-13 Modified by Alex Phu: Changed to package private.
 */
class NoSplitStrategy implements IDebtSplitStrategy {

    /**
     * Split strategy for creating the same debt (given the owedTotal amount) for each user in the given map.
     *
     * @param borrowers The selected borrowers.
     * @param owedTotal The amount that each borrower will pay.
     * @return A map with users and their respective owedTotal.
     */
    @Override
    public <T extends IUserData> Map<T, Tuple<BigDecimal, String>> splitDebt(Map<T, String> borrowers, BigDecimal owedTotal) {
        Map<T, Tuple<BigDecimal, String>> tempMap = new HashMap<>();

        for (Map.Entry<T, String> entry : borrowers.entrySet()) {
            tempMap.put(entry.getKey(), new Tuple<>(new BigDecimal(owedTotal.toString()), entry.getValue()));
        }
        return tempMap;
    }
}
