package com.goayo.debtify.model.database;

import com.goayo.debtify.model.User;

import java.util.List;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 * <p>
 *
 * An interface implemented by database classes.
 */

public interface IDatabase {
    Group getGroups(String phoneNumber);
    User getUser(String phoneNumber);
    boolean registerUser(String phoneNumber, String password, String name);
    boolean registerGroup(String name, List<User> users);
}
