package com.goayo.debtify.model;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @Author Oscar Sanner and Olof Sjögren.
 * @date 2020-10-09
 *
 * A creation class responsible for taking in un-parsed JSON-Strings of different formats and parsing
 * them to objects fitting the model.
 *
 * 2020-10-14 Modified by Oscar Sanner: Refactored the whole code to make it readable. Removed
 * "GetGroupFromId()" as it was never user. Created smaller reusable helper methods for different tasks,
 * reusable in case we want to bring the "GetGroupFromId()" back, or add another method.
 * 2020-10-16 Modified by Oscar Sanner: Since dates are now stored in database, this class has been extended
 * to handle persistence in dates.
 * 2020-10-16 Modified by Oscar Sanner and Olof Sjögren: Class now throws appropriate exceptions.
 * 2020-10-16 Modified by Alex Phu: Changed addPaymentsFromJsonDebtToGroup() where the date was set to the debt's date, not payment's date
 * 2020-10-22 Modified by Oscar Sanner: Removed constructors from the internal classes as they are not used by Gson at all.
 */

class FromJsonFactory {
    private final Gson gson;

    public FromJsonFactory() {
        gson = new Gson();
    }

    /**
     * Takes in a user JSON string and converts it into a new User object.
     *
     * Pre condition: The Json string has to be formatted according to the specification in the
     *                UserJsonString class documentation.
     *
     * @param userToBeLoggedIn An object containing the correctly formatted JSON-String.
     * @return a new user specified by the given Json-String.
     */

    public User getUser(JsonString.UserJsonString userToBeLoggedIn) {
        UserJsonObject userJsonObject = gson.fromJson(userToBeLoggedIn.getJson(), UserJsonObject.class);

        String name = userJsonObject.name;
        String phoneNumber = userJsonObject.phonenumber;

        return EntityFactory.createUser(phoneNumber, name);
    }

    /**
     * Takes in a user array JSON string and converts it into a set of new User objects.
     *
     * Pre condition: The Json string has to be formatted according to the specification in the
     *                UserArrayJsonString class documentation.
     *
     * @param contactListJson An object containing the correctly formatted JSON-String.
     * @return a set of users specified by the given Json-String.
     */
    public Set<User> getContactList(JsonString.UserArrayJsonString contactListJson) {
        Set<User> users = new HashSet<>();

        UserJsonObject[] contacts = gson.fromJson(contactListJson.getJson(), ContactsJsonObject.class).contacts;

        for (UserJsonObject userJsonObject : contacts) {
            users.add(EntityFactory.createUser(userJsonObject.phonenumber, userJsonObject.name));
        }

        return users;
    }

    /**
     * Takes in a group array JSON string and converts it into a set of new group objects.
     *
     * Pre condition: The Json string has to be formatted according to the specification in the
     *                GroupArrayJsonString class documentation.
     *
     * @param associatedGroupsJson An object containing the correctly formatted JSON-String.
     * @return a set of groups specified by the given Json-String.
     */

    public Set<Group> getGroups(JsonString.GroupArrayJsonString associatedGroupsJson) {

        GroupJsonObject[] groupJsonObjects = gson.fromJson(associatedGroupsJson.getJson(), GroupsArrayJsonObject.class).groupJsonObjects;
        Set<Group> groups = new HashSet<>();

        for (GroupJsonObject groupJson : groupJsonObjects) {

            Group g = EntityFactory.createGroup(groupJson.name, groupJson.id);

            addMembersFromGroupJsonToGroup(groupJson, g);
            addDebtsFromGroupJsonToGroup(groupJson, g);
            addPaymentsFromGroupJsonToGroup(groupJson, g);

            groups.add(g);
        }
        return groups;
    }


    /*
    Here follows the helper classes.
     */
    private void addPaymentsFromGroupJsonToGroup(GroupJsonObject groupJson, Group g) {
        for (DebtJsonObject debt : groupJson.debts){
            addPaymentsFromJsonDebtToGroup(debt, g);
        }
    }

    private void addPaymentsFromJsonDebtToGroup(DebtJsonObject debt, Group g) {
        for (PaymentJsonObject payment : debt.payments){
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy", Locale.US);
            Date date = new Date(0);
            try {
                date = format.parse(payment.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                g.payOffDebt(new BigDecimal(payment.amount), debt.id, date);
            } catch (InvalidPaymentException e) {
                e.printStackTrace();
                System.out.println("ERROR WHEN TRYING TO CONVERT JSON PAYMENTS TO " +
                        "PAYMENTS IN THE MODEL. This might be due to an improper database" +
                        ". Make sure that the database provided to the model adhere to the" +
                        " specifications.");
            }
        }
    }

    private void addDebtsFromGroupJsonToGroup(GroupJsonObject groupJson, Group g) {
        for (DebtJsonObject debt : groupJson.debts){
            Map<User, String> borrower = new HashMap<>();
            borrower.put(getMemberFromPhoneNumber(g, debt.borrower.phonenumber),debt.id);
            SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
            Date date = new Date(0);
            try {
                date = format.parse(debt.date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            try {
                g.createDebt(getMemberFromPhoneNumber(g, debt.lender.phonenumber), borrower, new BigDecimal(debt.owed), debt.description, new EvenSplitStrategy(), date);
            } catch (DebtException e) {
                e.printStackTrace();
                System.out.println("ERROR WHEN TRYING TO CONVERT JSON DEBTS TO " +
                        "DEBTS IN THE MODEL. This might be due to an improper database" +
                        ". Make sure that the database provided to the model adhere to the" +
                        " specifications.");
            }
        }
    }

    private void addMembersFromGroupJsonToGroup(GroupJsonObject groupJson, Group g) {
        for (UserJsonObject user : groupJson.members){
            try {
                g.addUser(EntityFactory.createUser(user.phonenumber, user.name));
            } catch (UserAlreadyExistsException e) {
                e.printStackTrace();
                throw new RuntimeException("Same user was added twice to the same group during initiation of the groups.");
            }
        }
    }

    private User getMemberFromPhoneNumber(Group g, String phoneNumber){
        for (User u : g.getGroupMembers()){
            if(u.getPhoneNumber().equals(phoneNumber)){
                return u;
            }
        }
        throw new RuntimeException("Error in initialization of groups, in " + this.getClass() + " \n" +
                                   "This is likely due to the database sending incorrect data and \n" +
                                   " not a problem with the OO-Model.");
    }


    /**
     * The following classes are static dataclasses used by the GSON library to convert JSON strings
     * to objects readable in java. These classes are used to instantiate the more sofisticated
     * object oriented model classes. Their data format mirrors the data format contained in the
     * different JSON strings sent out by the database.
     */

    static class GroupJsonObject {

        String name;
        String date;
        String id;
        UserJsonObject[] members;
        DebtJsonObject[] debts;
    }

    static class UserJsonObject {

        String name;
        String phonenumber;
        String password;
        String[] contacts;
    }

    static class ContactsJsonObject {
        UserJsonObject[] contacts;
    }

    static class GroupsArrayJsonObject {
        GroupJsonObject[] groupJsonObjects;
    }

    static class DebtJsonObject {

        String date;
        String description;
        UserJsonObject lender;
        UserJsonObject borrower;
        String owed;
        String id;
        PaymentJsonObject[] payments;
    }

    static class PaymentJsonObject {

        String date;
        String amount;
        String id;
    }
}
