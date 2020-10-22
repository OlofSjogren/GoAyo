package com.goayo.debtify.model;

import java.util.HashSet;

/**
 * @author Oscar Sanner and Olof Sjögren.
 * @date 2020-10-09
 * <p>
 * A class with static methods for instantiating new objects.
 *
 * 2020-10-22 Modified by Yenan Wang: Updated code formatting
 */
class EntityFactory {

    /**
     * Creates a new user based on parameters.
     *
     * @param phoneNumber The number of the new user to be created.
     * @param name        The name of the new user to be created.
     * @return A new user with specified properties.
     */
    public static User createUser(String phoneNumber, String name) {
        return new User(phoneNumber, name);
    }

    /**
     * Creates a new group based on parameters.
     *
     * @param groupName The name of the group.
     * @param id        The id of the group.
     * @return A new group with specified properties and no members.
     */
    public static Group createGroup(String groupName, String id) {
        return new Group(groupName, id, new HashSet<>());
    }
}
