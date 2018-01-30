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

public class BaseFilter implements Filter {

    protected FilterConfig filterConfig;
    protected HttpServletResponse response;
    protected HttpServletRequest request;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        this.request = (HttpServletRequest)servletRequest;
        this.response = (HttpServletResponse)servletResponse;
    }

    @Override
    public void destroy() {

    }
}
