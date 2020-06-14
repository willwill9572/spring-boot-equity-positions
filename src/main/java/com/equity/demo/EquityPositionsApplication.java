package com.equity.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.equity.demo.mapper")
public class EquityPositionsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquityPositionsApplication.class, args);
	}
}
