/**
 * @fileName :     FileListApi
 * @author :       zeeker
 * @date :         1/29/18 6:51 PM
 * @description :  列出网站所有图片
 */

package com.zeeker.keychain.api;

import com.zeeker.utils.file.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/file/list")
public class FileListApi extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<File> files = FileUtils.getAllFiles();
    }
}
