/**
 * @fileName :     BaseDaoTest
 * @author :       zeeker
 * @date :         12/01/2018 15:22
 * @description :
 */

package utils;

import com.zeeker.keychain.dao.BaseDao;
import com.zeeker.keychain.dao.KeychainDao;
import com.zeeker.keychain.dao.impl.jdbc.BaseDaoImpl;
import com.zeeker.keychain.dao.impl.jdbc.KeychainDaoImpl;
import com.zeeker.keychain.model.Keychain;
import com.zeeker.utils.classLoader.FileSystemClassLoader;
import com.zeeker.utils.jdbc.JdbcUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import sun.applet.AppletClassLoader;

import java.io.File;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class BaseDaoTest {

    @Test
    public void seperatorTest() throws ClassNotFoundException {
        Integer a1 = 9;
        Integer a2 = 5;
        int a =  (int)Math.ceil((double) a1 / (double)a2);

        System.out.println((int) a);
    }

    @Test
    public void selectTest(){
        KeychainDao keychainDao = new KeychainDaoImpl();
        List<Keychain> keychains = keychainDao.findList(null);
    }

    @Test
    public void insertTest(){
        Keychain keychain = new Keychain();
        keychain.setAccessPasswd("123456");
        keychain.setAccount("account");
        keychain.setDescription("desc");
        keychain.setEmail("email");
        keychain.setCreateTime(new Date());
        keychain.setUpdateTime(new Date());
        keychain.setLoginPasswd("123123");
        keychain.setName("test");
        KeychainDao keychainDao = new KeychainDaoImpl();
        keychainDao.insert(keychain);
    }

    @Test
    public void jdbcTest() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/zeeker?nuseUnicode=true&characterEncoding=UTF-8", "root", "zhou");

        PreparedStatement preparedStatement = connection.prepareStatement("select * from keychain");
        ResultSet resultSet = preparedStatement.executeQuery();
    }
}
