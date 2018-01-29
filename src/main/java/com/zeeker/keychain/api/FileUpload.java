/**
 * @fileName :     FileUpload
 * @author :       zeeker
 * @date :         1/28/18 3:33 AM
 * @description :
 */

package com.zeeker.keychain.api;

import com.zeeker.utils.RequestParser;
import com.zeeker.utils.file.FileUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet(value = "/upload")
public class FileUpload extends HttpServlet {
    private static final long serialVersionUID = -1581940926334627058L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 中文乱码
        req.setCharacterEncoding("utf-8");
        Map<String, List<FileItem>> map = RequestParser.parse(req);
        List<FileItem> uploadFile = map.get(RequestParser.MULTIPART_ITEMS);
        FileUtils.upload(uploadFile);
    }
}
