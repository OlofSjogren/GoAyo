package com.goayo.debtify.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Interface used as API for model group data.
 * Meant primarily to be used as a wrapper for model objects fetched from outside the model.
 * <p>
 * 2020-09-16 Modified by Gabriel & Yenan : Changed Set<IDebtData> into List<IDebtData>.
 * <p>
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu & Oscar Sanner : Added getUsersTotal method.
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 */
public interface IGroupData {
    /**
     * @return the id of the group.
     */
    String getGroupID();

    /**
     * @return a set of all the users which are members of the group, each user is of the IUserData type.
     */
    Set<IUserData> getIUserDataSet();

    /**
     * @return the name of the group.
     */
    String getGroupName();

    /**
     * @return a list with the debt data in the group, each debt is of the IDebtData type.
     */
    List<IDebtData> getDebts();

    /**
     * Method for getting the total calculated debt of a user in the group.
     *
     * @param phoneNumber the phone number of the user which net debt is to be calculated.
     * @return the total calculated debt.
     * @throws UserNotFoundException thrown if a user with the given phone number can't be found in the group.
     */
    BigDecimal getUserTotal(String phoneNumber) throws UserNotFoundException;
}
