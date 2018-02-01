/**
 * @fileName :     CoderFilter
 * @author :       zeeker
 * @date :         1/31/18 7:52 PM
 * @description :  代码转义
 */

package com.zeeker.keychain.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

public class HttpFilter extends BaseFilter{
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        super.doFilter(servletRequest, servletResponse, filterChain);
        filterChain.doFilter(new EscapeRequest(request), response);
    }


    /**
     * 将请求数据转义
     */
    private final class EscapeRequest extends HttpServletRequestWrapper {
        public EscapeRequest(HttpServletRequest request) {
            super(request);
        }

        @Override
        public String getParameter(String name) {
            String value = request.getParameter(name);
            if (value != null){
                value = escape(value);
            }
            return value;
        }

        private String escape(String value){
            if (value == null) {
                return null;
            }

            char[] content = new char[value.length()];
            value.getChars(0, value.length(), content, 0);
            StringBuilder result = new StringBuilder(content.length + 50);
            for (int i = 0 ; i < content.length; i++){
                switch (content[i]){
                    case '<' :
                       result.append("&lt;");
                       break;
                    case '>' :
                        result.append("&gt;");
                        break;
                    case '&' :
                        result.append("&amp;");
                        break;
                    case '"':
                        result.append("&quot;");
                        break;
                    default :
                        result.append(content[i]);
                }
            }

            return result.toString();
        }
    }

}
