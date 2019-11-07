package de.demmer.dennis.odrascrapercontroller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class OdraScraperControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdraScraperControllerApplication.class, args);
	}

}
