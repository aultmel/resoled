package org.launchcode.liftoff.shoefinder;

import jakarta.annotation.Resource;
import org.launchcode.liftoff.shoefinder.services.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ShoefinderApplication implements CommandLineRunner {

	@Resource
	StorageService storageService;

	public static void main(String[] args) {SpringApplication.run(ShoefinderApplication.class, args);
			}

	@Override
	public void run(String... arg) throws Exception {
//    storageService.deleteAll();
		storageService.init();
	}
}
