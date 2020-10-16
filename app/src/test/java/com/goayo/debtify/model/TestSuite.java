package com.goayo.debtify.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        DebtTest.class,
        DebtTrackerTest.class,
        GroupTest.class,
        ModelEngineTest.class,
        LedgerTest.class,
        PaymentTest.class,
        UserTest.class,
})
public class TestSuite {
}
