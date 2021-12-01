package com.example.springsoap.webservice;

import edu.cu.integration.constituentenrldata.ConstituentEnrollmentData;
import edu.cu.integration.constituentenrldata.EnrollmentResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EnrollmentWebServiceEndpoint {
	private static final String NAMESPACE_URI = "http://www.cu.edu/integration/ConstituentEnrlData";

	@PayloadRoot(localPart = "ConstituentEnrollmentData", namespace = NAMESPACE_URI)
	@ResponsePayload
	public EnrollmentResponse consumeEnrollment(@RequestPayload ConstituentEnrollmentData enrollmentData) {
		EnrollmentResponse response = new EnrollmentResponse();
		response.setMessage("SUCCESS");
		return response;
	}
}