package com.apps.alvarobanofos.presentview;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.apps.alvarobanofos.presentview.Helpers.Login;
import com.apps.alvarobanofos.presentview.Helpers.Registration;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class InitialActivity extends FragmentActivity implements SignInFragment.OnFragmentInteractionListener,
        GoogleApiClient.OnConnectionFailedListener, CompleteDataFragment.OnCompleteDataFragmentListener {

    private final static int REQUEST_PERMISSIONS_ID = 101;
    public final static int REGISTER_FROM_GOOGLE = 1;
    public final static int REGISTER_FROM_FORM = 2;

    Button btnSignIn;
    private GoogleApiClient mGoogleApiClient;
    private int typeRegister;

    private String userGoogleId;
    private String userEmail;
    private  String simId;
    private int userGender;
    private boolean creating = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseMessaging.getInstance().subscribeToTopic("global");
        creating = true;

        if(checkAllPermissions()) {
            Login.getInstance().loginIfUserLogged(this, this);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        creating = false;
    }

    public void continueCreating() {
        setContentView(R.layout.activity_initial);


        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(Plus.SCOPE_PLUS_LOGIN, Plus.SCOPE_PLUS_PROFILE)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .addApi(Plus.API)
                .build();

        btnSignIn = (Button) findViewById(R.id.btnIniciarSesion);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSignInFragment();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startSignInFragment() {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack("initialScreen");
        fragmentTransaction.setCustomAnimations(R.animator.slide_in_up, R.animator.slide_out_down, R.animator.slide_in_up, R.animator.slide_out_down);
        fragmentTransaction.replace(R.id.layoutInitial, SignInFragment.newInstance(mGoogleApiClient), "signInFragment");
        fragmentTransaction.commit();
    }

    private void startCompleteDataFragment(String backStack) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.addToBackStack(backStack);
        fragmentTransaction.replace(R.id.layoutInitial, CompleteDataFragment.newInstance(), "completeDataFragment");
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(int action, Object... params) {
        if(action == SignInFragment.COMPLETE_DATA) {
            typeRegister = (int) params[0];
            userGoogleId = (String) params[1];
            userEmail = (String) params[2];
            userGender = (int) params[3];
            simId = (String) params[4];
            startCompleteDataFragment("signInFragment");
        }
    }

    @Override
    public void onBackPressed() {
        if(getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean checkAllPermissions() {

        ArrayList<String> permissionToRequest = new ArrayList<String>();

        boolean result = true;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            permissionToRequest.add(Manifest.permission.READ_PHONE_STATE);
            result = false;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            result = false;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM) != PackageManager.PERMISSION_GRANTED) {
            permissionToRequest.add(Manifest.permission.SET_ALARM);
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
            continueCreating();
        }

    }

    @Override
    public void onCompleteDataInteraction(Object... paramsForm) {
        if(typeRegister == REGISTER_FROM_GOOGLE) {
            Object[] params = {
                    userGoogleId,
                    userEmail,
                    userGender,
                    (String) paramsForm[0],
                    (int) paramsForm[1],
                    (int) paramsForm[2],
                    simId
            };
            Registration.getInstance().registerWithGoogle(this, params);
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("InitialActivity", "DESTROY");
    }
}
