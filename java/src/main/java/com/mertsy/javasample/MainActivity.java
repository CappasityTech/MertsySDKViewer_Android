package com.mertsy.javasample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.mertsy.common.TokenStatus;
import com.mertsy.sdk.MertsySDK;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    private boolean hasStoragePermission() {
        return isPermissionGranted(targetStoragePermission());
    }

    private boolean hasCameraPermission() {
        return isPermissionGranted(Manifest.permission.CAMERA);
    }

    private String targetStoragePermission() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU ?
                Manifest.permission.READ_MEDIA_IMAGES :
                Manifest.permission.WRITE_EXTERNAL_STORAGE;
    }

    private boolean isPermissionGranted(String permission) {
        return ContextCompat.checkSelfPermission(
                this,
                permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    private boolean isTokenValid() {

        switch (MertsySDK.getTokenStatus()) {
            case CHECKING: {
                String msg = "Token validation. Try again later";
                Log.e(TAG, msg);
                showToast(msg);
                return false;
            }
            case ERROR: {
                String msg = "Wrong token";
                Log.e(TAG, msg);
                showToast(msg);
                return false;
            }
            default:
                return true;
        }
    }

    @Override
    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppCompatTextView tvTokenStatus = findViewById(R.id.tvTokenStatus);
        AppCompatTextView tvFrameworkVersion = findViewById(R.id.tvFrameworkVersion);

        AppCompatButton btnView = findViewById(R.id.btnView);

        //Required for capturing
        requestCameraAndStoragePermissions();

        tvTokenStatus.setText("Token status: Checking");

        MertsySDK.setOnTokenStatusChangedListener(tokenStatus -> {
            String status = "Token status: ";
            if (tokenStatus == TokenStatus.SUCCESS) {
                status += "Success";
            } else {
                status += "Error";
            }

            tvTokenStatus.setText(status);
        });

        tvFrameworkVersion.setText("SDK Version: " + MertsySDK.getVersion());

        btnView.setOnClickListener(v -> {
            if (isTokenValid()) {
                startActivity(new Intent(this, ModelViewActivity.class));
            }
        });

    }


    private void requestCameraAndStoragePermissions() {
        String[] permissions = new String[]{Manifest.permission.CAMERA, targetStoragePermission()};

        if (!hasStoragePermission() || !hasCameraPermission())
            requestPermissions(permissions, 123);
    }
}