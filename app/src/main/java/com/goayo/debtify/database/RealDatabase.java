package com.goayo.debtify.database;

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

import java.math.BigDecimal;
import java.net.ConnectException;
import java.util.Map;
import java.util.Set;

public class RealDatabase implements IDatabase {

    private final DbGroupsFetcher groupsFetcher;
    private final DbUserFetcher userFetcher;
    private final DbDebtsFetcher debtsFetcher;
    private final DbPaymentFetcher paymentFetcher;
    private final DbContactsFetcher contactsFetcher;

    public RealDatabase(){
        groupsFetcher = new DbGroupsFetcher();
        userFetcher = new DbUserFetcher();
        debtsFetcher = new DbDebtsFetcher();
        paymentFetcher = new DbPaymentFetcher();
        contactsFetcher = new DbContactsFetcher();
    }

    @Override
    public JsonString.GroupArrayJsonString getGroups(String phoneNumber) throws UserNotFoundException, ConnectException {
        return new JsonString.GroupArrayJsonString(groupsFetcher.fetchGroupsForUser(phoneNumber));
    }

    @Override
    public JsonString.UserJsonString getUser(String phoneNumber) throws UserNotFoundException, ConnectException {
        return new JsonString.UserJsonString(userFetcher.fetchUserFromPhoneNumber(phoneNumber));
    }

    @Override
    public void registerUser(String phoneNumber, String password, String name) throws ConnectException, RegistrationException {
        userFetcher.registerUser(phoneNumber, password, name);
    }

    @Override
    public void registerGroup(String name, Set<String> usersPhoneNumber, String id) throws RegistrationException, ConnectException {
        groupsFetcher.registerGroup(name, usersPhoneNumber, id);
    }

    @Override
    public void addDebt(String groupID, String lender, Map<IUserData, String> borrowers, BigDecimal amount, String description, IDebtSplitStrategy splitStrategy) throws GroupNotFoundException, UserNotFoundException, ConnectException, InvalidDebtException {
        debtsFetcher.addDebt(groupID, lender, borrowers, amount, description, splitStrategy);
    }

    @Override
    public void addContact(String userPhoneNumber, String phoneNumberOfContactToBeAdded) throws UserNotFoundException, ConnectException {
        contactsFetcher.addContact(userPhoneNumber, phoneNumberOfContactToBeAdded);
    }

    @Override
    public void removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved) throws UserNotFoundException, ConnectException {
        contactsFetcher.removeContact(userPhoneNumber, phoneNumberOfContactToBeRemoved);
    }

    @Override
    public void addPayment(String groupID, String debtID, BigDecimal amount, String id) throws GroupNotFoundException, InvalidDebtException, InvalidPaymentException, ConnectException {
        paymentFetcher.addPayment(groupID, debtID, amount, id);
    }

    @Override
    public void addUserToGroup(String groupID, String phoneNumber) throws UserNotFoundException, GroupNotFoundException, ConnectException, UserAlreadyExistsException {
        groupsFetcher.addUserToGroup(groupID, phoneNumber);
    }

    @Override
    public JsonString.UserJsonString getUserToBeLoggedIn(String phoneNumber, String password) throws LoginException, ConnectException {
        return new JsonString.UserJsonString(userFetcher.logInUser(phoneNumber, password));
    }

    @Override
    public JsonString.UserArrayJsonString getContactList(String phoneNumber) throws UserNotFoundException {
        return new JsonString.UserArrayJsonString(contactsFetcher.getContactList(phoneNumber));
    }

    @Override
    public void removeUserFromGroup(String phoneNumber, String groupID) throws UserNotFoundException, GroupNotFoundException, ConnectException {
        groupsFetcher.removeUserFromGroup(phoneNumber, groupID);
    }
}
