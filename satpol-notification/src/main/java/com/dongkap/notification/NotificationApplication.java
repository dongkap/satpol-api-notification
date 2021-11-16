package com.dongkap.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages={"com.dongkap"})
@EnableFeignClients(basePackages = {"com.dongkap.feign"})
@EnableJpaRepositories(basePackages = { "com.dongkap.*.dao", "com.dongkap.*.service" })
@EnableAsync
@EnableTransactionManagement
@EnableAutoConfiguration
@EnableResourceServer
@EnableCircuitBreaker
public class NotificationApplication extends SpringBootServletInitializer {

	private static final Logger LOG = LoggerFactory.getLogger(NotificationApplication.class);
	
    public static void main(String[] args) {
	    SpringApplication.run(NotificationApplication.class, args);
        LOG.info("DONGKAP - Hello Satpol!");
    }

}
