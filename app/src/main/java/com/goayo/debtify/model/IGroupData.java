package com.goayo.debtify.model;

import java.math.BigDecimal;
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
 * 2020-10-05 Modified by Oscar Sanner and Olof Sjögren: Switched all them doubles to them BigDecimals, and made sure all the
 * return types and params of methods are correctly set as BigDecimal.
 */
public interface IGroupData {
    String getGroupID();

    Set<IUserData> getIUserDataSet();

    String getGroupName();

    List<IDebtData> getDebts();

    BigDecimal getUserTotal(String phoneNumber) throws UserNotFoundException;
}
