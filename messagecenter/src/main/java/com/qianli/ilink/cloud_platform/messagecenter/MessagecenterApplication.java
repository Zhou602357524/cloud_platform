package com.qianli.ilink.cloud_platform.messagecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class MessagecenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagecenterApplication.class, args);
	}
}
