package com.clase.taller;

import org.springframework.boot.SpringApplication;

public class TestTallerApplication {

	public static void main(String[] args) {
		SpringApplication.from(TallerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
