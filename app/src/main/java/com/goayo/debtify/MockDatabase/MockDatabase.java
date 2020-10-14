package com.goayo.debtify.MockDatabase;

import com.goayo.debtify.model.Tuple;
import com.goayo.debtify.model.DebtException;
import com.goayo.debtify.model.GroupNotFoundException;
import com.goayo.debtify.model.IDatabase;
import com.goayo.debtify.model.IDebtSplitStrategy;
import com.goayo.debtify.model.InvalidDebtException;
import com.goayo.debtify.model.InvalidPaymentException;
import com.goayo.debtify.model.JsonString;
import com.goayo.debtify.model.LoginException;
import com.goayo.debtify.model.RegistrationException;
import com.goayo.debtify.model.UserAlreadyExistsException;
import com.goayo.debtify.model.UserNotFoundException;
import com.goayo.debtify.model.IUserData;
import com.google.gson.Gson;

import java.math.BigDecimal;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MockDatabase implements IDatabase {

    private Gson gson;

    public MockDatabase()  {
        gson = new Gson();
        users = new ArrayList<>();
        groups = new ArrayList<>();
        try {
            registerUser("1231231230", "123", "Olof Sjögren");
            registerUser("1231231231", "123", "Oscar Sanner");
            registerUser("1231231232", "123", "Alex Phu");
            registerUser("1231231233", "123", "Yenan Wang");

            addContact("1231231230", "1231231231");
            addContact("1231231231", "1231231232");
            addContact("1231231232", "1231231233");
            addContact("1231231233", "1231231230");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    List<MockDbObject.User> users;
    List<MockDbObject.Group> groups;

    @Override
    public JsonString.GroupArrayJsonString getGroups(String phoneNumber) throws UserNotFoundException, ConnectException {
        List<MockDbObject.Group> groupsWithUsers = new ArrayList<>();

        for (MockDbObject.Group group : groups) {
            for (MockDbObject.User user : group.members) {
                if (user.phonenumber.equals(phoneNumber)) {
                    groupsWithUsers.add(group);
                    break;
                }
            }
        }

        MockDbObject.Group[] returnArray = new MockDbObject.Group[groupsWithUsers.size()];
        MockDbObject.GroupsArrayJsonObject returnGroupsObject = new MockDbObject.GroupsArrayJsonObject(groupsWithUsers.toArray(returnArray));
        return new JsonString.GroupArrayJsonString(gson.toJson(returnGroupsObject));
    }

    @Override
    public JsonString.GroupJsonString getGroupFromId(String groupID) throws GroupNotFoundException, ConnectException {
        for (MockDbObject.Group g : groups) {
            if (g.id.equals(groupID)) {
                return new JsonString.GroupJsonString(gson.toJson(g));
            }
        }
        throw new GroupNotFoundException("The group wasn't found in the MOCK database");
    }

    @Override
    public JsonString.UserJsonString getUser(String phoneNumber) throws UserNotFoundException, ConnectException {
        for (MockDbObject.User u : users) {
            if (u.phonenumber.equals(phoneNumber)) {
                return new JsonString.UserJsonString(gson.toJson(u));
            }
        }
        throw new UserNotFoundException("The user wasn't found in the MOCK database");
    }

    @Override
    public void registerUser(String phoneNumber, String password, String name) throws ConnectException, RegistrationException {
        users.add(new MockDbObject.User(name, phoneNumber, password, new String[0]));
    }

    @Override
    public void registerGroup(String name, Set<String> usersPhoneNumber, String id) throws RegistrationException, ConnectException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());

        MockDbObject.User[] members = new MockDbObject.User[usersPhoneNumber.size()];
        String[] numbers = usersPhoneNumber.toArray(new String[usersPhoneNumber.size()]);

        for (int i = 0; i < members.length; i++) {
            try {
                JsonString.UserJsonString user = getUser(numbers[i]);
                members[i] = gson.fromJson(user.getJson(), MockDbObject.User.class);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
        groups.add(new MockDbObject.Group(name, date, id, members, new MockDbObject.Debt[0]));
    }

    @Override
    public void addDebt(String groupID, String lender, Map<IUserData, String> borrowers, BigDecimal amount, String description, IDebtSplitStrategy splitStrategy) throws Exception {
        JsonString.UserJsonString lenderJson = getUser(lender);
        Map<IUserData, Tuple<BigDecimal, String>> usersTotalsAndId = splitStrategy.splitDebt(borrowers, amount);
        MockDbObject.Debt[] debts = new MockDbObject.Debt[borrowers.size()];

        int i = 0;
        for (Map.Entry<IUserData, Tuple<BigDecimal, String>> entry : usersTotalsAndId.entrySet()) {
            JsonString.UserJsonString borrowerJson = getUser(entry.getKey().getPhoneNumber());
            MockDbObject.User lenderUser = gson.fromJson(lenderJson.getJson(), MockDbObject.User.class);
            MockDbObject.User borrowerUser = gson.fromJson(borrowerJson.getJson(), MockDbObject.User.class);
            debts[i] = new MockDbObject.Debt(lenderUser, borrowerUser, entry.getValue().getFirst().toString(), entry.getValue().getSecond(), new MockDbObject.Payment[0], description);
            i++;
        }

        for (MockDbObject.Group g : groups) {
            if (g.id.equals(groupID)) {
                MockDbObject.Debt[] newDebts = new MockDbObject.Debt[debts.length + g.debts.length];
                for (int t = 0; t < g.debts.length; t++) {
                    newDebts[t] = g.debts[t];
                }
                for (int r = g.debts.length; r < debts.length + g.debts.length; r++) {
                    newDebts[r] = debts[r - g.debts.length];
                }
                g.debts = newDebts;
                return;
            }
        }
        throw new GroupNotFoundException("Group not found in MOCK database");
    }

    @Override
    public void addContact(String userPhoneNumber, String phoneNumberOfContactToBeAdded) throws UserNotFoundException, ConnectException {
        for (MockDbObject.User u : users) {
            if (u.phonenumber.equals(userPhoneNumber)) {
                String[] newContactList = new String[u.contacts.length + 1];
                for (int i = 0; i < u.contacts.length; i++) {
                    newContactList[i] = u.contacts[i];
                }
                newContactList[u.contacts.length] = phoneNumberOfContactToBeAdded;
                u.contacts = newContactList;
                return;
            }
        }
        throw new UserNotFoundException("User getting the new contact wasn't found in MOCK database");
    }

    @Override
    public void removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved) throws UserNotFoundException, ConnectException {
        for (MockDbObject.User u : users) {
            if (u.phonenumber.equals(userPhoneNumber)) {
                String[] newContactList = new String[u.contacts.length - 1];
                List<String> contacts = new ArrayList<>(Arrays.asList(u.contacts));
                contacts.remove(phoneNumberOfContactToBeRemoved);
                u.contacts = contacts.toArray(newContactList);
                return;
            }
        }
        throw new UserNotFoundException("User getting the new contact wasn't found in MOCK database");
    }

    @Override
    public void addPayment(String GroupID, String debtID, BigDecimal amount, String id) throws GroupNotFoundException, InvalidDebtException, InvalidPaymentException, ConnectException {
        MockDbObject.Payment payment = new MockDbObject.Payment(amount.toString(), id);
        for (MockDbObject.Group g : groups) {
            if (g.id.equals(GroupID)) {
                for (MockDbObject.Debt debt : g.debts) {
                    if (debt.id.equals(debtID)) {
                        List<MockDbObject.Payment> payments = new ArrayList<>(Arrays.asList(debt.payments));
                        payments.add(payment);
                        int size = payments.size();
                        debt.payments = payments.toArray(new MockDbObject.Payment[size]);
                        return;
                    }
                }
            }
        }
        throw new DebtException("Debt not found");
    }

    @Override
    public void addUserToGroup(String groupID, String phoneNumber) throws UserNotFoundException, GroupNotFoundException, ConnectException, UserAlreadyExistsException {
        MockDbObject.User u = gson.fromJson(getUser(phoneNumber).getJson(), MockDbObject.User.class);
        for(MockDbObject.Group g : groups){
            if(g.id.equals(groupID)){
                List<MockDbObject.User> members = new ArrayList<>(Arrays.asList(g.members));
                members.add(u);
                int size = members.size();
                g.members = members.toArray(new MockDbObject.User[size]);
                return;
            }
        }
        throw new GroupNotFoundException("Group not found in MOCK database");
    }

    @Override
    public JsonString.UserJsonString getUserToBeLoggedIn(String phoneNumber, String password) throws LoginException, ConnectException {
        for(MockDbObject.User u : users){
            if (u.phonenumber.equals(phoneNumber) && u.password.equals(password)){
                return new JsonString.UserJsonString(gson.toJson(u));
            }
        }
        throw new LoginException("Wrong password or number");
    }

    @Override
    public JsonString.UserArrayJsonString getContactList(String phoneNumber) throws UserNotFoundException, ConnectException {
        MockDbObject.User u = gson.fromJson(getUser(phoneNumber).getJson(), MockDbObject.User.class);

        MockDbObject.User[] contacts = new MockDbObject.User[u.contacts.length];
        for(int i = 0; i < u.contacts.length; i++) {
            contacts[i] = gson.fromJson(getUser(u.contacts[i]).getJson(), MockDbObject.User.class);
        }
        MockDbObject.ContactsJsonObject contactsJsonObject = new MockDbObject.ContactsJsonObject(contacts);
        return new JsonString.UserArrayJsonString(gson.toJson(contactsJsonObject));
    }

    @Override
    public void removeUserFromGroup(String phoneNumber, String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException {
        for (MockDbObject.Group g : groups){
            if (g.id.equals(groupID)){
                List<MockDbObject.User> members = new ArrayList<>(Arrays.asList(g.members));
                for(MockDbObject.User member : members){
                    if (member.phonenumber.equals(phoneNumber)){
                        members.remove(member);
                        break;
                    }
                }
                int size = members.size();
                g.members = members.toArray(new MockDbObject.User[size]);
                return;
            }
        }
        throw new GroupNotFoundException("Group not found when trying to remove a member from it");
    }

}
