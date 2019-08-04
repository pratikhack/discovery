package com.discovery.interstellar.web.soap.endpoints;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

    @EnableWs
    @Configuration
    public class WebServiceConfig extends WsConfigurerAdapter {
        @Bean
        public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
            MessageDispatcherServlet servlet = new MessageDispatcherServlet();
            servlet.setApplicationContext(applicationContext);
            servlet.setTransformWsdlLocations(true);
            return new ServletRegistrationBean(servlet, "/ws/*");
        }

        @Bean(name = "interstellar")
        public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema interstellarSchema) {
            DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
            wsdl11Definition.setPortTypeName("ShortestPathsPort");
            wsdl11Definition.setLocationUri("/ws");
            wsdl11Definition.setTargetNamespace("http://interstellar.discovery.com/web/soap/domain");
            wsdl11Definition.setSchema(interstellarSchema);
            return wsdl11Definition;
        }

        @Bean
        public XsdSchema countriesSchema() {
            return new SimpleXsdSchema(new ClassPathResource("interstellar.xsd"));
        }
    }

