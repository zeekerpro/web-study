/**
 * @fileName :     BaseServiceImpl
 * @author :       zeeker
 * @date :         17/01/2018 14:11
 * @description :
 */

package com.zeeker.keychain.service.impl;

import com.zeeker.keychain.dao.BaseDao;
import com.zeeker.keychain.model.BaseObject;
import com.zeeker.keychain.service.BaseService;

import java.io.Serializable;
import java.util.List;

public class BaseServiceImpl<T extends BaseObject, ID extends Serializable> implements BaseService<T, ID> {

    private BaseDao<T, ID> baseDao;

    public void setBaseDao(BaseDao<T, ID> baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public T find(ID id) {
        return baseDao.findById(id);
    }

    @Override
    public List<T> findAll() {
        return baseDao.findList(null);
    }

    @Override
    public void add(T entity) {
        baseDao.insert(entity);
    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }
}
