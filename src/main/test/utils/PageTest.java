/**
 * @fileName :     PageTest
 * @author :       zeeker
 * @date :         18/01/2018 10:44
 * @description :
 */

package utils;

import com.zeeker.utils.page.Pageable;
import org.junit.Test;

public class PageTest {

    @Test
    public void pageTest(){
        Pageable pageable = new Pageable(1, 150);
        System.out.println("当前页 : " + String.valueOf(pageable.getPageIndex()));
        System.out.println("总页数 : " + pageable.getTotalPage());
        System.out.println("每页记录数 ： " + pageable.getPageSize());
        for (int i : pageable.getPageBar()){
            System.out.println(i);
        }
    }
}
