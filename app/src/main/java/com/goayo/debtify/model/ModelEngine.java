package com.goayo.debtify.model;

import java.util.List;

/**
 * A facade class for the model package. The purpose of the class is to be the face outwards towards other
 * packages depending on the model. This class aims to keep the model loosely coupled with other packages.
 *
 * @Author Oscar Sanner
 *
 */

public class ModelEngine {

    private Account account;
    private static ModelEngine instance;

    private ModelEngine(Account account){
        this.account = account;
    }

    public static ModelEngine getInstance(){
        if(instance == null){
            instance = new ModelEngine(new Account());
        }
        return instance;
    }

    public boolean registerUser(String phoneNumber, String name, String password){
        return false;
    }

    public boolean logInUser(String phoneNumber, String password){
        return false;
    }

    public boolean addContact(String phoneNumber){
        return false;
    }

    public void removeContact(String phoneNumber){

    }

    public boolean createGroup(String groupName, List<String> phoneNumberList){
        return false;
    }

    public boolean removeGroup(int groupID){
        return false;
    }

    public List<IGroupInformation> getGroups(){
        return
    }

    public boolean addUserToGroup(String phoneNumber, int groupID){
        return false;
    }
    public boolean removeUserFromGroup(String phoneNumber, int groupID){
        return false;
    }

    public boolean createDebt(int groupID, String lender, List<String> borrower, double owed){
        return false;
    }
    public boolean payOffDebt(double amount, String debtID){
        return false;
    }

    private boolean isThisInDatabase(String id){
        return false;
    }
}
