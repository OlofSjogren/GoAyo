package com.goayo.debtify.modelaccess;

import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Interface used as API for model group data.
 */
public interface IGroupData {
    String getGroupID();
    Set<IUserData> getNamesAndPhonenumbersMap();
    String getGroupName();
    Set<IDebtData> getDebts();
}
