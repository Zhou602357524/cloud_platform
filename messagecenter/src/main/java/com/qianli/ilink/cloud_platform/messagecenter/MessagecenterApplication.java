package com.qianli.ilink.cloud_platform.messagecenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class MessagecenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessagecenterApplication.class, args);
	}
}
