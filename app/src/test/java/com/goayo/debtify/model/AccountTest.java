package com.goayo.debtify.model;

import com.goayo.debtify.Database.RealDatabase;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AccountTest {

    static Account account;
    static IDatabase database;


    @Before
    public void beforeClass() throws Exception {
        database = new RealDatabase();
        account = new Account(database);
    }

    @Test
    public void placeHolder(){
        System.out.println("Hello World");
    }
}