package com.prographer.epub.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by prographers on 2015-02-27.
 */
public class FileDownload {
    static Logger log = LoggerFactory.getLogger(FileDownload.class);
    /**
     * URL에 있는 파일을 다운로드 한다.
     *
     * @param httpUrl      파일 경로
     * @param downloadPath 파일이 저장될 절대경로
     * @return 로컬 저장 경로
     */
    public static String download(String httpUrl, String downloadPath) {
        String localFilePath = null;
        if (httpUrl == null) return localFilePath;
        String[] paths = httpUrl.split("/");

        File downDir = new File(downloadPath);
        if(!downDir.exists())
            downDir.mkdirs();

        localFilePath = downloadPath + "/" + paths[paths.length - 1];


        //기존에 다운 받은 파일이 있을 경우 건너뜀
        File locFile = new File(localFilePath);
        if (locFile.exists()) return localFilePath;


        BufferedOutputStream out = null;
        InputStream in = null;
        try {
            URL url = new URL(httpUrl);

            out = new BufferedOutputStream(new FileOutputStream(localFilePath));
            URLConnection conn = url.openConnection();
            in = conn.getInputStream();

            byte[] buffer = new byte[1024];
            int numRead;
            while ((numRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, numRead);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            try {
                if (out != null) {out.close();}
                if (in != null) { in.close();}
            } catch (IOException ioe) {
                log.error(ioe.getMessage(),ioe);
            }
        }
        return localFilePath;
    }

}
