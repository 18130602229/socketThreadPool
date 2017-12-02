package com.lingdu.c3p0;

import java.beans.PropertyVetoException;  
import java.io.InputStream;
import java.sql.Connection;  
import java.sql.SQLException;  
import java.util.Properties;

import com.lingdu.util.MongodbUtil;
import com.mchange.v2.c3p0.ComboPooledDataSource;
  
public final class ConnectionManager {  
    //使用单利模式创建数据库连接池  
    private static ConnectionManager instance;  
    private static ComboPooledDataSource dataSource;  
    
    private static  String user; //用户名  
    private static  String password; //密码  
    private static  String jdbcUrl;  //数据库地址  
    private static  String driverClass;
    private static  String initialPoolSize;//初始化连接数  
    private static  String minPoolSize;//最小连接数  
    private static  String maxPoolSize;//最大连接数  
    private static  String maxStatements;//最长等待时间  
    private static  String maxIdleTime;//最大空闲时间，单位毫秒  
    
    static {
		Properties prop = new Properties();
		try {
			InputStream in =MongodbUtil.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(in);     ///加载属性列表
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            jdbcUrl = prop.getProperty("db.jdbcurl");
            driverClass = prop.getProperty("db.driver");
            initialPoolSize = prop.getProperty("db.initpoolsize");
            minPoolSize = prop.getProperty("db.minpoolsize");
            maxPoolSize = prop.getProperty("db.maxpoolsize");
            maxStatements = prop.getProperty("db.maxstatement");
            maxIdleTime = prop.getProperty("db.maxidletime");
            in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		  
	}
    /**
     * 创建连接池
     * @throws SQLException
     * @throws PropertyVetoException
     */
    private ConnectionManager() throws SQLException, PropertyVetoException {  
        dataSource = new ComboPooledDataSource();  
        dataSource.setUser(user);     
        dataSource.setPassword(password); 
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setDriverClass(driverClass);  
        dataSource.setInitialPoolSize(Integer.parseInt(initialPoolSize)); 
        dataSource.setMinPoolSize(Integer.parseInt(minPoolSize));
        dataSource.setMaxPoolSize(Integer.parseInt(maxPoolSize));
        dataSource.setMaxStatements(Integer.parseInt(maxStatements));
        dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
    }  
  
    public static final ConnectionManager getInstance() {  
        if (instance == null) {  
            try {  
                instance = new ConnectionManager();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
        return instance;  
    }  
  
    public synchronized final Connection getConnection() {  
        Connection conn = null;  
        try {  
            conn = dataSource.getConnection();  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }  
        return conn;  
    }  
}  