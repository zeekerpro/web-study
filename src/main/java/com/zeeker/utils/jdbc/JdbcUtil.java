/**
 * @fileName :     JdbcUtil
 * @author :       zeeker
 * @date :         11/01/2018 14:28
 * @description :  自定义jdbc框架
 */

package com.zeeker.utils.jdbc;

import java.sql.*;
import java.util.List;

public class JdbcUtil {

     //数据库连接池
    private static JdbcConnectionPool connectionPool = JdbcConnectionPool.getInstance();

    /**
     * update insert delete
     * @param sql
     * @param params
     */
    public static void update(String sql, List<Object> params) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; params != null && i < params.size(); i++){
                preparedStatement.setObject(i + 1, params.get(i));
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release(connection, preparedStatement, null);
        }
    }


    /**
     * select
     * @param sql
     * @param params
     * @param <T>
     * @return
     */
    public static <T> List<T> query(String sql, List<Object> params, Class<T> tClass, ResultSetHandler<T> handler){
        List<T> list = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try{
            connection = connectionPool.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++){
                preparedStatement.setObject( i + 1,params.get(i));
            }
            resultSet = preparedStatement.executeQuery();
            list = handler.handle(resultSet, tClass);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            release(connection, preparedStatement, resultSet);
        }

        return list;
    }


    /**
     * 释放资源
     * @param connection
     * @param statement
     * @param resultSet
     */
    public static void release(Connection connection, Statement statement, ResultSet resultSet){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
