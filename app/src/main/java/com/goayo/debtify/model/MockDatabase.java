package com.goayo.debtify.model;

import com.goayo.debtify.modelaccess.IUserData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 *
 * @Author Oscar Sanner
 * @date 2020-09-15
 *
 * <p>
 * A mock class to try different database calls on, without connecting to an actual database.
 * 
 * 2020-09-21 Modified by Oscar Sanner: Added functionality to remove a user from a group.
 */

class MockDatabase implements IDatabase {

    private List<Group> groups;
    private Map<String, User> users;

    public MockDatabase(){
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

        groups.add(new Group("School friends", UUID.randomUUID().toString(), schoolFriendsSet));
        groups.add(new Group("Trip to Italy", UUID.randomUUID().toString(), italySet));
        groups.add(new Group("Afterwork", UUID.randomUUID().toString(), awSet));
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
    public boolean registerGroup(String name, Set<String> users) {
        groups.add(new Group(name, "1234", null));
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
