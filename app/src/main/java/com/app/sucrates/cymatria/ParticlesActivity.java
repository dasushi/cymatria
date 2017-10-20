package com.app.sucrates.cymatria;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Stephen on 9/8/2017.
 */
public class ParticlesActivity extends AppCompatActivity {
    private GLSurfaceView glSurfaceView;
    private boolean rendererSet = false;
    private static final String TAG = "ParticlesActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glSurfaceView = new GLSurfaceView(this);
        final ActivityManager activityManager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        final ConfigurationInfo configInfo = activityManager.getDeviceConfigurationInfo();
        final boolean supportsES2 = configInfo.reqGlEsVersion >= 0x20000;
        Log.v(TAG, "OpenGL Version: " + configInfo.reqGlEsVersion);
        final boolean isEmulator =
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) &&
                        (Build.FINGERPRINT.startsWith("generic") ||
                                Build.FINGERPRINT.startsWith("unknown") ||
                                Build.MODEL.contains("google_sdk") ||
                                Build.MODEL.contains("Emulator") ||
                                Build.MODEL.contains("Android SDK built for x86"));
        final ParticlesRenderer renderer = new ParticlesRenderer(this);
        if (supportsES2 || isEmulator) {
            Log.v(TAG, "Starting OpenGL ES2.0 Renderer");
            glSurfaceView.setEGLContextClientVersion(2);
            //glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
            glSurfaceView.setRenderer(renderer);
            rendererSet = true;
        } else {
            Toast.makeText(this, "OpenGL ES2.0 not supported", Toast.LENGTH_LONG).show();
            Log.w(TAG, "OpenGL version insufficient or emulator error");
            return;
        }

        setContentView(glSurfaceView);
        //setContentView(R.layout.activity_open_gl);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(rendererSet) {
            glSurfaceView.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(rendererSet){
            glSurfaceView.onResume();
        }
    }
}
