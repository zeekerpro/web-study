/**
 * @fileName :     ClassTest
 * @author :       zeeker
 * @date :         1/27/18 8:54 PM
 * @description :
 */

package utils;

import com.mysql.cj.jdbc.SuspendableXAConnection;
import org.junit.Test;

public class ClassTest {

    @Test
    public void longTest(){
        System.out.println(10000 / 10000);
    }


    @Test
    public void threadLodalTest(){
        ThreadLocal<Integer>  threadLocal = new ThreadLocal<>();
        threadLocal.set(90);
        threadLocal.set(80);
        System.out.println(threadLocal.get());
        System.out.println(threadLocal.get());
        throw new RuntimeException("不支持的文件类型");
    }

}
