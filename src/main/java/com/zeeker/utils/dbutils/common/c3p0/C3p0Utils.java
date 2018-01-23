/**
 * @fileName :     C3p0Utils
 * @author :       zeeker
 * @date :         19/01/2018 16:55
 * @description :
 */

package com.zeeker.utils.dbutils.common.c3p0;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class C3p0Utils {
    private  static  ComboPooledDataSource cpds;

    static {
        cpds = new ComboPooledDataSource("mysql");
    }

    /**
     * 返回连接池
     * @return
     */
    public static ComboPooledDataSource getDataSource() {
        return cpds;
    }

    /**
     * 从连接池中获取一条连接
     * @return
     */
    public Connection getConnection(){
        Connection connection = null;
        try {
            connection = cpds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    

}
