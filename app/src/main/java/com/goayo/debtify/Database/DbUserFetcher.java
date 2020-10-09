package com.goayo.debtify.Database;

import com.google.gson.Gson;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DbUserFetcher {



    static class logInUser implements Runnable{

        String phoneNumber;
        String password;
        String data;

        public logInUser(String phoneNumber, String password){
            this.phoneNumber = phoneNumber;
            this.password = password;
        }

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

        @Override
        public void run() {
            data = logInUser(phoneNumber, password);
        }
    }

    static class RegisterUser implements Runnable{

        Boolean result = null;
        String phoneNumber;
        String password;
        String name;

        public RegisterUser(String phoneNumber, String password, String name){
            this.phoneNumber = phoneNumber;
            this.password = password;
            this.name = name;
        }

        @Override
        public void run() {
            result = registerUser(phoneNumber, password, name);
        }

        public boolean registerUser(String phoneNumber, String password, String name) {
            DbObject.User user = new DbObject.User(name, phoneNumber, password, new String[0]);
            Gson gson = new Gson();
            String data = gson.toJson(user);
            return DatabaseConnector.getInstance().postData(data, "users");
        }
    }



    static class FetchUserFromPhoneNumber implements Runnable{

        String data = null;
        String phoneNumber = null;

        public FetchUserFromPhoneNumber(String phoneNumber){
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void run() {
            data = fetchUserFromPhoneNumber(phoneNumber);
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
}
