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

    public static NoSplitStrategy createNoSplitStrategy(){
        return new NoSplitStrategy();
    }
    public static EvenSplitStrategy createEvenSplitStrategy(){
        return new EvenSplitStrategy();
    }
}
