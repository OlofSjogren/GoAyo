package com.goayo.debtify.model;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Interface used as API for model user data.
 * Meant primarily to be used as a wrapper for model objects fetched from outside the model.
 * <p>
 * 2020-10-14 Modified by Olof Sjögren: Updated JDocs.
 * 2020-10-15 Modified by Yenan Wang & Alex Phu: interface now extends Comparable
 */
public interface IUserData extends Comparable<IUserData> {

    /**
     * @return the name of the user.
     */
    String getName();

    /**
     * @return the phone number of the user.
     */
    String getPhoneNumber();
}
