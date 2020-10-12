package com.goayo.debtify.model;

/**
 * @author Alex Phu
 * @date   2020-10-12
 *
 * Static factory for SplitStrategies
 */
public final class DebtSplitFactory {

    private DebtSplitFactory() {
    }

    public static IDebtSplitStrategy createNoSplitStrategy(){
        return new NoSplitStrategy();
    }
    public static IDebtSplitStrategy createEvenSplitStrategy(){
        return new EvenSplitStrategy();
    }
}
