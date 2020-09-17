package com.goayo.debtify.model.database;

import com.goayo.debtify.model.Group;
import com.goayo.debtify.model.User;

import java.util.List;
import java.util.Set;

/**
 * @Author Oscar Sanner
 * @date 2020-09-15
 * <p>
 *
 * An interface implemented by database classes.
 */

public interface IDatabase {
    Set<Group> getGroups(String phoneNumber);
    User getUser(String phoneNumber);
    boolean registerUser(String phoneNumber, String password, String name);
    boolean registerGroup(String name, Set<User> users);
    User getUserToBeLoggedIn(String phoneNumber, String password);
}
