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

    // 使用 threadlocal 实现事务管理
    private  static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

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
     * 获取用于事务处理的链接
     * @return
     */
    public static Connection getConnection(){
        Connection connection = threadLocal.get();
        try {
            if (connection == null){
                connection = cpds.getConnection();
                threadLocal.set(connection);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * 在当前线程开启事务
     */
    public static void startTransaction(){
        // 得到当前线程上绑定的连接，开启事务
        Connection connection = threadLocal.get();
        // 当前线程没绑定，手动获取连接，并绑定到线程
        if (connection == null){
            connection = getConnection();
        }
        // 开启事务
        try {
            connection.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    public  static void commitTransaction(){
        Connection connection = threadLocal.get();
        // 连接不为 null 才提交， 否则不提交
        if (connection != null){
            try {
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭事务
     */
    public static void closeTranscation(){
        Connection connection = threadLocal.get();

        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
            // 从当前线程中移除绑定的链接，一定要放在 finally语句中
            threadLocal.remove();
        }

    }



}
