package com.example.mind.voicecameraexa;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class CameraActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_ALL_PERMISSIONS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (needPermissions(this)) {
            requestPermissions();
        }else if (intent != null){
            intent.setComponent(null);
            intent.setPackage("com.google.android.GoogleCamera");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }else {
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ALL_PERMISSIONS:
                boolean hasAllPermissions = true;
                for (int i = 0; i < grantResults.length; ++i) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        hasAllPermissions = false;
                    }
                }
                if (hasAllPermissions) {
                    finish();
                } else {
                    Toast.makeText(this, "Unable to get all required permissions", Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
                break;
            default:
        }
    }

    static public boolean needPermissions(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return activity.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED
                    || activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermissions() {
        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        };
        requestPermissions(permissions, PERMISSIONS_REQUEST_ALL_PERMISSIONS);
    }
}
