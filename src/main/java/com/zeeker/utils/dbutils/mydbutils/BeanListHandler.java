/**
 * @fileName :     BeanListHandler
 * @author :       zeeker
 * @date :         11/01/2018 15:02
 * @description :
 */

package com.zeeker.utils.dbutils.mydbutils;

import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BeanListHandler<T> implements ResultSetHandler<T>{
    /**
     * 将 resultSet 转换为对应的对象列表
     * @param resultSet
     * @return
     */
    @Override
    public List<T> handle(ResultSet resultSet, Class<T> tClass){
        List<T> beanList = new ArrayList<T>();

        if (resultSet != null){
            try {
                ResultSetMetaData metaData = resultSet.getMetaData();
                int colCount = metaData.getColumnCount();
                while (resultSet.next()) {
                    T entity = tClass.newInstance();
                    for (int i = 0; i < colCount; i++){
                        String columeName = metaData.getColumnName(i + 1);
                        Object value = resultSet.getObject(i + 1);
                        BeanUtils.setProperty(entity, DbNameConveter.underlineToCamel(columeName), value);
                    }
                    beanList.add(entity);
                }
            } catch (IllegalAccessException | InstantiationException | SQLException | InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        return beanList;
    }



}
