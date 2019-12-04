package com.perfree.common;

import cn.hutool.core.io.FileUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * TODO
 *
 * @author YinPengFei
 */
public class DownlaodUtils {

    /**
     * 下载文件,并在下载完成之后删除
     * @param file
     * @param response
     * @return
     */
    public static Boolean downloadFile(File file, HttpServletResponse response,Boolean downloadDoneIsDel) {
        String fileName = file.getName();
        if (file.exists()) {
            response.setContentType("application/force-download");
            fileName = new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
                os.flush();
                os.close();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
               if(downloadDoneIsDel){
                   FileUtil.del(file);
               }
            }
        }
        return false;
    }
}
