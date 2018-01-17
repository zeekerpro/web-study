/**
 * @fileName :     KeychainServiceImpl
 * @author :       zeeker
 * @date :         17/01/2018 14:17
 * @description :
 */

package com.zeeker.keychain.service.impl;

import com.zeeker.keychain.dao.KeychainDao;
import com.zeeker.keychain.factory.DaoFactory;
import com.zeeker.keychain.model.Keychain;
import com.zeeker.keychain.service.KeychainService;

public class KeychainServiceImpl extends BaseServiceImpl<Keychain, Long> implements KeychainService{

    private KeychainDao keychainDao;

    {
        keychainDao = DaoFactory.getInstance().createDao(KeychainDao.class);
        setBaseDao(keychainDao);
    }

}
