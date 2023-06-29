package org.launchcode.liftoff.shoefinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ShoefinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoefinderApplication.class, args);
	}

}
