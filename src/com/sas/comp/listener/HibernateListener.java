package com.sas.comp.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.sas.comp.hibernate.Hibernate;

@WebListener
public class HibernateListener implements ServletContextListener {

	@Override
	public void contextDestroyed(final ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(final ServletContextEvent arg0) {
		Hibernate.getInstance(); // initialize on server startup.  No lag on first page load.
	}

}
