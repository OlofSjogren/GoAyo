package com.goayo.debtify.model;

import androidx.annotation.Nullable;

import com.goayo.debtify.modelaccess.IUserData;

/**
 * @author Alex Phu, Olof Sjögren
 * @date 2020-09-15
 * <p>
 * Class representing user.
 *
 * ---
 * 2020-09-16 Modified by Olof & Alex: class implements IUserData,
 */
public class User implements IUserData {

    private final String phoneNumber;
    private final String name;

    public User(String phoneNumber, String name) {
        this.phoneNumber = phoneNumber;
        this.name = name;
    }

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
