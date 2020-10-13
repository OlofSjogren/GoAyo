package com.goayo.debtify.model;

/**
 * @author Alex Phu
 * @date   2020-10-12
 *
 * Static factory for SplitStrategies
 *
 * 2020-10-13 Modified by Olof Sjögren: Removed empty constructor.
 */
public final class DebtSplitFactory {

    public static IDebtSplitStrategy createNoSplitStrategy(){
        return new NoSplitStrategy();
    }
    public static IDebtSplitStrategy createEvenSplitStrategy(){
        return new EvenSplitStrategy();
    }
}
