package com.goayo.debtify.model.database;

import com.goayo.debtify.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @Author Oscar Sanner
 *
 * A mock class to try different database calls on, without connecting to an actual database.
 *
 */

class MockDatabase implements IDatabase {

    private List<Group> groups;
    private List<User> users;


    /**
     * A method for retrieving groups from the database.
     *
     * @param phoneNumber The phone number for the user for who's groups will be returned.
     *
     * @return All groups associated with the phone number sent into the method.
     */
    @Override
    public List<Group> getGroups(String phoneNumber) {

        groups = new ArrayList<>();
        for(int i = 0; i < 3; i ++){
            groups.add(new Group());
        }
        return groups;
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
        return new User(phoneNumber, "Erik Andersson");
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
    public boolean registerGroup(String name, List<User> users) {
        groups.add(new Group());
        return true;
    }
}
