package com.goayo.debtify.model;

import java.util.HashSet;
import java.util.Set;

public class EntityFactory {
    public static User createUser(String phoneNumber, String name){
        return new User(phoneNumber, name);
    }

    public static Group createGroup(String groupName, String id){
        return new Group(groupName, id, new HashSet<>());
    }
}
