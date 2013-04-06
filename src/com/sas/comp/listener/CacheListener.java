package com.sas.comp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CacheListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
	}

}
