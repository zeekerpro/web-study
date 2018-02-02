/**
 * @fileName :     BaseFilter
 * @author :       zeeker
 * @date :         1/30/18 8:28 AM
 * @description :
 */

package com.zeeker.keychain.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class BaseFilter implements Filter {

    protected FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        handle(request, response, filterChain);
    }

    @Override
    public void destroy() {

    }

    protected abstract void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException;

}
