/**
 * @fileName :     EncodingFilter
 * @author :       zeeker
 * @date :         1/30/18 8:01 AM
 * @description :   统一全站编码格式
 */

package com.zeeker.keychain.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class EncodingFilter extends BaseFilter{

    // 默认字符集
    private static final String DEFAULT_CHARSET = "UTF-8";

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        // 获取要设置的字符集
        String charset = filterConfig.getInitParameter("charset");
        charset = charset == null ? DEFAULT_CHARSET : charset;

        request.setCharacterEncoding(charset);
        response.setCharacterEncoding(charset);
        response.setContentType("text/html;charset=" + charset);
        filterChain.doFilter(new RequestWapper(request), response);
    }


    /**
     * 包装 request ，解决 get 请求方式乱码问题
     */
    private final class RequestWapper extends HttpServletRequestWrapper{

        private HttpServletRequest request;

        public RequestWapper(HttpServletRequest request) {
            super(request);
            this.request = request;
        }

        @Override
        public String getParameter(String name) {
            String value = request.getParameter(name);
            if ("get".equalsIgnoreCase(request.getMethod()) && value != null){
                try {
                    value = new String(value.getBytes("iso8859-1"), request.getCharacterEncoding());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    throw new  RuntimeException();
                }
            }
            return value;
        }
    }

}
