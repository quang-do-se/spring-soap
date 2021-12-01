package com.example.springsoap.webservice;

import javax.xml.bind.JAXBElement;

import io.spring.guides.spring_soap.ConstituentEnrollmentData;
import io.spring.guides.spring_soap.CountryRequest;
import io.spring.guides.spring_soap.CountryResponse;
import io.spring.guides.spring_soap.Currency;
import io.spring.guides.spring_soap.EnrollmentResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountryEndpoint {
	private static final String NAMESPACE_URI = "http://spring.io/guides/spring-soap";

	private CountryRepository countryRepository;

	@Autowired
	public CountryEndpoint(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "countryRequest")
	@ResponsePayload
	public CountryResponse getCountry(@RequestPayload CountryRequest request) {
		CountryResponse response = new CountryResponse();
		response.setCountry(countryRepository.findCountry(request.getName()));

		return response;
	}

	@PayloadRoot(localPart = "ConstituentEnrollmentData", namespace = "http://spring.io/guides/spring-soap")
	@ResponsePayload
	public EnrollmentResponse consumeEnrollment(@RequestPayload ConstituentEnrollmentData request) {
		EnrollmentResponse response = new EnrollmentResponse();
		response.setMessage(request.getConstituentId());
		return response;
	}
}