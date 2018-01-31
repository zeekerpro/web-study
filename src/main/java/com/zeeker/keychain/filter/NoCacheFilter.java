/**
 * @fileName :     NoCacheFilter
 * @author :       zeeker
 * @date :         1/30/18 8:21 AM
 * @description :  控制jsp不在客户端浏览器缓存
 */

package com.zeeker.keychain.filter;

import javax.servlet.*;
import java.io.IOException;

public class NoCacheFilter extends BaseFilter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        super.doFilter(servletRequest, servletResponse, filterChain);
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Prama", "no-cache");
        filterChain.doFilter(request, response);
    }

}
