/**
 * @fileName :     BaseService
 * @author :       zeeker
 * @date :         17/01/2018 14:06
 * @description :
 */

package com.zeeker.keychain.service;

import com.zeeker.keychain.model.BaseObject;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseObject, ID extends Serializable> {

    T find(ID id);

    List<T> findAll();

    void add(T entity);

    void update(T entity);

    void delete(T entity);

}
