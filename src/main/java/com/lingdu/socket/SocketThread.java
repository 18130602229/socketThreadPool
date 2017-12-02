package com.lingdu.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;


/**
 * socketThread 线程
 */
class SocketThread extends Thread {
	private static Logger logger =Logger.getLogger("SocketThread.class");
	private ServerSocket serverSocket;
	private int count = 0;
	
	public SocketThread(ServerSocket serverSocket, String socketPort) {
		if (serverSocket == null) {
			try {
				this.serverSocket = new ServerSocket(Integer.parseInt(socketPort));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		while (!this.isInterrupted()) { // �߳�δ�ж�ִ��ѭ��
			try {
				Socket socket = serverSocket.accept();
				if (socket != null){
					count++;
					logger.info("���Ӵ��� ��"+count);
					new ProcessSocketData(socket).start();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void closeServerSocket() {
		try {
			if (serverSocket != null && !serverSocket.isClosed())
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}