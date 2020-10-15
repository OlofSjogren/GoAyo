package com.goayo.debtify.database;

import com.google.gson.Gson;

class DbContactsFetcher {
    public void removeContact(String userPhoneNumber, String phoneNumberOfContactToBeRemoved) {
        DbObject.ContactPost post = new DbObject.ContactPost(userPhoneNumber, phoneNumberOfContactToBeRemoved);
        Gson gson = new Gson();
        String data = gson.toJson(post);
        DatabaseConnector.getInstance().postData(data, "users/removeContact");
    }

    public void addContact(String userPhoneNumber, String contactToBeAdded) {
        DbObject.ContactPost post = new DbObject.ContactPost(userPhoneNumber, contactToBeAdded);
        Gson gson = new Gson();
        String data = gson.toJson(post);
        DatabaseConnector.getInstance().postData(data, "users/addContact");
    }

    public String getContactList(String phoneNumber) {
        DbUserFetcher fetcher = new DbUserFetcher();
        String user = fetcher.fetchUserFromPhoneNumber(phoneNumber);
        Gson gson = new Gson();

        DbObject.User u = gson.fromJson(user, DbObject.User.class);
        DbObject.User[] users = new DbObject.User[u.contacts.length];
        for (int i = 0; i < u.contacts.length; i++) {
            users[i] = gson.fromJson(fetcher.fetchUserFromPhoneNumber(u.contacts[i]), DbObject.User.class);
        }
        DbObject.ContactsJsonObject contacts = new DbObject.ContactsJsonObject(users);
        return gson.toJson(contacts);
    }
}
