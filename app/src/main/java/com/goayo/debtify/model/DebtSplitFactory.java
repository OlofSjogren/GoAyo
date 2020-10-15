package com.goayo.debtify.model;

/**
 * @author Alex Phu
 * @date 2020-10-12
 * <p>
 * Static factory for SplitStrategies
 * <p>
 * 2020-10-13 Modified by Olof Sjögren: Removed empty constructor.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
public final class DebtSplitFactory {

    /**
     * Method for creating a NoSplitStrategy and returning it with the IDebtSplitStrategy type.
     *
     * @return a new wrapped NoSplitStrategy.
     */
    public static IDebtSplitStrategy createNoSplitStrategy() {
        return new NoSplitStrategy();
    }

    /**
     * Method for creating a EvenSplitStrategy and returning it with the IDebtSplitStrategy type.
     *
     * @return a new wrapped NoSplitStrategy.
     */
    public static IDebtSplitStrategy createEvenSplitStrategy() {
        return new EvenSplitStrategy();
    }
}
