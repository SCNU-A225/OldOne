package com.campus.oldone.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.File;

public class ImageUtil {
    public static final String MIME_JPEG = "image/jpeg";
    public static final String MIME_PNG = "image/png";

    public static String getMimeType(File file){
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getPath(),options);
        return options.outMimeType;
    }

    public static String getSuffix(String mimeType){
        switch (mimeType){
            case MIME_JPEG: return ".jpg";
            case MIME_PNG: return ".png";
            default: return "";
        }
    }
}
