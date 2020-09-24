package com.goayo.debtify.model;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        AccountTest.class,
        DebtTest.class,
        DebtTrackerTest.class,
        GroupTest.class,
        LedgerTest.class,
        MockDatabaseTest.class,
        PaymentTest.class,
        UserTest.class,
})
public class TestSuite {
}
