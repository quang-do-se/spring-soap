package com.example.springsoap.webservice;

import java.util.Collections;
import java.util.List;

import org.apache.wss4j.common.ConfigurationConstants;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "enrollment")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema enrollmentSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("enrollmentPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setSchema(enrollmentSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema enrollmentSchema() {
        return new SimpleXsdSchema(new ClassPathResource("enrollment.xsd"));
    }

    @Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler() {
        SimplePasswordValidationCallbackHandler callbackHandler = new SimplePasswordValidationCallbackHandler();
        callbackHandler.setUsersMap(Collections.singletonMap("admin", "secret"));
        return callbackHandler;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor securityInterceptor = new Wss4jSecurityInterceptor();
        securityInterceptor.setSecurementMustUnderstand(false);
        securityInterceptor.setValidationActions(ConfigurationConstants.USERNAME_TOKEN);
        securityInterceptor.setValidationCallbackHandler(securityCallbackHandler());
        return securityInterceptor;
    }

    @Override
    public void addInterceptors(List interceptors) {
        interceptors.add(securityInterceptor());
    }
}
