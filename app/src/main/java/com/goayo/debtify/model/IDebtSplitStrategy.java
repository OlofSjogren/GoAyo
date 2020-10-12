package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

/**
 * @author Alex Phu, Yenan Wang
 * @date   2020-10-09
 *
 * Strategy interface for splitting up debts.
 */
public interface IDebtSplitStrategy {
    /**
     * Splits the debts to the borrowers depending on which strategy.
     * @param borrowers The selected borrowers.
     * @param owedTotal Amount that the borrowers are owed.
     * @return
     */
    Map<User, BigDecimal> splitDebt(Set<User> borrowers, BigDecimal owedTotal);
}
