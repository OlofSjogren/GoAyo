package com.goayo.debtify.model;

import com.google.gson.Gson;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FromJsonFactory {
    private Gson gson;

    public FromJsonFactory(){
        gson = new Gson();
    }

    public User getUser(JsonString.UserJsonString userToBeLoggedIn) {
        
        String name = gson.fromJson(userToBeLoggedIn.getJson(), UserJsonObject.class).name;
        String phoneNumber = gson.fromJson(userToBeLoggedIn.getJson(), UserJsonObject.class).phonenumber;
        return EntityFactory.createUser(phoneNumber, name);
    }

    public Set<User> getContactList(JsonString.UserArrayJsonString contactListJson) {
        Set<User> users = new HashSet<>();
        UserJsonObject[] contacts = gson.fromJson(contactListJson.getJson(), ContactsJsonObject.class).contacts;
        for (UserJsonObject object : contacts){
            users.add(getUser(new JsonString.UserJsonString(gson.toJson(object))));
        }
        return users;
    }

    public Set<Group> getGroups(JsonString.GroupArrayJsonString associatedGroupsJson) throws Exception {


        GroupJsonObject[] groupJsonObjects = null;
        try {
            groupJsonObjects = gson.fromJson(associatedGroupsJson.getJson(), GroupsArrayJsonObject.class).groupJsonObjects;
        } catch (Exception e){
            e.printStackTrace();
        }

        Set<Group> groups = new HashSet<>();

        for (GroupJsonObject json : groupJsonObjects){
            groups.add(getGroupFromId(new JsonString.GroupJsonString(gson.toJson(json))));
        }

        return groups;
    }

    public Group getGroupFromId(JsonString.GroupJsonString groupJson) throws Exception {
        GroupJsonObject deSerializedJsonGroup = gson.fromJson(groupJson.getJson(), GroupJsonObject.class);
        Group group = EntityFactory.createGroup(deSerializedJsonGroup.name, deSerializedJsonGroup.id);

        for(UserJsonObject userJsonObject : deSerializedJsonGroup.members){
            group.addUser(getUser(new JsonString.UserJsonString(gson.toJson(userJsonObject))));
        }

        for (DebtJsonObject debtJsonObject : deSerializedJsonGroup.debts){
            User borrower = getUser(new JsonString.UserJsonString(gson.toJson(debtJsonObject.borrower)));
            User lender = getUser(new JsonString.UserJsonString(gson.toJson(debtJsonObject.lender, UserJsonObject.class)));
            Map<User, String> borrowers = new HashMap<>();
            borrowers.put(borrower, debtJsonObject.id);
            group.createDebt(lender, borrowers, new BigDecimal(debtJsonObject.owed), debtJsonObject.description, new EvenSplitStrategy());

            for(PaymentJsonObject paymentJsonObject : debtJsonObject.payments){
                group.payOffDebt(new BigDecimal(paymentJsonObject.amount), debtJsonObject.id);
            }
        }
        return group;
    }


    /**
     * The following classes are static dataclasses used by the GSON library to convert JSON strings
     * to objects readable in java. These classes are used to instantiate the more object oriented
     * model classes. The data they hold mirrors the data contained in the different JSON strings
     * sent out by the database.
     */

    static class GroupJsonObject {
        public GroupJsonObject(String name, String date, String groupId, UserJsonObject[] members, DebtJsonObject[] debts) {
            this.name = name;
            this.date = date;
            this.id = groupId;
            this.members = members;
            this.debts = debts;
        }

        String name;
        String date;
        String id;
        UserJsonObject[] members;
        DebtJsonObject[] debts;
    }

    static class UserJsonObject {

        public UserJsonObject(String name, String phonenumber, String password, String[] contacts) {
            this.name = name;
            this.phonenumber = phonenumber;
            this.password = password;
            this.contacts = contacts;
        }

        String name;
        String phonenumber;
        String password;
        String[] contacts;
    }

    static class ContactsJsonObject {
        public ContactsJsonObject(UserJsonObject[] contacts) {
            this.contacts = contacts;
        }
        UserJsonObject[] contacts;
    }

    static class GroupsArrayJsonObject {
        GroupJsonObject[] groupJsonObjects;

        public GroupsArrayJsonObject(GroupJsonObject[] groupJsonObjects) {
            this.groupJsonObjects = groupJsonObjects;
        }
    }

    static class DebtJsonObject {
        public DebtJsonObject(UserJsonObject lender, UserJsonObject borrower, String owed, String debtId, PaymentJsonObject[] payments, String description) {
            this.lender = lender;
            this.borrower = borrower;
            this.owed = owed;
            this.id = debtId;
            this.payments = payments;
            this.description = description;
        }


        String description;
        UserJsonObject lender;
        UserJsonObject borrower;
        String owed;
        String id;
        PaymentJsonObject[] payments;
    }

    static class PaymentJsonObject {
        public PaymentJsonObject(String amount, String paymentId) {
            this.amount = amount;
            this.id = paymentId;
        }

        String amount;
        String id;
    }
}
