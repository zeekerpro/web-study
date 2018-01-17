/**
 * @fileName :     NetClassLoader
 * @author :       zeeker
 * @date :         17/01/2018 10:31
 * @description :
 */

package com.zeeker.utils.classLoader;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class NetClassLoader extends ClassLoader{

    private  String  rootUrl;

    public NetClassLoader(String rootUrl) {
        this.rootUrl = rootUrl;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        // first, check if the class has been loaded, acturally this step has been done by loadClass() method of ClassLoader
        // Class<?> c= findLoadedClass(name);

        byte[] classData = getClassData(name);
        if (classData == null){
            throw new ClassNotFoundException();
        }
        Class<?> c = defineClass(name, classData, 0, classData.length);

        return  c;
    }

    /**
     * 从网络流总读取字节码
     * @param name
     * @return
     */
    private byte[] getClassData(String name) {
        String classPath = rootUrl + File.separator + name.replace('.', File.separatorChar) + ".class";
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try{
            URL classUrl = new URL(classPath);
            inputStream = classUrl.openStream();
            byte[] buffer = new byte[1024];
            int temp = 0;

            while ( (temp = inputStream.read(buffer)) != -1 ){
                byteArrayOutputStream.write(buffer, 0, temp);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (inputStream != null){
                try{
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return  byteArrayOutputStream.toByteArray();
    }

}
