package com.goayo.debtify.database;

import com.google.gson.Gson;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

 class DbUserFetcher {


    public String logInUser(String phoneNumber, String password) {

        Map<String, String> args = new HashMap<>();
        args.put("number", phoneNumber);
        args.put("password", intsPassword(password));
        String data = null;
        try {
            data = DatabaseConnector.getInstance().getData(args, "login");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return data;

    }


    public void registerUser(String phoneNumber, String password, String name) {
        DbObject.User user = new DbObject.User(name, phoneNumber, intsPassword(password), new String[0]);
        Gson gson = new Gson();
        String data = gson.toJson(user);
        DatabaseConnector.getInstance().postData(data, "users");

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

     private String intsPassword(String room) {
         char[] genArray = room.toCharArray();
         StringBuilder id = new StringBuilder();
         for (char c : genArray) {
             id.append((int) c);
         }
         return id.toString();
     }

}

