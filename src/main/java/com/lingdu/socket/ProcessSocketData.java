package com.lingdu.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lingdu.c3p0.DBUtil;
import com.lingdu.entity.Message;
import com.lingdu.utiltool.ConmmentUtil;

class ProcessSocketData extends Thread {
	Logger logger = LoggerFactory.getLogger(ProcessSocketData.class);
	private Socket socket;
	private static DBUtil dbutil = new DBUtil();
	public ProcessSocketData() {
		super();
	}

	public ProcessSocketData(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		try {
			BufferedReader is = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			char[] charleng = new char[1024];
			int length = is.read(charleng);
			StringBuffer sb = new StringBuffer();
			for (char ch : charleng) {
				sb.append(ch);
			}
			// 客户端传来数据
			String str = sb.toString();
			logger.info("客户端发过  "+length+" 内容为:" + str);
			System.out.println(str);
			// 客户端传来的数据如“xxx.xxx.xxx.xxx;hell world ....”格式 ，则保存，否则请求响应。
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			// 判断字符传来的字符串是否包含 ；符号,如何大于 -1 则包含。
			int quedex = str.indexOf(";");
			String ip = socket.getInetAddress().getHostAddress();
			if (quedex > -1) {
				//String aimIp = str.substring(0, quedex);
				// 判断截取字符串是否是ip
				//Boolean bole = ConmmentUtil.ipRegex(aimIp);
				if (true) {
					//将 content 转成  list<Message> 格式
					String valuestr = str.substring(quedex + 1).trim();
					//List<Message> list1  = new ArrayList<Message>();
					int size = addMessageList(valuestr);
					if(size > 0){
						os.println("succese date");
						os.flush();
					}else{
						os.println("fiale date");
						os.flush();
					}
					
				} /*else {
					System.out.println("ip 格式不正确！");
				}*/
			} else {
				// 查询数据库，看是否含有自己 ip 地数据。
				List<Message> list = countMessageAll(ip);
				logger.info("当前客户端ip："+ip);
				int size = list.size();
				if (size > 0) {
					// 删除该条数据
					Message message =list.get(0);
					deleteById(message.getId());
					logger.info("返回内容:" + message.toString());
					os.println(message.toString());
					os.flush();
				} else {
					os.println("not date");
					os.flush();
				}
			}

			// 关闭资源
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 将数据 批量插入
	 * @param list
	 * @return
	 */
	public int addMessageList(String str){
		return dbutil.insertMessageList(str);
		
	}
	/**
	 * 根据 ip 查出所有数据
	 * @param ip
	 * @return
	 */
	public List<Message> countMessageAll(String ip){
		return dbutil.countMessageAll(ip);
	}
	
	/**
	 * 将还回的数据进行删除
	 */
	public  void deleteById(int id){
		dbutil.deleteById(id);
	}
}