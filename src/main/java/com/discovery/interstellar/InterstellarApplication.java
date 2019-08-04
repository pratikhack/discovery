package com.discovery.interstellar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;

@SpringBootApplication
public class InterstellarApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterstellarApplication.class, args);
	}
}
