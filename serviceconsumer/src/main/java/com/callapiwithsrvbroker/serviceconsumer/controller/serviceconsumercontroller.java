package com.callapiwithsrvbroker.serviceconsumer.controller;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class serviceconsumercontroller {
    @GetMapping("getconsumertoken")
    String getConsumerToken(){
        HttpResponse<String> serviceResponse;
        try {
            String token = getToken();
            serviceResponse = requestService(token);
        } catch (Exception e) {
//            response.getWriter().append(e.getMessage());
            return "Error";
        }
//        response.setStatus(serviceResponse.getStatus());
//        response.getWriter().append(serviceResponse.getBody().toString());
        return serviceResponse.getBody().toString();
    }


    private String getToken() throws Exception {
        HttpResponse<JsonNode> jsonResponse = Unirest.post("<authentication URL in service instance key>/oauth/token")
                .header("accept", "application/json")
                .field("grant_type", "password")
                .field("username", "<user in IdP>")
                .field("password", "<password in IdP>")
                .field("client_id", "<client ID in service instance key>")
                .field("client_secret", "<client secret in service instance key>")
                .field("login_hint", "{\"origin\":\"sap.custom\"}")
                .asJson();
        if (jsonResponse.getStatus() != HttpStatus.SC_OK) {
            throw new Exception("Invalid response from UAA. Status code: " + String.valueOf(jsonResponse.getStatus()));
        }
        JSONObject response = jsonResponse.getBody().getObject();
        Object accessToken = response.get("access_token");
        if (accessToken == null) {
            throw new Exception("No access token found. Response from UAA: " + response.toString());
        }
        return accessToken.toString();
    }


    private HttpResponse<String> requestService(String token) throws UnirestException, MalformedURLException {
        String productServiceRootUrl = "<business app URL in service instance key>";
        String productServiceUrl = new URL(new URL(productServiceRootUrl), "/getconsumertoken").toString();
        return Unirest.get(productServiceUrl).header("Authorization", "Bearer " + token).asString();
    }


}
