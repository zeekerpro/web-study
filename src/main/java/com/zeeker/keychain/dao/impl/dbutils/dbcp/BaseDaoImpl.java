package com.zeeker.keychain.dao.impl.dbutils.dbcp;

import com.zeeker.keychain.dao.BaseDao;
import com.zeeker.keychain.model.BaseObject;
import com.zeeker.utils.dbutils.common.dbcp.DbcpUtils;
import com.zeeker.utils.dbutils.mydbutils.DbNameConveter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @fileName :     BaseDaoImpl
 * @author :       zeeker
 * @date :         23/01/2018 18:37
 * @description :  dbcp + dbutils 实现 dao
 *  写法和 c3p0 差不多
 */


public class BaseDaoImpl<T extends BaseObject, ID extends Serializable & Comparable<ID>> implements BaseDao<T, ID>{

    protected Class<T> entityClass;
    protected String TABLE_NAME;

    public BaseDaoImpl() {
        Type type = this.getClass().getGenericSuperclass();
        Type[] types = ((ParameterizedType) type).getActualTypeArguments();
        entityClass = (Class<T>)types[0];
        TABLE_NAME = DbNameConveter.className2TableName(entityClass.getSimpleName());
    }

    @Override
    public T findById(ID id) {
        T entity = null;
        QueryRunner queryRunner = new QueryRunner(DbcpUtils.getDataSource());
        String sql = "select * from " + TABLE_NAME + " where id = " + id;
        ResultSetHandler<T> resultSetHandler = new BeanHandler<>(entityClass);
        try {
            entity = queryRunner.query(sql, resultSetHandler);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<T> findList(Map<String, Object> params) {
        // todo
        return null;
    }

    @Override
    public void insert(T entity) {
        // todo
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
