/**
 * @fileName :     BaseDaoImpl
 * @author :       zeeker
 * @date :         11/01/2018 16:22
 * @description :   使用自建数据库框架
 */

package com.zeeker.keychain.dao.impl.jdbc;


import com.zeeker.keychain.dao.BaseDao;
import com.zeeker.keychain.model.BaseObject;
import com.zeeker.utils.jdbc.BeanListHandler;
import com.zeeker.utils.jdbc.DbNameConveter;
import com.zeeker.utils.jdbc.JdbcUtil;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.*;
import java.util.*;

public abstract class BaseDaoImpl<T extends BaseObject, ID extends Serializable & Comparable<ID>> implements BaseDao<T, ID>{

    protected Class<T> entityClass;

    protected String TABLE_NAME;

    public BaseDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        Type[] parameterizedType = ((ParameterizedType)type).getActualTypeArguments();
        entityClass = (Class<T>)parameterizedType[0];
        // 数据库表名都是小写
        TABLE_NAME = DbNameConveter.className2TableName(entityClass.getSimpleName());
    }

    @Override
    public T findById(ID id) {
        String sql = "select * from " + TABLE_NAME + " where id = ?";
        List<Object> params = new ArrayList<>(1);
        params.add(id);
        List<T> list = JdbcUtil.query(sql, params, entityClass, new BeanListHandler<T>());
        return list != null ? list.get(0) : null;
    }

    @Override
    public List<T> findList(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(TABLE_NAME).append(" where 1 = 1");
        List<Object> queryParams = new ArrayList<>();
        if (params != null){
            for (Map.Entry<String, Object> entry : params.entrySet()){
                sql.append("and ").append(entry.getKey()).append(" =? ");
                queryParams.add(entry.getValue());
            }
        }
        List<T> list = JdbcUtil.query(sql.toString(), queryParams, entityClass, new BeanListHandler<T>());

        return list;
    }

    @Override
    public void insert(T entity) {
        StringBuilder sql = new StringBuilder("insert into " + TABLE_NAME + "(" );
        StringBuilder sqlParams = new StringBuilder("(");
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass, Object.class);
            PropertyDescriptor[] fields = beanInfo.getPropertyDescriptors();
            List<Object> params = new ArrayList<Object>();
            for (int i = 0; i < fields.length; i++){
                sql.append((DbNameConveter.camelToUnderline(fields[i].getName())));
                sqlParams.append("?");
                if (i < fields.length - 1){
                    sql.append(", ");
                    sqlParams.append(", ");
                }
                Method getMethod = fields[i].getReadMethod();
                Object value = getMethod.invoke(entity, null);
                params.add(value);
            }
            String insertString = sql.append(") values ").append(sqlParams).append(")").toString();
            JdbcUtil.update(insertString, params);
        } catch (IntrospectionException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity) {
        StringBuilder sql = new StringBuilder("update " + TABLE_NAME + "set ");
        Field[] fields = entityClass.getDeclaredFields();
        List<Object> params = new ArrayList<>();
        for (int i = 0; i < fields.length; i++){
            String sqlappend = DbNameConveter.camelToUnderline(fields[i].getName()) + "= ? ";
            if (i < fields.length - 1){
                sqlappend += ", ";
            }
            sql.append(sqlappend);
            try {
                fields[i].setAccessible(true);
                params.add(fields[i].get(entity));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String updateStr = sql.append("where id = " + entity.getId()).toString();
        System.out.println(updateStr);
        JdbcUtil.update(updateStr, params);
    }

    @Override
    public void delete(T entity) {
        String sql = "delete from " + TABLE_NAME + "where id = ?";
        List<Object> params = new ArrayList<>(1);
        params.add(entity.getId());
        JdbcUtil.update(sql, params);
    }
}
