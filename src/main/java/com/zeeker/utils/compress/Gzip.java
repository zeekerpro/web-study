/**
 * @fileName :     Gzip
 * @author :       zeeker
 * @date :         02/02/2018 12:20 AM
 * @description :
 */

package com.zeeker.utils.compress;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class Gzip {

    /**
     * 压缩数据
     * @param src
     * @return
     */
    public static byte[] compress(byte[] src){
        byte[] desData = null;

        try{
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gOutStream = new GZIPOutputStream(byteArrayOutputStream);
            gOutStream.write(src);
            gOutStream.close();
            desData = byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return desData;
    }
}
