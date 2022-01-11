package com.wahyu.portofolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.naming.NamingException;

@SpringBootApplication
public class PortofolioApplication {

	public static void main(String[] args) throws NamingException {
		SpringApplication.run(PortofolioApplication.class, args);
	}

}
