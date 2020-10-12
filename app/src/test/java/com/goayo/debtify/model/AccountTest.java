package com.goayo.debtify.model;

import com.goayo.debtify.Database.RealDatabase;
import com.goayo.debtify.modelaccess.IGroupData;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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