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
 * <p>
 * 2020-09-21 Modified by Oscar Sanner: Added functionality to remove a user from a group.
 */

class MockDatabase implements IDatabase {

    private List<Group> groups;
    private Map<String, User> users;
    private Map<User, List<User>> userContactLists;

    public MockDatabase() {
        users = new HashMap<>();
        groups = new ArrayList<>();

        users.put("racso", new User("0701234546", "Oscar Sanner"));
        users.put("folo", new User("0786458765", "Olof Sjögren"));
        users.put("xela", new User("0738980732", "Alex Phu"));
        users.put("naney", new User("0701094578", "Yenan Wang"));
        users.put("leirbag", new User("0733387676", "Gabriel Brattgård"));

        HashSet<User> italySet = new HashSet<>();
        italySet.add(users.get("folo"));
        italySet.add(users.get("rasco"));
        italySet.add(users.get("xela"));

        HashSet<User> schoolFriendsSet = new HashSet<>();
        schoolFriendsSet.add(users.get("rasco"));
        schoolFriendsSet.add(users.get("folo"));
        schoolFriendsSet.add(users.get("xela"));
        schoolFriendsSet.add(users.get("naney"));
        schoolFriendsSet.add(users.get("leirbag"));

        HashSet<User> awSet = new HashSet<>();
        awSet.add(users.get("leirbag"));
        awSet.add(users.get("naney"));
        awSet.add(users.get("xela"));

        groups.add(new Group("School friends", "1a705586-238d-4a29-b7af-36dc103bd45a", schoolFriendsSet));
        groups.add(new Group("Trip to Italy", "4116c93e-5542-4b5c-8423-010a901abdce", italySet));
        groups.add(new Group("Afterwork", "d467b5bc-5fa9-4ac2-890d-29a07803d484", awSet));
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
            if(g.getGroupID() == groupID){
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
        return false;
    }

    @Override
    public boolean removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved) {
        return false;
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
