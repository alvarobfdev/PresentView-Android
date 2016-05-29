package com.apps.alvarobanofos.presentview;
 
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.Window;

import com.apps.alvarobanofos.presentview.Helpers.Login;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
 
public class SplashScreenActivity extends Activity {
 
    // Set the duration of the splash screen
    private static final long SPLASH_SCREEN_DELAY = 1500;

    private final static int REQUEST_PERMISSIONS_ID = 101;
    public final static int REGISTER_FROM_GOOGLE = 1;
    public final static int REGISTER_FROM_FORM = 2;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
 
        // Set portrait orientation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // Hide title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
 
        setContentView(R.layout.splash_screen);
 
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if(checkAllPermissions()) {
                    Login.getInstance().loginIfUserLogged(SplashScreenActivity.this);
                }

                finish();
            }
        };
 
        // Simulate a long loading process on application startup.
        Timer timer = new Timer();
        timer.schedule(task, SPLASH_SCREEN_DELAY);
    }

    private boolean checkAllPermissions() {

        ArrayList<String> permissionToRequest = new ArrayList<String>();

        boolean result = true;

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionToRequest.add(android.Manifest.permission.READ_PHONE_STATE);
            result = false;
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionToRequest.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
            result = false;
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
            permissionToRequest.add(android.Manifest.permission.SET_ALARM);
            result = false;
        }

        if(permissionToRequest.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    permissionToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_ID);

        }
        return result;
    }

    /* Metodo que recoge el resultado asincrono de la
        solicitud de permisos en caso de ser aceptados
        reinicia actividad para volver a cargar elementos
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean permission_granted = true;
        if(requestCode == REQUEST_PERMISSIONS_ID) {
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_DENIED) {
                    permission_granted = false;
                }
            }
        }

        if(permission_granted) {
            Login.getInstance().loginIfUserLogged(SplashScreenActivity.this);
            finish();
        }

    }
 
}