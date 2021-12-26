package com.tareqyassin.bigslice.utils;

import android.content.ContentResolver;
import android.net.Uri;
import android.webkit.MimeTypeMap;

import androidx.appcompat.app.AppCompatActivity;

public class UtilsFunctions {

    public static String getFileExtension(AppCompatActivity activity, Uri uri){
        ContentResolver contentResolver = activity.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }


}
