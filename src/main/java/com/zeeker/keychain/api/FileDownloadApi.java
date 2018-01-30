/**
 * @fileName :     FileDownloadApi
 * @author :       zeeker
 * @date :         1/30/18 12:23 AM
 * @description :  文件下载
 */

package com.zeeker.keychain.api;

import com.zeeker.utils.file.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/file/download")
public class FileDownloadApi extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileUUIDName = req.getParameter("fileName");
        FileUtils.download(fileUUIDName, resp);
    }
}
