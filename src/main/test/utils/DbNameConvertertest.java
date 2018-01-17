/**
 * @fileName :     DbNameConvertertest
 * @author :       zeeker
 * @date :         11/01/2018 17:19
 * @description :
 */

package utils;

import com.zeeker.utils.jdbc.DbNameConveter;
import org.junit.Test;

public class DbNameConvertertest {

    @Test
    public void camelToUnderlinetest(){
       String str = "CreateTimeD" ;
        System.out.println(DbNameConveter.className2TableName(str));

    }

    @Test
    public void testname(){
        System.out.println(DbNameConveter.className2TableName(this.getClass().getSimpleName()));
    }


    @Test
    public void test(){
        String str = "createTime";

        String[] strings = str.split("[A-Z]");
        for (String s : strings){
            System.out.println(s);
        }

        String[] strings1 = str.split("[a-z]+");
        for (String s : strings1){
            System.out.println(s);
        }
    }
}
