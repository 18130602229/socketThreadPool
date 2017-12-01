package com.lingdu.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import org.apache.log4j.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.lingdu.util.MongodbUtil;
import com.lingdu.utiltool.ConmmentUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;

class ProcessSocketData extends Thread {

	private static Logger logger = Logger.getLogger("ProcessSocketData.class");
	private Socket socket;
	private static String dbName = "socket";
	private static MongodbUtil mongo = new MongodbUtil(dbName);
	private static final String COLL_NAME = "message";

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
			// 客户端传来的数据如“xxx.xxx.xxx.xxx;hell world ....”格式 ，则保存，否则请求响应。
			PrintWriter os = new PrintWriter(socket.getOutputStream());
			// 判断字符传来的字符串是否包含 ；符号,如何大于 -1 则包含。
			int quedex = str.indexOf(";");
			String ip = socket.getInetAddress().getHostAddress();
			if (quedex > -1) {
				String aimIp = str.substring(0, quedex);
				// 判断截取字符串是否是ip
				Boolean bole = ConmmentUtil.ipRegex(aimIp);
				if (bole) {
					String content = str.substring(quedex + 1).trim();
					addMessage(content, aimIp, ip);
					os.println("succese date");
					os.flush();
				} else {
					System.out.println("ip 格式不正确！");
				}
			} else {
				// 查询数据库，看是否含有自己 ip 地数据。
				List<Document> list = countMessageAll(ip);
				logger.info("当前客户端ip："+ip);
				int size = list.size();
				if (size > 0) {
					Document document = list.get(0);
					ObjectId obj = (ObjectId) document.get("_id");
					// 删除该条数据
					deleteByID(obj);
					logger.info("返回内容:" + document.toString());
					os.println(document.toString());
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
	 * 新增一条message 数据
	 * 
	 * @param message
	 * @param ip
	 */
	public static void addMessage(String content, String aimIp, String ip) {
		Document document = new Document();
		document.append("date", ConmmentUtil.getformter());
		document.append("ip", ip);
		document.append("aimIp", aimIp);
		document.append("message", content);
		mongo.insert(document, COLL_NAME);
	}

	/**
	 * 根据ip获得一条数据
	 * 
	 * @param ip
	 * @return
	 */
	public static Document getMessage(String ip) {
		BasicDBObject queryOne = new BasicDBObject();
		queryOne.append("ip", ip);
		Document docOne = mongo.findOne(queryOne, COLL_NAME);
		return docOne;
	}

	/**
	 * 根据具体属性进行修改
	 * 
	 * @param ip
	 * @return
	 */
	public static Document updateByFiel(String ip) {
		Bson filter = Filters.eq("ip", ip);
		Document doc = new Document();
		doc.append("state", 0);
		return mongo.updateByFiel(filter, doc, COLL_NAME);
	}

	/**
	 * 根据id进行修改
	 * 
	 * @param id
	 * @return
	 */
	public static Document updateBykey(String id) {
		Document doc = new Document();
		doc.append("state", 0);
		return mongo.updateById(id, doc, COLL_NAME);
	}

	/**
	 * 根据id进行删除
	 * 
	 * @param id
	 */
	public static void deleteByID(ObjectId _idobj) {
		Bson filter = Filters.eq("_id", _idobj);
		mongo.deleteOne(filter, COLL_NAME);
	}

	/**
	 * 根据ip 获取数据库的数量。
	 * 
	 * @param ip
	 * @return
	 */
	public static List<Document> countMessageAll(String ip) {
		BasicDBObject query = new BasicDBObject();
		query.append("aimIp", ip);
		return mongo.findAll(query, COLL_NAME);

	}
}