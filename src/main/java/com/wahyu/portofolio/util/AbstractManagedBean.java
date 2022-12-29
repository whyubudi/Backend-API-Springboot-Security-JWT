package com.wahyu.portofolio.util;

import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONException;
import org.primefaces.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.*;
import java.util.*;

public abstract class AbstractManagedBean {

    @Value("${query.api.url}")
    private String url;
    @Value("${db.datasource.url}")
    private String dburl;
    @Value("${db.datasource.user}")
    private String dbuser;
    @Value("${db.datasource.pass}")
    private String dbpass;
    private static String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    protected List<JSONObject> getDataQuery(Map<String,Object> inputData, String path) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<Map<String,Object>> request = new HttpEntity<>(inputData,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ArrayList> response = restTemplate.postForEntity(url+path,request,ArrayList.class);
        List<JSONObject> listData = new ArrayList<JSONObject>(response.getBody());
//        for (int i = 0; i < listData.size(); i++) {
//            JSONObject json = new JSONObject(listData.get(i));
//        }
        return listData;
    }

    protected static Map<String,Object> mapJSON(JSONObject json) throws JSONException {

        Map<String, Object> map = new HashMap();
        Iterator<String> keysItr = json.keys();
        while(keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = json.get(key);
            if(String.valueOf(value).equals("null")){
                value = "";
            }
            if(value instanceof JSONArray) {
                value = listJSON((JSONArray) value);
            } else if(value instanceof JSONObject) {
                value = mapJSON((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    protected static List<Object> listJSON(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<>();
        for(int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if(value instanceof JSONArray) {
                value = listJSON((JSONArray) value);
            } else if(value instanceof JSONObject) {
                value = mapJSON((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    protected String getDbConnection(String query) {

        String data = "";
        try {
            Class.forName(JDBC_DRIVER);
            Connection conn = DriverManager.getConnection(dburl,dbuser,dbpass);
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                data = rs.getString(1);
            }

            ps.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return data;
    }
}
