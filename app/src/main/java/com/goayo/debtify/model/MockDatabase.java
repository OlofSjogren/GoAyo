package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IUserData;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @Author Oscar Sanner
 * @date 2020-09-15
 *
 * <p>
 * A mock class to try different database calls on, without connecting to an actual database.
 */

class MockDatabase implements IDatabase {

    private Set<Group> groups;
    private Set<User> users;
    private Set<User> contactList;
    private User noFriendsUser;
    private User userToBeLoggedIn;

    public MockDatabase(){
        users = new HashSet<>();
        userToBeLoggedIn = new User("0756415987", "Rolf Broberg");
        users.add(new User("0704345621", "Olle Johansson"));
        users.add(new User("0735216752", "Rickard Nicklasson"));
        users.add(new User("0734355982", "Gabriel Phu"));
        users.add(new User("0773345654", "Yenan Sjögren"));
        users.add(userToBeLoggedIn);
        noFriendsUser = new User("0876123221", "Bo Lean");

        groups = new HashSet<>();
        groups.add(new Group("Afterwork", "1002", users));
        groups.add(new Group("Trip To Spain", "1003", users));
        groups.add(new Group("School friends", "1004", users));
        users.add(noFriendsUser);

        contactList = new HashSet<>();

        users.add(new User("0762435058", "Benny Svensson"));
        users.add(new User("0765483856", "Karl Cool"));
        users.add(new User("0769974574", "Emil Lin"));
        users.add(new User("0733517869", "Dante Alighieri"));

        userToBeLoggedIn = new User("0756415987", "Rolf Broberg");

        contactList.add(new User("0762435058", "Benny Svensson"));
        contactList.add(new User("0765483856", "Karl Cool"));
        contactList.add(new User("0769974574", "Emil Lin"));
        contactList.add(new User("0733517869", "Dante Alighieri"));

    }


    /**
     * A method for retrieving groups from the database.
     *
     * @param phoneNumber The phone number for the user for who's groups will be returned.
     *
     * @return All groups associated with the phone number sent into the method.
     */
    @Override
    public Set<Group> getGroups(String phoneNumber) {
        Set<Group> groupsWithSentInPhoneNumber = new HashSet<>();
        for(Group g : groups){
            for(IUserData user : g.getIUserDataSet()){
                if(user.getPhoneNumber().equals(phoneNumber)){
                    groupsWithSentInPhoneNumber.add(g);
                }
            }
        }
        return groupsWithSentInPhoneNumber;
    }

    /**
     * A method which retries a User from the database, if the sent in phone number is associated with a user.
     *
     * @param phoneNumber The phone number for the user which the database will look for.
     *
     * @return The user in the database who has the phone number
     */
    @Override
    public User getUser(String phoneNumber) {
        for (User u : users){
            if(u.getPhoneNumber().equals(phoneNumber)){
                return u;
            }
        }
        return null;
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

        for (User u : users){
            if(u.getPhoneNumber().equals(phoneNumber)){
                return false;
            }
        }

        users.add(new User(phoneNumber, name));
        return true;
    }

    /**
     * A method registering a group to the database.
     *
     * @param name Name of the group to be created in the database.
     * @param users List of users to be associated with the group.
     *
     * @return True if the creation was successful.
     */

    @Override
    public boolean registerGroup(String name, Set<User> users) {
        groups.add(new Group(name, "1234" ,users));
        return true;
    }

    /**
     * Get a user based on the provided phone number and password. If they match.
     *
     * @param phoneNumber The phone number of the user.
     * @param password The password of the user.
     *
     * @return The user with the provided credentials.
     */
    @Override
    public User getUserToBeLoggedIn(String phoneNumber, String password) {
        return userToBeLoggedIn;
    }

    public void removeUserFromGroup(String phoneNumber, String groupID){
        User userToBeRemoved = null;
        for(User u : users){
            if(u.getPhoneNumber().equals(phoneNumber)){
                userToBeRemoved = u;
            }
        }

        for (Group g : getGroups(phoneNumber)){
            if(g.getGroupID().equals(groupID)){
                if(userToBeRemoved != null){
                    groups.remove(userToBeRemoved);
                    g.removeUser(userToBeRemoved); //Todo, wrong dependency order??
                }
                else {
                    //Todo: SomeKind Of exeption??
                }
            }
            //Todo: User not in group exeption.
        }
    }

    @Override
    public Set<User> getContactList(String phoneNumber) {
        return contactList;
    }

}
