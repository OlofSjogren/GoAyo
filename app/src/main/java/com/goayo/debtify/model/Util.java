package com.goayo.debtify.model;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Yenan Wang
 * @date 2020-10-16
 * <p>
 * Singleton utility class that performs pointless calculations for other classes
 */
public enum Util {
    ;

    /**
     * Convert a Set of IUserData to a Set of String
     *
     * @param userSet The Set of users to be converted
     * @return A Set of String where the String represents the users' phone numbers
     */
    public static Set<String> convertToUserPhoneNumberSet(Set<IUserData> userSet) {
        Set<String> userNameSet = new HashSet<>();
        for (IUserData user : userSet) {
            userNameSet.add(user.getPhoneNumber());
        }
        return userNameSet;
    }

    /**
     * Performs the mathematical calculation setA\setB,
     * if a user exists in setA and not setB, the user is added to the return value,
     * if a user exists in setB and not setA, the user is omitted,
     * if a user exists both in setA and setB, the user is omitted
     *
     * @param setA The minuend
     * @param setB The subtrahend
     * @return The difference of setA and setB
     */
    public static Set<IUserData> getUserSetDifference(Set<IUserData> setA, Set<IUserData> setB) {
        Set<IUserData> difference = new HashSet<>();
        boolean userExistsInB;
        for (IUserData userInA : setA) {
            userExistsInB = false;
            for (IUserData userInB : setB) {
                if (userInB.getPhoneNumber().equals(userInA.getPhoneNumber())) {
                    userExistsInB = true;
                    break;
                }
            }

            if (!userExistsInB) {
                difference.add(userInA);
            }
        }
        return difference;
    }
}
