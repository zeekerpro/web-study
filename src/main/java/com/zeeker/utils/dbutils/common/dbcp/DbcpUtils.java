/**
 * @fileName :     DbcpUtils
 * @author :       zeeker
 * @date :         19/01/2018 16:18
 * @description :
 */

package com.zeeker.utils.dbutils.common.dbcp;

import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DbcpUtils {

    // 数据库连接池
    private  static DataSource dataSource = null;

    static {
        Properties properties = new Properties();
        InputStream inputStream = DbcpUtils.class.getClassLoader().getResourceAsStream("dbcp-config.properties");
        try {
            properties.load(inputStream);
            BasicDataSourceFactory basicDataSourceFactory = new BasicDataSourceFactory();
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

}
