package com.example.mind.voicecameraexa;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class TakePictureActivity extends AppCompatActivity {
    private static String TAG = "TakePictureActivity";

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
        } else if (CameraActivity.needPermissions(this)) {
            startActivity(new Intent(this, CameraActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            finish();
            return;
        } else if (!isVoiceInteraction()) {
            if (intent != null) {
                intent.setComponent(null);
                intent.setPackage("com.google.android.GoogleCamera");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            finish();
            return;
        }

        setContentView(R.layout.activity_camera);
        CameraFragment fragment = CameraFragment.newInstance();
        fragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }
}
