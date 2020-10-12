package com.goayo.debtify.Database;

import com.google.gson.Gson;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

 class DbUserFetcher {


    public String logInUser(String phoneNumber, String password) {

        Map<String, String> args = new HashMap<>();
        args.put("number", phoneNumber);
        args.put("password", password);
        String data = null;
        try {
            data = DatabaseConnector.getInstance().getData(args, "login");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return data;

    }


    public boolean registerUser(String phoneNumber, String password, String name) {
        DbObject.User user = new DbObject.User(name, phoneNumber, password, new String[0]);
        Gson gson = new Gson();
        String data = gson.toJson(user);
        return DatabaseConnector.getInstance().postData(data, "users");

    }


    String fetchUserFromPhoneNumber(String phoneNumber) {
        Map<String, String> args = new HashMap<>();
        args.put("number", phoneNumber);
        String data = null;
        try {
            data = DatabaseConnector.getInstance().getData(args, "users");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

}
