package com.example.library.UI;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.library.R;

public class PermissionUtils {
    public static final int PICK_IMAGE_REQUEST = 1;
    public static final int CAMERA_REQUEST = 2;

    public static void requestImageSource(Activity activity, ImageSourceCallback callback) {
        new androidx.appcompat.app.AlertDialog.Builder(activity)
                .setTitle(R.string.add_photo)
                .setItems(R.array.image_source_options, (dialog, which) -> {
                    switch (which) {
                        case 0: checkCameraPermission(activity); break;
                        case 1: checkStoragePermission(activity); break;
                    }
                })
                .show();
    }

    private static void checkCameraPermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
        } else {
            openCamera(activity);
        }
    }

    private static void checkStoragePermission(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_REQUEST);
        } else {
            openGallery(activity);
        }
    }

    private static void openCamera(Activity activity) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }

    private static void openGallery(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }



    public interface ImageSourceCallback {
        void onImageSelected(int requestCode, Intent data);
    }
}
