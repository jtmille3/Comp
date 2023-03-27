package com.sas.comp.server;

import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.core.StandardServer;
import org.apache.catalina.startup.Tomcat;

public class EmbeddedServer {
	
	private static Integer port = 80;

	public static void main(final String[] args) throws Exception {
		final EmbeddedServer server = new EmbeddedServer();
		for( String a : args) {
			if( a.startsWith("--port=") ) {
				String p = a.substring(a.indexOf("=")+1);
				port = new Integer(p);
			}
		}
		server.start();
	}

	private void start() throws Exception {
		final String appBase = "";

		final Tomcat tomcat = new Tomcat();
		tomcat.setPort(port);

		tomcat.setBaseDir("./web");
		tomcat.getHost().setAppBase(appBase);

		final String contextPath = "/";

		// Add AprLifecycleListener
		StandardServer server = (StandardServer) tomcat.getServer();
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);

		tomcat.addWebapp(contextPath, appBase);
		tomcat.start();
		tomcat.getServer().await();
	}
}
