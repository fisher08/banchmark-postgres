package com.example.demo_postgresql;

import org.springframework.boot.SpringApplication;

public class TestDemoPostgresqlApplication {

	public static void main(String[] args) {
		SpringApplication.from(DemoPostgresqlApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
