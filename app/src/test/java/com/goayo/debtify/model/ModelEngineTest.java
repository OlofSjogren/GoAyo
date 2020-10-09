package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IGroupData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class ModelEngineTest {

    Map<String, String> passwordAndNumber;
    ModelEngine modelEngine = ModelEngine.getInstance();
    Random random;
    Set<String> someNumbers;

    @Before
    public void setUp() throws Exception {
        passwordAndNumber = new HashMap<>();
        random = new Random();
        someNumbers = new HashSet<>();

        for (int i = 0; i < 2; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(10000, 99999);
            int randomNumComp = ThreadLocalRandom.current().nextInt(10000, 99999);
            passwordAndNumber.put(Integer.toString(i), randomNum + Integer.toString(randomNumComp));
        }
        System.out.println("Hello World");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void registerUser() throws Exception {
        modelEngine.registerUser("1111223344", "ExternalOne", "123");
        modelEngine.registerUser("2222334455", "ExternalTwo", "456");
        int i = 0;
        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            modelEngine.registerUser(entry.getValue(), "TestUser" + i, entry.getKey());
            i++;
        }
        logInUser();
    }

    @Test
    public void logInUser() throws Exception {
        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            someNumbers.add(entry.getValue());
            modelEngine.logInUser(entry.getValue(), entry.getKey());
            removeContact();
            addContact();
            createGroup();
            addUserToGroup();
            //removeUserFromGroup();
            //leaveGroup();
            logOutUser();
        }
    }

    @Test
    public void logOutUser() {
        modelEngine.logOutUser();
    }

    @Test
    public void addContact() throws Exception {
        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            modelEngine.addContact(entry.getValue());
        }
    }

    @Test
    public void removeContact() throws Exception {
        int i = 0;
        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            modelEngine.addContact(entry.getValue());
            modelEngine.removeContact(entry.getValue());
            i++;
            if (i == 10) {
                break;
            }
        }
    }

    @Test
    public void createGroup() throws Exception {
        int i = someNumbers.size();
        Set<String> addableUsers = new HashSet<>(someNumbers);
        addableUsers.remove(modelEngine.getLoggedInUser().getPhoneNumber());

        if (i == 1) {
            return;
        }
        modelEngine.createGroup("TestGroup" + i, addableUsers);
    }

    @Test
    public void leaveGroup() throws Exception {
        for (IGroupData data : modelEngine.getGroups()) {
            modelEngine.leaveGroup(data.getGroupID());
        }
    }

    @Test
    public void getGroups() {
        //todo: Nothing tested by above.
    }

    @Test
    public void addUserToGroup() throws Exception {
        modelEngine.addContact("1111223344");
        modelEngine.addContact("2222334455");

        for (IGroupData data : modelEngine.getGroups()) {
            modelEngine.addUserToGroup("1111223344", data.getGroupID());
            modelEngine.addUserToGroup("2222334455", data.getGroupID());
        }

    }

    @Test
    public void removeUserFromGroup() throws Exception {
        for (IGroupData data : modelEngine.getGroups()) {
            modelEngine.removeUserFromGroup("1111223344", data.getGroupID());
            modelEngine.removeUserFromGroup("2222334455", data.getGroupID());
        }
    }

    @Test
    public void createDebt() throws Exception {
        modelEngine.logInUser("6130143699", "0");
        Set<String> borrower = new HashSet<>();
        borrower.add("3294566230");
        String lender = "6130143699";
        String groupId = "6a03c4c8-e963-49bc-8e7f-1ddaf7c67c36";
        BigDecimal amount = new BigDecimal("200");

        modelEngine.createDebt(groupId,lender,borrower,amount,"nope");
        System.out.println("Hello World");
    }

    @Test
    public void payOffDebt() throws Exception {
        modelEngine.logInUser("6130143699", "0");
        modelEngine.payOffDebt(new BigDecimal("20"), "270c616a-4d0c-40d5-b6b7-20fbdde09f7c","6a03c4c8-e963-49bc-8e7f-1ddaf7c67c36");
    }

    @Test
    public void getLoggedInUser() {
    }

    @Test
    public void getGroup() {
    }

    @Test
    public void getContacts() {
    }
}