/**
 * @fileName :     RequestParser
 * @author :       zeeker
 * @date :         1/29/18 8:10 AM
 * @description :  request 解析器
 */

package com.zeeker.utils;

import com.zeeker.utils.file.FileUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RequestParser {
    public static String NORMAL_ITEMS = "normal";
    public static String MULTIPART_ITEMS = "multipart";

    /**
     * 将 post 请求的请求数据解析为普通输入项和上传的文件项
     * @param request
     * @return
     */
    public static Map<String, List<FileItem>> parse(HttpServletRequest request){
        Map<String, List<FileItem>> map = new HashMap<>();
        List<FileItem> normalItems = new ArrayList<>();
        List<FileItem> multipartItems = new ArrayList<>();
        ServletFileUpload fileUpload = FileUtils.getFileUpload();
        if (ServletFileUpload.isMultipartContent(request)) {
            try {
                List<FileItem> fileItems = fileUpload.parseRequest(request);
                for (FileItem fileItem : fileItems) {
                    if (fileItem.isFormField()) {
                        normalItems.add(fileItem);
                    } else {
                        multipartItems.add(fileItem);
                    }
                }
                map.put(NORMAL_ITEMS, normalItems);
                map.put(MULTIPART_ITEMS, multipartItems);
            } catch (FileUploadBase.FileSizeLimitExceededException e){
                e.printStackTrace();
                throw  new RuntimeException("文件大小超出限制");
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
        }

        return map;
    }
}
