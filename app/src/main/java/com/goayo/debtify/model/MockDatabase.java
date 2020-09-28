package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IDebtData;
import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 *
 * <p>
 * A mock class to try different database calls on, without connecting to an actual database.
 *
 * <p>
 * 2020-09-21 Modified by Oscar Sanner: Added functionality to remove a user from a group.
 * 2020-09-28 Modified by Oscar Sanner and Olof Sjögren: Added methods for debts, payments and contacts.
 *
 */

class MockDatabase implements IDatabase {

    private List<Group> groups;
    private Map<String, User> users;
    private Map<User, List<User>> userContactLists;

    String pwOscar = "racso";
    String pwOlof = "folo";
    String pwAlex = "xela";
    String pwYenan = "naney";
    String pwGabriel = "leirbag";

    public MockDatabase() {
        users = new HashMap<>();
        groups = new ArrayList<>();

        users.put(pwOscar, new User("0701234546", "Oscar Sanner"));
        users.put(pwOlof, new User("0786458765", "Olof Sjögren"));
        users.put(pwAlex, new User("0738980732", "Alex Phu"));
        users.put(pwYenan, new User("0701094578", "Yenan Wang"));
        users.put(pwGabriel, new User("0733387676", "Gabriel Brattgård"));

        HashSet<User> italySet = new HashSet<>();
        italySet.add(users.get(pwOlof));
        italySet.add(users.get(pwOscar));
        italySet.add(users.get(pwAlex));

        HashSet<User> schoolFriendsSet = new HashSet<>();
        schoolFriendsSet.add(users.get(pwOscar));
        schoolFriendsSet.add(users.get(pwOlof));
        schoolFriendsSet.add(users.get(pwAlex));
        schoolFriendsSet.add(users.get(pwYenan));
        schoolFriendsSet.add(users.get(pwGabriel));

        HashSet<User> awSet = new HashSet<>();
        awSet.add(users.get(pwGabriel));
        awSet.add(users.get(pwYenan));
        awSet.add(users.get(pwAlex));

        groups.add(new Group("School friends", "1a705586-238d-4a29-b7af-36dc103bd45a", schoolFriendsSet));
        groups.add(new Group("Trip to Italy", "4116c93e-5542-4b5c-8423-010a901abdce", italySet));
        groups.add(new Group("Afterwork", "d467b5bc-5fa9-4ac2-890d-29a07803d484", awSet));

        Set<User> olofSet = new HashSet<>();
        olofSet.add(users.get(pwOlof));

        Set<User> oscarSet = new HashSet<>();
        oscarSet.add(users.get(pwOscar));

        Set<User> alexSet = new HashSet<>();
        alexSet.add(users.get(pwAlex));

        Set<User> yenanSet = new HashSet<>();
        yenanSet.add(users.get(pwYenan));

        Set<User> gabrielSet = new HashSet<>();
        gabrielSet.add(users.get(pwGabriel));

        try {
            groups.get(0).createDebt(users.get(pwOlof), oscarSet, 140.5);
            groups.get(0).createDebt(users.get(pwYenan), oscarSet, 140.5);
            groups.get(0).createDebt(users.get(pwGabriel), yenanSet, 90.25);
            groups.get(0).createDebt(users.get(pwGabriel), olofSet, 90.99);
            groups.get(0).createDebt(users.get(pwAlex), gabrielSet, 80);

            groups.get(1).createDebt(users.get(pwOlof), alexSet, 30.89);
            groups.get(1).createDebt(users.get(pwOlof), oscarSet, 30.89);
            groups.get(1).createDebt(users.get(pwOscar), alexSet, 27.09);

            groups.get(2).createDebt(users.get(pwGabriel), alexSet, 20.9);
            groups.get(2).createDebt(users.get(pwGabriel), yenanSet, 30.0);

            String id00 = groups.get(0).getDebts().get(0).getDebtID();
            String id01 = groups.get(0).getDebts().get(1).getDebtID();
            String id02 = groups.get(0).getDebts().get(2).getDebtID();
            String id03 = groups.get(0).getDebts().get(3).getDebtID();
            String id04 = groups.get(0).getDebts().get(4).getDebtID();

            groups.get(0).payOffDebt(50.25, id00);
            groups.get(0).payOffDebt(25, id00);

            String id1_0 = groups.get(1).getDebts().get(0).getDebtID();
            String id1_1 = groups.get(1).getDebts().get(1).getDebtID();
            String id1_2 = groups.get(1).getDebts().get(2).getDebtID();
            groups.get(1).payOffDebt(30.89, id1_0);
            groups.get(1).payOffDebt(27.09, id1_1);
            groups.get(1).payOffDebt(16.90, id1_2);
            groups.get(1).payOffDebt(5.0, id1_2);

            String id2_0 = groups.get(2).getDebts().get(0).getDebtID();
            String id2_1 = groups.get(2).getDebts().get(0).getDebtID();
            groups.get(2).payOffDebt(20.9, id2_0);
            groups.get(2).payOffDebt(20.0, id2_1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * A method for retrieving groups from the database.
     *
     * @param phoneNumber The phone number for the user for who's groups will be returned.
     * @return All groups associated with the phone number sent into the method.
     */
    @Override
    public Set<Group> getGroups(String phoneNumber) {
        Set<Group> groupsWithSentInPhoneNumber = new HashSet<>();
        for (Group g : groups) {
            for (IUserData user : g.getIUserDataSet()) {
                if (user.getPhoneNumber().equals(phoneNumber)) {
                    groupsWithSentInPhoneNumber.add(g);
                }
            }
        }
        return groupsWithSentInPhoneNumber;
    }



    @Override
    public Group getGroupFromId(String groupID) {
        for (Group g : groups){
            if(g.getGroupID().equals(groupID)){
                return g;
            }
        }
        throw new NullPointerException("THE GROUP DOESNT EXIST");

    }

    /**
     * A method which retries a User from the database, if the sent in phone number is associated with a user.
     *
     * @param phoneNumber The phone number for the user which the database will look for.
     * @return The user in the database who has the phone number
     */
    @Override
    public User getUser(String phoneNumber) {
        return getUserFromDatabase(phoneNumber);
    }

        /**
         * A method registering a user to the database
         *
         * @param phoneNumber Phone number for the user to be registered in the database.
         * @param password Password for the user to be registered in the database.
         * @param name Name for the user to be registered in the database.
         *
         * @return true if the registration was successful.
         */
    @Override
    public boolean registerUser(String phoneNumber, String password, String name) {
        User user = getUserFromDatabase(phoneNumber);
        if(user != null){
            return false;
        }
        user = new User(phoneNumber, name);
        users.put(password, user);
        return true;
    }

    /**
     * A method registering a group to the database.
     *
     * @param name  Name of the group to be created in the database.
     * @param users List of users to be associated with the group.
     * @return True if the creation was successful.
     */

    @Override
    public boolean registerGroup(String name, Set<String> users) {
        Set<User> usersToBeAdded = new HashSet<>();
        for(String string : users){
            User user = getUserFromDatabase(string);
            if(user == null){
                return false;
            }
            usersToBeAdded.add(user);
        }
        groups.add(new Group(name, UUID.randomUUID().toString(), usersToBeAdded));
        return true;
    }

    @Override
    public boolean addDebt(String groupID, String lender, Set<String> borrowers, double amount) throws Exception {
        Group group = getGroupFromId(groupID);
        Set<User> borrowersSet = new HashSet<>();
        for(String string : borrowers){
            User user = getUserFromDatabase(string);
            if(user == null){
                return false;
            }
            borrowersSet.add(user);
        }
        group.createDebt(getUserFromDatabase(lender), borrowersSet, amount);
        return true;
    }

    @Override
    public boolean addContact(String userPhoneNumber, String contactToBeAdded) {
        User user = getUserFromDatabase(userPhoneNumber);
        User userToBeAdded = getUserFromDatabase(contactToBeAdded);
        for (Map.Entry<User,List<User>> entry : userContactLists.entrySet()){
            if (entry.getKey().equals(user)){
                return entry.getValue().add(userToBeAdded);
            }
        }
        return false;
        //TODO Add exception here. User couldn't be added.
    }

    @Override
    public boolean removeContact(String userPhoneNumber, String contactToBeRemoved) {
        User user = getUserFromDatabase(userPhoneNumber);
        User userToBeRemoved = getUserFromDatabase(contactToBeRemoved);
        for (Map.Entry<User,List<User>> entry : userContactLists.entrySet()){
            if (entry.getKey().equals(user)){
                return entry.getValue().remove(userToBeRemoved);
            }
        }
        return false;
        //TODO Add exception here. User couldn't be added.
    }

    @Override
    public boolean addPayment(String GroupID, String debtID, double amount) {
        Group group = getGroupFromId(GroupID);
        try {
            group.payOffDebt(amount, debtID);
        } catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean addUserToGroup(String groupID, String phoneNumber) {
        Group group = getGroupFromId(groupID);
        User user = getUserFromDatabase(phoneNumber);
        if(user == null){
            return false;
        }
        group.addUser(user);
        return true;
    }

    /**
     * Get a user based on the provided phone number and password. If they match.
     *
     * @param phoneNumber The phone number of the user.
     * @param password    The password of the user.
     * @return The user with the provided credentials.
     *
     */
    @Override
    public User getUserToBeLoggedIn(String phoneNumber, String password) {
        for (Map.Entry<String,User> passUserSet : users.entrySet()){
            if (passUserSet.getKey().equals(password)){
                return passUserSet.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean removeUserFromGroup(String phoneNumber, String groupID) {
        User userToBeRemoved = getUserFromDatabase(phoneNumber);
        Group group = getGroupFromId(groupID);
        boolean groupRemoveSuccess = false;

        if (userToBeRemoved == null || group == null) {
            //TODO Throw exception here. User doesn't exist in database.
            return false;
        }

        for (Group g : getGroups(phoneNumber)) {
            if (g.getGroupID().equals(groupID)) {
                groupRemoveSuccess = g.removeUser(userToBeRemoved);
                //Todo: Throw exception here. User doesn't exist in group, shouldn't rely on boolean.
            }
        }
        return groupRemoveSuccess;
    }

    @Override
    public Set<User> getContactList(String phoneNumber) {
        User user = getUserFromDatabase(phoneNumber);
        Set<User> contactList = new HashSet<>();
        if (user == null) {
            //TODO Throw exception here
            return null;
        } else {
            contactList = new HashSet<User>(userContactLists.get(user));
        }
        return contactList;
    }

    private User getUserFromDatabase(String phoneNumber){
        for (Map.Entry<String, User> mapElement : users.entrySet()) {
            if(mapElement.getValue().getPhoneNumber().equals(phoneNumber)){
                return mapElement.getValue();
            }
        }
        return null;
    }
}
