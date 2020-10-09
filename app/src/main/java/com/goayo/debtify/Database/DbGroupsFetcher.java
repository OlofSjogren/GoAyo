package com.goayo.debtify.Database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.hc.core5.http.ParseException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class DbGroupsFetcher {
    public boolean removeUserFromGroup(String phoneNumber, String groupID) {
        Gson gson = new Gson();
        DbUserFetcher fetcher = new DbUserFetcher();
        DbObject.User u = gson.fromJson(fetcher.fetchUserFromPhoneNumber(phoneNumber), DbObject.User.class);

        DbObject.GroupMemberPost post = new DbObject.GroupMemberPost(groupID, u);
        String data = gson.toJson(post);
        return DatabaseConnector.getInstance().postData(data, "groups/removeMember");
    }

    public boolean addUserToGroup(String groupID, String phoneNumber) {
        Gson gson = new Gson();
        DbUserFetcher fetcher = new DbUserFetcher();
        DbObject.User u = gson.fromJson(fetcher.fetchUserFromPhoneNumber(phoneNumber), DbObject.User.class);

        DbObject.GroupMemberPost post = new DbObject.GroupMemberPost(groupID, u);
        String data = gson.toJson(post);
        return DatabaseConnector.getInstance().postData(data, "groups/addMember");
    }

    public boolean registerGroup(String name, Set<String> usersPhoneNumber) {
        Gson gson = new Gson();
        DbUserFetcher fetcher = new DbUserFetcher();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        DbObject.Group group = new DbObject.Group(name, date, "", new DbObject.User[usersPhoneNumber.size()], new DbObject.Debt[0]);
        int i = 0;
        for (String phoneNumber : usersPhoneNumber){
            String user = fetcher.fetchUserFromPhoneNumber(phoneNumber);
            DbObject.User u = gson.fromJson(user, DbObject.User.class);
            group.members[i] = u;
            i++;
        }
        String data = gson.toJson(group);
        return DatabaseConnector.getInstance().postData(data, "groups");
    }

    private String[] setToArray(Set<String> usersPhoneNumber) {
        String[] retArr = new String[usersPhoneNumber.size()];
        return usersPhoneNumber.toArray(retArr);
    }

    public String fetchGroupFromId(String groupID) {
        HashMap<String, String> map = new HashMap<>();
        map.put("groupId", groupID);
        String jsonData = null;
        try {
            jsonData = DatabaseConnector.getInstance().getData(map, "groups");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public String fetchGroupsForUser(String phoneNumber) {
        HashMap<String, String> map = new HashMap<>();
        map.put("number", phoneNumber);
        String jsonData = null;
        try {
            jsonData = DatabaseConnector.getInstance().getData(map, "groups");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
            try{
                DbObject.GroupsArrayJsonObject group = new Gson().fromJson(jsonData, DbObject.GroupsArrayJsonObject.class);
            } catch (Exception e){
                return new Gson().toJson( new DbObject.GroupsArrayJsonObject(new DbObject.Group[0]));
            }
        return jsonData;
    }
}
