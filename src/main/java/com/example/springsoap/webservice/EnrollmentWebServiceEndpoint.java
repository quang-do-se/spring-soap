package com.example.springsoap.webservice;

import edu.cu.integration.constituentenrldata.ConstituentEnrollmentData;
import edu.cu.integration.constituentenrldata.EnrollmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EnrollmentWebServiceEndpoint {
	private static final String NAMESPACE_URI = "http://www.cu.edu/integration/ConstituentEnrlData";

	public static final String JMS_ENROLLMENTS_QUEUE = "jmsIncomingEnrollmentsQueue";

	private final JmsTemplate jmsTemplate;

	@Autowired
	public EnrollmentWebServiceEndpoint(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@PayloadRoot(localPart = "ConstituentEnrollmentData", namespace = NAMESPACE_URI)
	@ResponsePayload
	public EnrollmentResponse consumeEnrollment(@RequestPayload ConstituentEnrollmentData enrollmentData) {
		// Send message to queue
		jmsTemplate.convertAndSend(JMS_ENROLLMENTS_QUEUE, enrollmentData);

		EnrollmentResponse response = new EnrollmentResponse();
		response.setMessage("SUCCESS");
		return response;
	}
}