package com.goayo.debtify.Database;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;
import java.util.Map;

class DatabaseConnector {
    private static DatabaseConnector instance;
    private String port = "2501";
    private String ipAddr = "http://83.252.195.154" + ":" + port + "/";

    private DatabaseConnector(){}

    public static DatabaseConnector getInstance(){
        if(instance == null){
            instance = new DatabaseConnector();
        }
        return instance;
    }

    public String getData(Map<String, String> variables, String route) throws IOException, ParseException {
        String returnVal;
        String address = ipAddr + route + "?";
        for(Map.Entry<String, String> set : variables.entrySet()){
            address = address + set.getKey() + "=" + set.getValue() + "&";
        }

        returnVal = getDataFromServer(address);

        return returnVal;
    }

    private String getDataFromServer(String address) throws IOException, ParseException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpEntity entity;
        String result = null;

        try {

            HttpGet request = new HttpGet(address);
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                System.out.println(response);
                entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return result;
    }

    public boolean postData(String data, String route){
        String address = ipAddr + route;
        try{
            if (postDataToServer(address, data)){
                return true;
            }
        } catch (Exception e){
            return false;
        }
        return false;
    }

    private boolean postDataToServer(String address, String data) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(address);

        StringEntity entity = new StringEntity(data);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = null;
        try {
            response = client.execute(httpPost);
            System.out.println(response);
            System.out.println(EntityUtils.toString(response.getEntity()));
            client.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        if(response.getCode() != 200){
            return false;
        }
        return true;
    }

    void putData(){

    }

    void deleteData(){

    }

}
