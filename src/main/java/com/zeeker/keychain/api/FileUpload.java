/**
 * @fileName :     FileUpload
 * @author :       zeeker
 * @date :         1/28/18 3:33 AM
 * @description :
 */

package com.zeeker.keychain.api;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
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
import java.util.UUID;

@WebServlet(value = "/upload")
public class FileUpload extends HttpServlet {
    private static final long serialVersionUID = -1581940926334627058L;

    private static final List<String> supportFileType = new ArrayList<>();

    static {
        supportFileType.add(".jpeg");
        supportFileType.add(".png");
        supportFileType.add(".jpg");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 中文乱码
        req.setCharacterEncoding("utf-8");

        // 上传文件的保存目录和上传文件的缓冲目录
        String savePath = this.getServletContext().getRealPath("/WEB-INF/upload");
        String tempPath = this.getServletContext().getRealPath("/WEB-INF/temp");
        File saveDir= new File(savePath);
        File tempDir = new File(tempPath);
        if (!saveDir.exists()){
            saveDir.mkdir();
        }
        if (!tempDir.exists()){
            tempDir.mkdir();
        }

        try{
            // 1. 获取解析工厂
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // 设置缓冲目录大小和路径，当上传的文件大于缓冲区大小时就会生成一个临时文件存放到临时目录中；
            diskFileItemFactory.setSizeThreshold(1024 * 1024);
            diskFileItemFactory.setRepository(tempDir);
            // 2. 获取解析器
            ServletFileUpload upload = new ServletFileUpload(diskFileItemFactory);
            upload.setHeaderEncoding("utf-8");
            // 设置单个上传文件的最大大小
            upload.setFileSizeMax(1024 * 1024 * 5);
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
                        String fileName = fileItem.getName().substring(fileItem.getName().lastIndexOf(File.separator) + 1);
                        if (fileName == null || fileName.trim().equals("")){
                            // 如果当前 item没有文件，则继续遍历下一个item
                            continue;
                        }
                        String extName = fileName.substring(fileName.lastIndexOf("."));
                        if (!supportFileType.contains(extName)){
                            throw new RuntimeException("不支持的文件类型: " +  extName);
                        }
                        InputStream inputStream = null;
                        // 保存的文件名
                        String saveFileName = generateFileName(fileName);
                        // 子目录
                        File saveFileChildPath = generateSavePath(savePath, fileName);
                        OutputStream outputStream = new FileOutputStream(saveFileChildPath.getPath() + File.separator + saveFileName);
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
                            if (outputStream != null){
                                try {
                                    outputStream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            // 删除临时文件, 一定要在输入输出流关闭以后才能删除，否则删不掉，因为输入输出流是建立在缓冲文件上的
                            fileItem.delete();
                        }
                    }
                }
            }

        }
        catch(FileUploadBase.FileSizeLimitExceededException e){
            throw new RuntimeException("文件大小超出限制");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 根据文件名生成文件的保存目录-hash算法产生目录
     * @param path : 总目录
     * @param fileName
     * @return
     */
    public File generateSavePath(String path, String fileName){
        int hashcode = fileName.hashCode();
        int firstdir = hashcode & 0xf;
        int seconddir = (hashcode>>4) & 0xf;
        String savedir = path + File.separator + firstdir + File.separator + seconddir;
        File saveFile = new File(savedir);
        if (!saveFile.exists()){
            saveFile.mkdirs();
        }
        return saveFile;
    }

    /**
     * 针对上传文件的文件名生成一个唯一的文件名，防止文件覆盖
     * @param fileName
     * @return
     */
    private String generateFileName(String fileName) {
        return UUID.randomUUID().toString() + "_" + fileName;
    }
}
