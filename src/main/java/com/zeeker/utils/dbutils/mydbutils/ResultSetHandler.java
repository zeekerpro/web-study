package com.zeeker.utils.dbutils.mydbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface ResultSetHandler<T> {
     List<T> handle(ResultSet resultSet, Class<T> tClass) throws SQLException;

}
