package com.lingdu.c3p0;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

import com.lingdu.entity.Message;

public class DBUtil {

	private static Logger logger = Logger.getLogger("DBUtil.class");
	private static final String KEY_STR = "(terminalid,longitude,latitude,direction,curspeed,speeds,imei,communiid,cellular,version,proxyid,temperature,oil,mileage,sensor,unixtimestamp) VALUES ";

	/**
	 * 数据批量插入
	 * 
	 * @throws SQLException
	 */
	public int insertMessageList(String str) {
		Connection conn = ConnectionManager.getInstance().getConnection();
		Random rand = new Random();
		int randNum = rand.nextInt(9);
		logger.info("生成的随机数 ；"+ randNum);
		// 插入的条数
		int size = 0;
			// 将 list 集合 变成 value 的字符串。进行批量插入
			//String values = listTOStr(list);
			Statement stmt = null;
			StringBuffer sbf = new StringBuffer();
			sbf.append("(").append(str).append(")");
			try {
				String sql = "INSERT INTO message"+randNum + KEY_STR + sbf.toString();
				stmt = conn.createStatement();
				size = stmt.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
				logger.error("数据插入失败！！");
			} finally {
				release(conn, stmt, null);
			}
		return size;

	}

	/**
	 * 新增数据
	 * 
	 * @throws SQLException
	 */
	public List<Message> countMessageAll(String speeds){
		Connection conn = ConnectionManager.getInstance().getConnection();
		List<Message> list = new ArrayList<Message>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select * from message where speeds=" +"'"+ speeds+"'";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			Message ms = null;
			while (rs.next()) {
				ms = new Message();
				ms.setId(rs.getInt("id")) ;
				ms.setTerminalid(rs.getInt("terminalid"));
				ms.setLongitude(rs.getInt("longitude"));
				ms.setLatitude(rs.getInt("latitude"));
				ms.setDirection(rs.getInt("direction"));
				ms.setCurspeed(rs.getInt("curspeed"));
				ms.setSpeeds(rs.getString("speeds"));
				ms.setImei(rs.getString("imei"));
				ms.setCommuniid(rs.getInt("communiid"));
				ms.setCellular(rs.getString("cellular"));
				ms.setVersion(rs.getString("version"));
				ms.setProxyid(rs.getString("proxyid"));
				ms.setTemperature(rs.getInt("temperature"));
				ms.setOil(rs.getInt("oil"));
				ms.setMileage(rs.getInt("mileage"));
				ms.setSensor(rs.getInt("sensor"));
				ms.setUnixtimestamp(rs.getInt("unixtimestamp"));

		          list.add(ms);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			release(conn, stmt, rs);
		}
		return list;
	}

	/**
	 * 删除数据
	 * 
	 * @param name
	 * @return
	 */
	public void deleteById(int id) {
		Connection conn = ConnectionManager.getInstance().getConnection();
		String sql = "delete from message where id=" + id;
		Statement stmt = null;
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			release(conn, stmt, null);
		}
	}

	/**
	 * @Method: release
	 * @Description: 释放资源，
	 *               释放的资源包括Connection数据库连接对象，负责执行SQL命令的Statement对象，存储查询结果的ResultSet对象
	 * @Anthor:孤傲苍狼
	 *
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				// 关闭存储查询结果的ResultSet对象
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				// 关闭负责执行SQL命令的Statement对象
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (conn != null) {
			try {
				// 将Connection连接对象还给数据库连接池
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static String listTOStr(List<Message> list) {
		StringBuffer sbf = new StringBuffer();
		int size = list.size();
		Message ms = new Message();
		for (int i = 0; i < size; i++) {
			ms = list.get(i);
			if (i == 0) {
				sbf.append("(");
				sbf.append(ms.ObjectToStr(ms));
				sbf.append(")");
			} else {
				sbf.append(",(");
				sbf.append(ms.ObjectToStr(ms));
				sbf.append(")");
			}
		}
		if (list.size() == 1) {

		}
		return null;
	}

}