package com.goayo.debtify.modelaccess;

import com.goayo.debtify.model.UserNotFoundException;

import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Interface used as API for model group data.
 * <p>
 * 2020-09-16 Modified by Gabriel & Yenan : Changed Set<IDebtData> into List<IDebtData>.
 * <p>
 * 2020-09-25 Modified by Olof Sjögren, Alex Phu & Oscar Sanner : Added getUsersTotal method.
 */
public interface IGroupData {
    String getGroupID();

    Set<IUserData> getIUserDataSet();

    String getGroupName();

    List<IDebtData> getDebts();

    double getUsersTotal(String phoneNumber) throws UserNotFoundException;
}
