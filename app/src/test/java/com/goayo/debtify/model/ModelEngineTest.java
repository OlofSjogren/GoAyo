package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IGroupData;
import com.goayo.debtify.modelaccess.IUserData;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ModelEngineTest {

    static Map<String, String> passwordAndNumber;
    static ModelEngine modelEngine = ModelEngine.getInstance();
    static Set<String> someNumbers;
    static private int amountOfUsers = 5;



    @BeforeClass
    public static void setUp() throws Exception {
        passwordAndNumber = new HashMap<>();
        someNumbers = new HashSet<>();

        for (int i = 0; i < amountOfUsers; i++) {
            int randomNum = ThreadLocalRandom.current().nextInt(10000, 99999);
            int randomNumComp = ThreadLocalRandom.current().nextInt(10000, 99999);
            passwordAndNumber.put(Integer.toString(i), randomNum + Integer.toString(randomNumComp));
        }

        registerAllUsersInHashSet();

        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            logInAndDoStuff(entry);
            modelEngine.logOutUser();
        }
    }

    private static void registerAllUsersInHashSet() throws RegistrationException, ConnectException {
        int i = 1;
        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            modelEngine.registerUser(entry.getValue(), "TestUser" + i, entry.getKey());
            i++;
        }
    }

    private static void logInAndDoStuff(Map.Entry<String, String> entry) throws Exception {
        int amountOfContactsToBeCreated = 3;
        int amountOfGroupsToBeCreated = 2;
        int amountOfDebtsToBeCreated = 2;
        int amountOfPaymentsToBeCreated = 1;


        //Order of calls is important here.
        modelEngine.logInUser(entry.getValue(), entry.getKey());
        addSomeContacts(amountOfContactsToBeCreated);
        createSomeGroups(amountOfGroupsToBeCreated);
        createSomeDebts(amountOfDebtsToBeCreated);
        createSomePayments(amountOfPaymentsToBeCreated);

    }

    private static void createSomePayments(int amountOfPaymentsToBeCreated) throws Exception {
        for (int i = 0; i < amountOfPaymentsToBeCreated; i++){
            IGroupData randomGroupData = getRandomGroupForLoggedInUser();
            IDebtData randomDebtData = getRandomDebtFromGroup(randomGroupData);
            if(randomDebtData == null) {
                continue;
            }

            int payOffAmount = ThreadLocalRandom.current().nextInt(0, randomDebtData.getAmountOwed().intValue());
            modelEngine.payOffDebt(new BigDecimal(Integer.toString(payOffAmount)), randomDebtData.getDebtID(), randomGroupData.getGroupID());
        }
    }

    private static void createSomeDebts(int amountOfDebtsToBeCreated) throws Exception {
        for (int i = 0; i < amountOfDebtsToBeCreated; i++){
            IGroupData group = getRandomGroupForLoggedInUser();
            if(group.getIUserDataSet().size() < 2){
                continue;
            }
            Set<String> borrowers = getRandomSubsetOfPhoneNumberStringsFromIGroupData(group);
            int randomAmount = ThreadLocalRandom.current().nextInt(100, 1000);

            modelEngine.createDebt(group.getGroupID(), modelEngine.getLoggedInUser().getPhoneNumber(), borrowers, new BigDecimal(Integer.toString(randomAmount)), "Test", new EvenSplitStrategy());
        }
    }

    private static void createSomeGroups(int amountOfGroupsToBeCreated) throws Exception {
        int contactsSize = modelEngine.getContacts().size();

        for (int i = 0; i < amountOfGroupsToBeCreated; i++) {
            Set<String> phoneNumberSet = new HashSet<>();
            int amount = ThreadLocalRandom.current().nextInt(2, contactsSize);
            for (int t = 0; t < amount; t++) {
                phoneNumberSet.add(modelEngine.getContacts().toArray(new IUserData[contactsSize])[t].getPhoneNumber());
            }
            modelEngine.createGroup("TestGroupBy " + modelEngine.getLoggedInUser().getPhoneNumber(), phoneNumberSet);
        }
    }

    private static void addSomeContacts(int amountOfContactsToBeCreated) throws Exception {
        for (int i = 0; i < amountOfContactsToBeCreated; i++) {
            modelEngine.addContact(getRandomUserFromHashMap().getValue());
        }
    }

    private static IDebtData getRandomDebtFromGroup(IGroupData randomGroupData) {
        if (randomGroupData.getDebts().size() != 0){
            int randomDebtIndex = ThreadLocalRandom.current().nextInt(0, randomGroupData.getDebts().size());
            System.out.println("FOUND A DEBT!");
            return randomGroupData.getDebts().get(randomDebtIndex);
        } else {
            return null;
        }
    }


    private static Set<String> getRandomSubsetOfPhoneNumberStringsFromIGroupData(IGroupData group) {
        System.out.println("Amount of users in " + group.getGroupName() + ": " + group.getIUserDataSet().size());
        int sizeOfBorrowers = ThreadLocalRandom.current().nextInt(1, group.getIUserDataSet().size());

        List<IUserData> toBeShuffled = new ArrayList<>(group.getIUserDataSet());
        Collections.shuffle(toBeShuffled);

        String[] phoneNumbers = new String[sizeOfBorrowers];


        for(int i = 0; i < sizeOfBorrowers; i++){
            phoneNumbers[i] = toBeShuffled.get(i).getPhoneNumber();
        }

        return new HashSet<>(Arrays.asList(phoneNumbers));
    }

    private static IGroupData getRandomGroupForLoggedInUser() {
        int randomGroupIndex = ThreadLocalRandom.current().nextInt(0, modelEngine.getGroups().size());
        return modelEngine.getGroups().toArray(new IGroupData[modelEngine.getGroups().size()])[randomGroupIndex];
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void persistence() throws Exception {
        Map.Entry<String, String> u = getRandomUserFromHashMap();
        String userPassword = u.getKey();
        String userNumber = u.getValue();


        modelEngine.logInUser(userNumber, userPassword);

        String groupName = UUID.randomUUID().toString();

        //Create a fresh group.
        modelEngine.createGroup(groupName, new HashSet<>());

        IGroupData g = null;
        for (IGroupData groupData : modelEngine.getGroups()){
            if(groupData.getGroupName().equals(groupName)){
                g = groupData;
            }
        }
        assertNotNull(g);

        //All contacts will share one loan.
        Set<String> borrowers = new HashSet<>();

        //Add all contacts into the group.
        for(IUserData userData : modelEngine.getContacts()){
            modelEngine.addUserToGroup(userData.getPhoneNumber(), g.getGroupID());
            borrowers.add(userData.getPhoneNumber());
        }

        //Generate a random amount
        int amount = new Random().nextInt(300);

        //Generate a random description
        String description = UUID.randomUUID().toString();

        //Will create a debt in the fresh group.
        modelEngine.createDebt(g.getGroupID(), modelEngine.getLoggedInUser().getPhoneNumber(), borrowers, new BigDecimal(amount + 100), description, new EvenSplitStrategy());
        List<IDebtData> debt = g.getDebts();

        String groupId = g.getGroupID();

        //Create assertion strings.
        List<String> assertionStrings = new ArrayList<>();
        for(int i = 0; i < borrowers.size(); i++){
            assertionStrings.add(debt.get(i).getBorrower().getPhoneNumber() + " OWES " + debt.get(i).getOriginalDebt().toString()  + " DESC: " + debt.get(i).getDescription());
        }

        modelEngine.logOutUser();
        modelEngine.logInUser(userNumber, userPassword);

        //Reset the variables to point to the reloaded groups
        g = modelEngine.getGroup(groupId);
        debt = g.getDebts();

        for(int i = 0; i < borrowers.size(); i++){
            assertTrue(assertionStrings.contains(debt.get(i).getBorrower().getPhoneNumber() + " OWES " + debt.get(i).getOriginalDebt().toString()+ " DESC: " + debt.get(i).getDescription()));
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
    public void leaveGroup() throws Exception {
        for (IGroupData data : modelEngine.getGroups()) {
            modelEngine.leaveGroup(data.getGroupID());
        }
    }


    @Test
    public void addUserToGroup() throws Exception {
        Map.Entry<String, String> user = getRandomUserFromHashMap();
        modelEngine.logInUser(user.getValue(), user.getKey());

        String one = getRandomUserFromHashMap().getValue();
        String two = getRandomUserFromHashMap().getValue();
        modelEngine.addContact(one);
        modelEngine.addContact(two);

        for (IGroupData data : modelEngine.getGroups()) {
            modelEngine.addUserToGroup(one, data.getGroupID());
            modelEngine.addUserToGroup(two, data.getGroupID());
        }
    }

    @Test
    public void removeUserFromGroup() throws Exception {
        Map.Entry<String, String> user = getRandomUserFromHashMap();
        modelEngine.logInUser(user.getValue(), user.getKey());

        IGroupData data = getRandomGroupForLoggedInUser();

        String one = getRandomUserFromHashMap().getValue();
        modelEngine.addContact(one);
        modelEngine.addUserToGroup(one, data.getGroupID());

        modelEngine.removeUserFromGroup(one, data.getGroupID());
    }


    @Test
    public void getLoggedInUser() throws Exception {
        Map.Entry<String, String> user = getRandomUserFromHashMap();
        modelEngine.logInUser(user.getValue(), user.getKey());

        assertNotNull(modelEngine.getLoggedInUser());
    }

    @Test
    public void getGroup() throws Exception {
        Map.Entry<String, String> user = getRandomUserFromHashMap();
        modelEngine.logInUser(user.getValue(), user.getKey());

        IGroupData data = getRandomGroupForLoggedInUser();
        IGroupData fetchedData = modelEngine.getGroup(data.getGroupID());

        assertEquals(data, fetchedData);
    }

    @Test
    public void getContacts() throws Exception {
        Map.Entry<String, String> user = getRandomUserFromHashMap();
        modelEngine.logInUser(user.getValue(), user.getKey());

        Set<IUserData> fetchedSet = modelEngine.getContacts();
        assertNotNull(fetchedSet);
    }

    private static Map.Entry<String, String> getRandomUserFromHashMap() {
        int randomIndex = ThreadLocalRandom.current().nextInt(0, amountOfUsers);
        int i = 0;
        for (Map.Entry<String, String> entry : passwordAndNumber.entrySet()) {
            if (i == randomIndex) {
                return entry;
            } else {
                i++;
            }
        }
        throw new RuntimeException("amount of users never reached, method is either getRandomUserFromHashMap() is poorly written or setUp() is poorly written. Amount of users: " + amountOfUsers + "randomIndex: " + randomIndex);
    }

}