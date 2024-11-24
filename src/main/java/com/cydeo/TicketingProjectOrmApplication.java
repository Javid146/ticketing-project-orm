package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TicketingProjectOrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketingProjectOrmApplication.class, args);
	}

	@Bean //todo ModelMapper is a 3rd party class we added with dependency therefore it is written here with @Bean annot
	//but we do not use this structure at real job, in real job you create configuration package for it.
	public ModelMapper mapper(){
		return new ModelMapper();
	}

}
