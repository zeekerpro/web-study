/**
 * @fileName :     DaoFactory
 * @author :       zeeker
 * @date :         16/01/2018 11:10
 * @description :  生成指定的Dao对象
 */

package com.zeeker.keychain.factory;

import com.zeeker.keychain.dao.BaseDao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {

    private static Properties properties = new Properties();

    private static final DaoFactory daoFactory = new DaoFactory();

    private DaoFactory(){
        InputStream configStream = this.getClass().getClassLoader().getResourceAsStream("factory_config.properties");
        try {
            properties.load(configStream);
        } catch (IOException e) {
            throw  new ExceptionInInitializerError("初始化 dao factory 错误");
        }
    }

    public static DaoFactory getInstance(){
        return daoFactory;
    }

    /**
     * 创建 dao
     * @param daoClass
     * @param <Dao>
     * @return
     */
    public <Dao extends BaseDao> Dao createDao(Class<Dao> daoClass){

        String daoName = daoClass.getSimpleName();
        String daoImplName = properties.getProperty(daoName);

        Dao dao  = null;
        try {
            dao = (Dao) Class.forName(daoImplName).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return dao;
    }


}
