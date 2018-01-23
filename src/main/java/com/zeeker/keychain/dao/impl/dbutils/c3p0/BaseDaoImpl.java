/**
 * @fileName :     BaseDaoImpl
 * @author :       zeeker
 * @date :         23/01/2018 17:24
 * @description :  c3p0 连接池 + dbutils 数据库操作框架 实现 dao
 */

package com.zeeker.keychain.dao.impl.dbutils.c3p0;

import com.zeeker.keychain.dao.BaseDao;
import com.zeeker.keychain.model.BaseObject;
import com.zeeker.utils.dbutils.common.c3p0.C3p0Utils;
import com.zeeker.utils.dbutils.mydbutils.DbNameConveter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseDaoImpl<T extends BaseObject, ID extends Serializable & Comparable<ID>> implements BaseDao<T,ID> {

    protected Class<T> entityClass;

    protected String TABLE_NAME;

    public BaseDaoImpl() {
        Type type = getClass().getGenericSuperclass();
        Type[] paramType = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>)paramType[0];
        TABLE_NAME = DbNameConveter.className2TableName(entityClass.getSimpleName());
    }

    @Override
    public T findById(ID id) {
        T entity = null;
        QueryRunner queryRunner = new QueryRunner(C3p0Utils.getDataSource());
        ResultSetHandler<T> resultSetHandler = new BeanHandler<T>(entityClass);
        String sql = "select * from " + TABLE_NAME + " where id = " + id;
        try {
            entity = queryRunner.query(sql, resultSetHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public List<T> findList(Map<String, Object> params) {
        List<T> entitys = new LinkedList<>();
        QueryRunner queryRunner = new QueryRunner(C3p0Utils.getDataSource());
        ResultSetHandler<List<T>> resultSetHandler = new BeanListHandler<T>(entityClass);
        StringBuilder sql = new StringBuilder("select * from " + TABLE_NAME + "where 1 = 1 ");
        for (Map.Entry<String, Object> entry : params.entrySet()){
            sql.append("and " + entry.getKey() + " = " + entry.getValue() + " ");
        }
        try {
            entitys = queryRunner.query(sql.toString(), resultSetHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entitys;
    }

    @Override
    public void insert(T entity) {
        QueryRunner queryRunner = new QueryRunner(C3p0Utils.getDataSource());
        StringBuilder sql = new StringBuilder("insert into " + TABLE_NAME + "(");
        StringBuilder sqlParams = new StringBuilder("(");
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(entityClass, Object.class);
            PropertyDescriptor[] fields = beanInfo.getPropertyDescriptors();
            List<Object> params = new ArrayList<Object>();
            for (int i = 0; i < fields.length; i++) {
                sql.append((DbNameConveter.camelToUnderline(fields[i].getName())));
                sqlParams.append("?");
                if (i < fields.length - 1) {
                    sql.append(", ");
                    sqlParams.append(", ");
                }
                Method getMethod = fields[i].getReadMethod();
                Object value = getMethod.invoke(entity, null);
                params.add(value);
            }
            String insertString = sql.append(") values ").append(sqlParams).append(")").toString();
            queryRunner.update(sql.toString(), entity);
        }catch (IntrospectionException | InvocationTargetException | IllegalAccessException e){
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(T entity) {
        // todo

    }

    @Override
    public void delete(T entity) {
        // todo
    }
}
