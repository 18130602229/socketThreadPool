package com.lingdu.socket;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerSocketListener implements ServletContextListener {
	private SocketThread socketThread;
	public void contextDestroyed(ServletContextEvent e) {
		if (socketThread != null && socketThread.isInterrupted()) {
			socketThread.closeServerSocket();
			socketThread.interrupt();
		}
	}

	public void contextInitialized(ServletContextEvent e) {
		ServletContext servletContext = e.getServletContext();
		if (socketThread == null) {
			socketThread = new SocketThread(null, servletContext);
			socketThread.start(); 
		}
	}
}