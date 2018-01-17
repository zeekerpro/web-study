/**
 * @fileName :     FileSystemClassLoader
 * @author :       zeeker
 * @date :         16/01/2018 16:43
 * @description :  自定义文件系统类加载器
 */

package com.zeeker.utils.classLoader;

import java.io.*;

public class FileSystemClassLoader extends ClassLoader{

    private  String CLASS_PATH;

    public FileSystemClassLoader(String CLASS_PATH) {
        this.CLASS_PATH = CLASS_PATH;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        // 检查类是否已经被加载
        Class<?> c = findLoadedClass(name);

        if (c == null){

            // 委派给父类加载，父类加载不到会抛异常，所以先try，加载不到再自己加载
            ClassLoader parentClassLoader = this.getParent();
            try{
                c = parentClassLoader.loadClass(name);
            }catch (Exception e){}

            // 父类没加载到，子类继续加载
            if (c == null){
                // 获取字节码文件
                byte[] classData = getClassData(name);
                if (classData == null){
                    throw  new ClassNotFoundException();
                }
                //获取到了字节码，将字节码数据 链接、初始化，并生成 Class 对象
                defineClass(name, classData, 0, classData.length);
            }
        }

        return c;
    }

    /**
     * 通过 io 流读取 class 字节码文件
     * @param name
     * @return
     */
    private byte[] getClassData(String name){

        String path = CLASS_PATH + File.separator + name.replace('.', File.separatorChar) + ".class";
        FileInputStream inputStream  = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            inputStream = new FileInputStream(path);
            byte[] buffer = new byte[1024];
            int temp = 0;
            while ( (temp = inputStream.read(buffer)) != -1 ){
                baos.write(buffer, 0, temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null){
               try {
                   inputStream.close();
               } catch (IOException e) {
                   e.printStackTrace();
               }
            }
        }

        return baos.toByteArray();
    }
}
