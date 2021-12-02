package com.example.springsoap.webservice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.colorado.oit.es.enrollments.ConstituentEnrollmentData;
import edu.colorado.oit.es.enrollments.EnrollmentResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class EnrollmentWebServiceEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(EnrollmentWebServiceEndpoint.class);
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
        logReceivedEnrollment(enrollmentData);

        jmsTemplate.convertAndSend(JMS_ENROLLMENTS_QUEUE, enrollmentData);
        logger.info("Sent enrollment to queue.");

        return buildResponse();
    }

    private EnrollmentResponse buildResponse() {
        EnrollmentResponse response = new EnrollmentResponse();
        response.setMessage("SUCCESS");
        return response;
    }

    private void logReceivedEnrollment(ConstituentEnrollmentData enrollmentData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            logger.info("Enrollment received from SOAP: " + mapper.writeValueAsString(enrollmentData));
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
    }
}