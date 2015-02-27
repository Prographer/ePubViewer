package com.prographer.epub.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Created by prographers on 2015-02-27.
 */
public class ZipUtil {
    private static final byte[] buf = new byte[1024];

    public static void unzip(String targetZip) throws Exception {
        ZipInputStream in = null;

        try {
            int lastPos = targetZip.lastIndexOf(".");
            String dirPath = targetZip.substring(0, lastPos) + "/";
            File fCompleteDir = new File(dirPath);
            if (fCompleteDir.exists()) {
                //디렉토리가 존재 할 경우 압축이 풀린것으로 보고 빠져나감.
                return;
            }

            fCompleteDir.mkdirs();
            in = new ZipInputStream(new FileInputStream(targetZip));
            ZipEntry entry = null;
            while ((entry = in.getNextEntry()) != null) {
                String path = dirPath + entry.getName();
                if (entry.getName().lastIndexOf("/") > 0) {
                    new File(path.substring(0, path.lastIndexOf("/"))).mkdirs();
                }
                if (!entry.isDirectory()) {
                    FileOutputStream out = new FileOutputStream(path, false);
                    int bytes_read;
                    while ((bytes_read = in.read(buf)) != -1) {
                        out.write(buf, 0, bytes_read);
                    }
                    out.close();
                }
            }
        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            if (in != null)
                in.close();
        }
    }
}
