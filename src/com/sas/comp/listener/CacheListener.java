package com.sas.comp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sas.comp.models.Competitive;
import com.sas.comp.service.CompetitiveService;

@WebListener
public class CacheListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
	}

	private final CompetitiveService competitiveService = new CompetitiveService();

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		final long startTime = System.currentTimeMillis();

		final Competitive competitive = competitiveService.getCompetition();
		Cache.getCache().setCompetitive(competitive);

		final long elapsedTime = System.currentTimeMillis() - startTime;
		System.out.println(elapsedTime + "ms");
	}

}
