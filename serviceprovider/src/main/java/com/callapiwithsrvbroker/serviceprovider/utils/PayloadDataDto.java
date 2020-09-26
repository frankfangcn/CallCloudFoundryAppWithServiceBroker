package com.callapiwithsrvbroker.serviceprovider.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class PayloadDataDto {

	String subscriptionAppName;
	String subscriptionAppId;
	String subscribedTenantId;
	String subscribedSubaccountId;
	String subscribedSubdomain;
	String subscriptionAppPlan;
	String globalAccountGUID;
	Long subscriptionAppAmount;
	Map<String, String> additionalInformation;
	String eventType;
}
