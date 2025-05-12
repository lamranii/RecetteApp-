package com.example.library.UI;


import android.graphics.Bitmap;
import java.io.ByteArrayOutputStream;

public class ImageUtils {
    public static byte[] bitmapToByteArray(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        return stream.toByteArray();
    }
}
