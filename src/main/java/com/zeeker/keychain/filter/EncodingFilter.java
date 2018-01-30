/**
 * @fileName :     EncodingFilter
 * @author :       zeeker
 * @date :         1/30/18 8:01 AM
 * @description :   统一全站编码格式
 */

package com.zeeker.keychain.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class EncodingFilter extends BaseFilter{

    // 默认字符集
    private static final String DEFAULT_CHARSET = "UTF-8";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        super.doFilter(servletRequest, servletResponse, filterChain);
        // 获取要设置的字符集
        String charset = filterConfig.getInitParameter("charset");
        charset = charset == null ? DEFAULT_CHARSET : charset;
        request.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        response.setContentType("text/html;charset=" + charset);
        filterChain.doFilter(request, response);

    }

}
