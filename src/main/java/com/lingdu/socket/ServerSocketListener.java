package com.lingdu.socket;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lingdu.util.MongodbUtil;

public class ServerSocketListener implements ServletContextListener {
	private SocketThread socketThread;
	private static String socketPort;
	//获取config配置中的socket 监听的端口
	static {
		Properties prop = new Properties();
		try {
			InputStream in =MongodbUtil.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(in);     ///加载属性列表
           Iterator<String> it=prop.stringPropertyNames().iterator();
         while(it.hasNext()){
              String key=it.next();
              if("socket.port".equals(key)){
            	  socketPort = prop.getProperty(key);
              }
          }
         in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	}
	public void contextDestroyed(ServletContextEvent e) {
		if (socketThread != null && socketThread.isInterrupted()) {
			socketThread.closeServerSocket();
			socketThread.interrupt();
		}
	}

	public void contextInitialized(ServletContextEvent e) {
		if (socketThread == null) {
			socketThread = new SocketThread(null, socketPort);
			socketThread.start(); 
		}
	}
}