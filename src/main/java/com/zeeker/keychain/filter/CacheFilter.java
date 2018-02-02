/**
 * @fileName :     CacheFilter
 * @author :       zeeker
 * @date :         1/30/18 8:52 AM
 * @description :   控制静态资源缓存时间
 */

package com.zeeker.keychain.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CacheFilter extends BaseFilter{

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 获取用户想要访问的资源
        String uri = request.getRequestURI();

        // 获取该资源的缓存时间
        int expires = 0;
        if (uri.endsWith(".jpg")){
            expires = Integer.valueOf(filterConfig.getInitParameter("jpg"));
        }else if (uri.endsWith(".css")){
            expires = Integer.valueOf(filterConfig.getInitParameter("css"));
        }else{
            expires = Integer.valueOf(filterConfig.getInitParameter("js"));
        }

        response.setDateHeader("expires", System.currentTimeMillis() + expires * 60 * 1000);

        filterChain.doFilter(request, response);
    }

}
