package com.callapiwithsrvbroker.serviceprovider.controller;

import com.sap.cloud.security.xsuaa.token.SpringSecurityContext;
import com.sap.cloud.security.xsuaa.token.Token;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceProviderController {
    @GetMapping("getconsumertoken")
    String getConsumerToken(){
        Token token = SpringSecurityContext.getToken();
        return token.getAppToken();
    }

}
