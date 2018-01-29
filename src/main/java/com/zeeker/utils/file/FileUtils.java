/**
 * @fileName :     FileUtils
 * @author :       zeeker
 * @date :         1/29/18 4:11 AM
 * @description :  文件上传下载工具类
 */

package com.zeeker.utils.file;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

public class FileUtils {
    private static final List<String> SUPPORT_FILE_TYPES = new ArrayList<>();
    private static File SAVE_PATH;
    private static File TEMP_PATH;
    private static int SIZE_THRESHOLD;
    private static long FILE_SIZE_MAX;

    // 初始化数据
    static {
        Properties properties = new Properties();
        InputStream inputStream = FileUtils.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(inputStream);
            String classPath = FileUtils.class.getClassLoader().getResource("/").getPath();
            File rootPath = new File(classPath).getParentFile();
            SAVE_PATH = new File( rootPath + properties.getProperty("file.savePath"));
            if (!SAVE_PATH.exists()){
                SAVE_PATH.mkdirs();
            }
            TEMP_PATH = new File( rootPath + properties.getProperty("file.tempPath"));
            if (!TEMP_PATH.exists()){
                TEMP_PATH.mkdirs();
            }
            SIZE_THRESHOLD = Integer.valueOf(properties.getProperty("file.sizeThreshold"));
            FILE_SIZE_MAX = Long.valueOf(properties.getProperty("file.fileSizeMax"));
            String supportTypes = properties.getProperty("file.supportTypes");
            if (!supportTypes.trim().equals("")){
                String[] types = supportTypes.split(",");
                for (String type : types) {
                    SUPPORT_FILE_TYPES.add(type.trim());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 上传文件
     * @param fileItems
     */
    public static void upload( List<FileItem> fileItems){
        for (FileItem fileItem : fileItems){
            if(!fileItem.isFormField()){
                String fileName = getOriginFileName(fileItem);
                if (!isSupport(fileName)){
                    throw  new RuntimeException("不支持的文件类型!");
                }
                try{
                    // 保存文件
                    saveFile(fileItem);
                }finally {
                    // 删除临时文件
                    fileItem.delete();
                }
            }
        }
    }

    /**
     * 获取解析器，并设置解析器参数
     * @return
     */
    public static ServletFileUpload getFileUpload(){
        // 1. 获取解析工厂, 并设置工厂的缓冲目录
        DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
        fileItemFactory.setSizeThreshold(SIZE_THRESHOLD);
        fileItemFactory.setRepository(TEMP_PATH);
        // 2. 生成解析器，并设置解析参数和监听进度
        ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
        upload.setFileSizeMax(FILE_SIZE_MAX);
        upload.setHeaderEncoding("utf-8");
        upload.setProgressListener(new ProgressListener() {
            private  long megaBytes = -1;
            @Override
            public void update(long pBytesRead, long pContentLength, int pItems) {

                long mBytes = pBytesRead / 1000000;
                if (megaBytes == mBytes) {
                    return;
                }
                megaBytes = mBytes;
                System.out.println("We are currently reading item " + pItems);
                if (pContentLength == -1) {
                    System.out.println("So far, " + pBytesRead + " bytes have been read.");
                } else {
                    System.out.println("So far, " + pBytesRead + " of " + pContentLength
                            + " bytes have been read.");
                }
            }
        });
        return upload;
    }


    /**
     * 确定该文件是否支持上传
     * @param fileName
     * @return
     */
    private static boolean isSupport(String fileName) {
        boolean support = false;
        if (fileName != null || !fileName.trim().equals("")){
            String fileExtName = fileName.substring(fileName.lastIndexOf("."));
            support = SUPPORT_FILE_TYPES.contains(fileExtName);
        }
        return support;
    }


    /**
     * 保存文件
     * @param fileItem
     */
    private static void saveFile(FileItem fileItem) {
        // 生成保存文件的子目录
        File savePath = getSavePath(fileItem);
        // 为了防止文件覆盖，给每个文件生成一个存储的文件名
        String saveFileName = getSaveFileName(fileItem);
        // 调用输入输出流写入文件
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            outputStream = new FileOutputStream(savePath.getPath() + File.separator + saveFileName);
            inputStream = fileItem.getInputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("保存文件失败");
        } finally {
            if (outputStream != null){
                try{
                    outputStream.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null){
                try{
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取文件的保存路径
     * @param fileItem
     * @return
     */
    private static File getSavePath(FileItem fileItem) {
        int hashCode = fileItem.hashCode();
        int firstDir = hashCode & 0xf;
        int secondDir = (hashCode >> 4) & 0xf;
        File path = new File(SAVE_PATH + File.separator + firstDir + File.separator + secondDir);
        if (!path.exists()){
            path.mkdirs();
        }
        return path;
    }

    /**
     * 获取文件保存名
     * @param fileItem
     * @return
     */
    private static String getSaveFileName(FileItem fileItem) {
        return UUID.randomUUID() + "_" + getOriginFileName(fileItem);
    }

    /**
     * 获取文件的原始文件名
     * @param fileItem
     * @return
     */
    private static String getOriginFileName(FileItem fileItem){
        String originFileName = null;
        String fullFileName = fileItem.getName();
        if (fullFileName !=null && !fullFileName.trim().equals("")){
            originFileName = fullFileName.substring(fullFileName.lastIndexOf(File.separator) + 1);
        }
        return originFileName;
    }

}
