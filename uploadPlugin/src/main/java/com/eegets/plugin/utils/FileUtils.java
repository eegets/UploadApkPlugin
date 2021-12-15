package com.eegets.plugin.utils;

import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wangkai on 2021/12/10 15:09
 * <p>
 * Desc TODO
 */
public class FileUtils {
    public static void saveToLocal(String imageUrl, String imagePath) throws IOException {
        ByteArrayOutputStream out= QRCode.from(imageUrl).to(ImageType.PNG).stream();
        byte[] data = out.toByteArray();
        OutputStream oute = new FileOutputStream(new File(imagePath));
        oute.write(data);
        oute.flush();
        oute.close();
    }
}
