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
 * <p>
 * 2020-10-12 Modified by Alex Phu and Yenan Wang: Changed RoundingMode from HALF_UP to HALF_EVEN.
 * 2020-10-13 Modified by Oscar Sanner: Changed the parameters of the function to become generic for IUserData.
 * It's now usable outside of the Model. Also returns a tuple with an ID and the total amount for the user.
 * 2020-10-13 Modified by Alex Phu: Changed to package private.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
class EvenSplitStrategy implements IDebtSplitStrategy {

    /**
     * Split strategy for splitting the debt (given the owedTotal amount) equally among all given borrowers.
     *
     * @param borrowers The selected borrowers.
     * @param owedTotal The total amount which will be split equally among all users.
     * @param <T>       is a subtype of IUserData (or just IUserData) and represents the user among which the debt will be split.
     * @return a Map with the user as the key and a tuple with an ID and the specific user total as the map value.
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
