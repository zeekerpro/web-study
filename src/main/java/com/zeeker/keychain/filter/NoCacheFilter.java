/**
 * @fileName :     NoCacheFilter
 * @author :       zeeker
 * @date :         1/30/18 8:21 AM
 * @description :  控制动态jsp数据不在客户端缓存
 */

package com.zeeker.keychain.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class NoCacheFilter extends BaseFilter{

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Prama", "no-cache");
        filterChain.doFilter(request, response);
    }

}
