package com.sas.comp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sas.comp.models.Competitive;
import com.sas.comp.service.CompetitiveService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
		final long startTime = System.currentTimeMillis();
		CompetitiveService competitiveService = new CompetitiveService();
		final Competitive competitive = competitiveService.getCompetition();
		Cache.getCache().setCompetitive(competitive);
		final long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println(elapsedTime + "ms");
    }

}
