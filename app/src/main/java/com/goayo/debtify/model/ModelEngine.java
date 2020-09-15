package com.goayo.debtify.model;

import java.util.List;

public class ModelEngine {

    private Account account;

    public ModelEngine(Account account){
        this.account = account;
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
