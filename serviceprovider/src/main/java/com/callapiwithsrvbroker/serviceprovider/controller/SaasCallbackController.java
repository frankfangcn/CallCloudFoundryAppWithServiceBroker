package com.callapiwithsrvbroker.serviceprovider.controller;


import com.callapiwithsrvbroker.serviceprovider.utils.PayloadDataDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
public class SaasCallbackController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // This Endpoint is called when a new tenant subscribes to the application
    @PutMapping(value = "/callback/v1.0/tenants/{tenantID}")
    public String callbackPut(@PathVariable(value = "tenantID") String tenantID, @RequestBody PayloadDataDto payload) throws IOException {
        String logMessage = "callback service successfully called with RequestMethod = PUT for tenant " + tenantID + " with payload message = " + payload.toString();
        logger.info(logMessage);
        return "https://" + payload.getSubscribedSubdomain() + "-callapiwithsrvbroker-srvprovider." + "cfapps.sap.hana.ondemand.com";
    }

//    // This Endpoint is called when information about the subscription is requested (For example the dependencies)
//    @GetMapping(value = "/callback/v1.0/dependencies")
//    public List<DependantServiceDto> callbackGet() {
//        String logMessage = "callback service successfully called with RequestMethod = GET for tenant ";
//        logger.info(logMessage);
//
//        List<DependantServiceDto> dependenciesList = new ArrayList<>();
//        if (!StringUtils.isEmpty(xsappnameService)) {
//            dependenciesList = Arrays.asList(new DependantServiceDto[] {
//                    new DependantServiceDto(xsappnameService) });
//        }
//        return dependenciesList;
//    }

    // This Endpoint is called when a tenant unsubscribes from the application
    @DeleteMapping(value = "/callback/v1.0/tenants/{tenantID}")
    public void callbackDelete(@PathVariable(value = "tenantID") String tenantID, @RequestBody PayloadDataDto payload) {
        String logMessage = "callback service successfully called with RequestMethod = DELETE for tenant " + tenantID + " with payload message = " + payload.toString();
        logger.info(logMessage);
    }

}
