package com.example.springsoap.webservice;

import edu.cu.integration.ConstituentEnrollmentData;
import edu.cu.integration.EnrollmentResponse;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class WebServiceEndpoint {
	private static final String NAMESPACE_URI = "http://www.cu.edu/integration";

	@PayloadRoot(localPart = "ConstituentEnrollmentData", namespace = NAMESPACE_URI)
	@ResponsePayload
	public EnrollmentResponse consumeEnrollment(@RequestPayload ConstituentEnrollmentData request) {
		EnrollmentResponse response = new EnrollmentResponse();
		response.setMessage(request.getConstituentId());
		return response;
	}
}