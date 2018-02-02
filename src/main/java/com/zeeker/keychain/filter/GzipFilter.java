/**
 * @fileName :     GZIPFilter
 * @author :       zeeker
 * @date :         2/1/18 12:51 AM
 * @description :  response数据压缩,
 *
 *  step1 : 包装response，将response的数据取出写到缓冲当中
 *  step2 ： 取出缓冲的数据，压缩以后再返回给客户端
 */

package com.zeeker.keychain.filter;

import com.zeeker.utils.compress.Gzip;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class GzipFilter extends BaseFilter{

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        ResponseWapper responseWapper = new ResponseWapper(response);
        filterChain.doFilter(request, responseWapper);
        // 取出数据
        byte[] outData = responseWapper.getBuffer();
        // 压缩数据
        byte[] compressdData = Gzip.compress(outData);
        // 将数据发送给客户端
        response.setHeader("content-endoding", "gzip");
        response.setHeader("content-length", String.valueOf(compressdData.length));
        response.getOutputStream().write(compressdData);
    }


    /**
     * 将返回给客户端你的数据重定向到内部的缓冲区中
     */
    private final class ResponseWapper extends HttpServletResponseWrapper{

        private HttpServletResponse response;

        private ByteArrayOutputStream bufferStream = new ByteArrayOutputStream();
        private PrintWriter printWriter ;

        public ResponseWapper(HttpServletResponse response) {
            super(response);
            this.response = response;
        }

        @Override
        public ServletOutputStream getOutputStream() throws IOException {
            return new OutpuStreamWapper(bufferStream);
        }

        @Override
        public PrintWriter getWriter() throws IOException {
            printWriter = new PrintWriter(new OutputStreamWriter(bufferStream, response.getCharacterEncoding()));
            return printWriter;
        }

        /**
         *  获取缓冲的数据
         * @return
         */
        public byte[] getBuffer(){
            if (printWriter != null){
                printWriter.flush();
            }
            return bufferStream.toByteArray();
        }
    }

    private final class OutpuStreamWapper extends ServletOutputStream {

        private ByteArrayOutputStream bufferStream;

        public OutpuStreamWapper(ByteArrayOutputStream bufferStream) {
            this.bufferStream = bufferStream;
        }

        /**
         * 将数据写到缓冲区中，而不是直接写到客户端，相当于输出重定向
         * @param b
         * @throws IOException
         */
        @Override
        public void write(int b) throws IOException {
            bufferStream.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }

    }
}
