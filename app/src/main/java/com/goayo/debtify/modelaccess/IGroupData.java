package com.goayo.debtify.modelaccess;

import java.util.List;
import java.util.Set;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Interface used as API for model group data.
 *
 * 2020-09-16 Modified by Gabriel & Yenan : Changed Set<IDebtData> into List<IDebtData>.
 */
public interface IGroupData {
    String getGroupID();
    Set<IUserData> getIUserDataSet();
    String getGroupName();
    List<IDebtData> getDebts();
}
