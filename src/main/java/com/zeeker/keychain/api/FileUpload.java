/**
 * @fileName :     FileUpload
 * @author :       zeeker
 * @date :         1/28/18 3:33 AM
 * @description :
 */

package com.zeeker.keychain.api;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

@WebServlet(value = "/upload")
public class FileUpload extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");

        // 中文乱码
        req.setCharacterEncoding("utf-8");

        File file = new File(savePath);
        if (!file.exists()){
            file.mkdir();
        }
        try{
            // 1. 获取解析工厂
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // 2. 获取解析器
            ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
            upload.setHeaderEncoding("utf-8");
            // 判断删除的数据是不是上传表单数据
            if (ServletFileUpload.isMultipartContent(req)) {
                // 3. 解析request
                List<FileItem> fileItemList = upload.parseRequest(req);
                // 4. 迭代解析
                for (FileItem fileItem : fileItemList) {

                    if (fileItem.isFormField()) {
                        // 普通输入项
                        String paramName = fileItem.getName();
                        String paramValue = fileItem.getString("UTF-8");
                    } else {
                        // 上传的文件
                        String fileName = fileItem.getName().substring(fileItem.getName().lastIndexOf(File.pathSeparatorChar) + 1);
                        InputStream inputStream = null;
                        OutputStream outputStream = new FileOutputStream(savePath + File.separator + fileName);
                        try {
                            inputStream = fileItem.getInputStream();
                            int len = 0;
                            byte[] buffer = new byte[1024];
                            while ((len = inputStream.read(buffer)) > 0) {
                                outputStream.write(buffer);
                            }
                        } finally {
                            if (inputStream != null) {
                                try {
                                    inputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            if (outputStream != null) {
                                try {
                                    outputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
