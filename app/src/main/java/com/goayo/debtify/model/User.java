package com.goayo.debtify.model;

import androidx.annotation.Nullable;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing user.
 * <p>
 * 2020-09-16 Modified by Olof & Alex: class implements IUserData.
 * 2020-10-13 Modified by Olof Sjögren:
 */
class User implements IUserData {

    private final String phoneNumber;
    private final String name;

    public User(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

    /**
     * Override of the comparing equals method.
     *
     * @param obj the object which will be compared to the user object.
     * @return true if the compared object is of the User class and has the same phone number. Otherwise false.
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof User) {
            return phoneNumber.equals(((User) obj).phoneNumber);
        }
        return false;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }
}
