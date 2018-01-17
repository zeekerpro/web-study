package com.zeeker.keychain.dao;

import java.util.List;
import java.util.Map;

public interface BaseDao<T, ID> {

    T findById(ID id);

    List<T>  findList(Map<String, Object> params);

    void insert(T entity);

    void update(T entity);

    void delete(T entity);
}
