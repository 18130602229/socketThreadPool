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
    private static  String maxIdleTime;//最大空闲时间，单位毫秒  
    private static String  acquireIncrement; //连接池在无空闲连接可用时一次性创建的新数据库连接数,default:3 
    private static String acquireRetryAttempts; //#连接池在获得新连接失败时重试的次数，如果小于等于0则无限重试直至连接获得成功  
    private static String acquireRetryDelay; //#连接池在获得新连接时的间隔时间 
    
    static {
		Properties prop = new Properties();
		try {
			InputStream in =MongodbUtil.class.getClassLoader().getResourceAsStream("config.properties");
            prop.load(in);     ///加载属性列表
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            jdbcUrl = prop.getProperty("db.jdbcurl");
            driverClass = prop.getProperty("db.driver");
            
            initialPoolSize = prop.getProperty("c3p0.initialPoolSize");
            minPoolSize = prop.getProperty("c3p0.minPoolSize");
            maxPoolSize = prop.getProperty("c3p0.maxPoolSize");
            maxIdleTime = prop.getProperty("c3p0.maxIdleTime");
            acquireIncrement = prop.getProperty("c3p0.acquireIncrement");
            acquireRetryAttempts = prop.getProperty("c3p0.acquireRetryAttempts");
            acquireRetryDelay = prop.getProperty("c3p0.acquireRetryDelay");
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
        dataSource.setMaxIdleTime(Integer.parseInt(maxIdleTime));
        dataSource.setAcquireIncrement(Integer.parseInt(acquireIncrement));
        //maxIdleTime:连接的最大空闲时间，如果超过这个时间，某个数据库连接还没有被使用，则会断开掉这个连接。 这个一般可以在mysql安装目录的 my.ini 文件中
        dataSource.setAcquireRetryAttempts(Integer.parseInt(acquireRetryAttempts));
        dataSource.setAcquireRetryDelay(Integer.parseInt(acquireRetryDelay));
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