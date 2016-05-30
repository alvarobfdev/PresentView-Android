package com.apps.alvarobanofos.presentview;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apps.alvarobanofos.presentview.Helpers.Login;
import com.apps.alvarobanofos.presentview.Helpers.Notifications;
import com.apps.alvarobanofos.presentview.Helpers.Registration;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.LoginByGoogleResult;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.StandardRegistration;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SignUpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment {

    private static final String TAG = "SignInFragment";
    private static final int RC_SIGN_IN = 9001;
    public static final int COMPLETE_DATA = 1;

    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInAccount googleAcct;
    private Person person;
    private String simId;
    private EditText etUser;
    private EditText etPass;
    private Context context;
    private Activity activity;

    private SignInFragment.OnFragmentInteractionListener mListener;



    private PresentViewApiClient.JsonApiRequestListener resultPVGoogleAPI = new PresentViewApiClient.JsonApiRequestListener() {
        @Override
        public void jsonApiRequestResult(Object object) {
            LoginByGoogleResult loginByGoogleResult = (LoginByGoogleResult) object;
            if(loginByGoogleResult.getStatus() == 1) {
                if(!loginByGoogleResult.isRegistered()) {

                    Registration.getInstance().registerByGoogleCompleteData(googleAcct, person, simId, mListener, etUser.getText().toString());
                }
                else {
                    Login.getInstance().loginUser(activity, loginByGoogleResult.getUser());
                }

            }
        }
    };

    private PresentViewApiClient.JsonApiRequestListener resultPVStandardReg = new PresentViewApiClient.JsonApiRequestListener() {
        @Override
        public void jsonApiRequestResult(Object object) {
            StandardRegistration standardRegistration = (StandardRegistration) object;
            if(standardRegistration.getStatus() == 1) {
                if(standardRegistration.isRegistered_yet()) {
                    if(standardRegistration.isNot_data_completed()) {
                        Registration.getInstance().registerByGoogleCompleteData(null, null, simId, mListener, etUser.getText().toString());
                    }
                    else Notifications.singleToast(context, "Email ya registrado!");
                }
                else if (standardRegistration.isRegistrated()) {
                    Registration.getInstance().registerByGoogleCompleteData(null, null, simId, mListener, etUser.getText().toString());

                }
                else {
                    Notifications.singleToast(context, "Fallo al registrar!");
                }

            }
        }
    };

    public SignUpFragment() {
        // Required empty public constructor
    }


    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment SignInFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(GoogleApiClient param1) {
        SignUpFragment fragment = new SignUpFragment();
        fragment.setmGoogleApiClient(param1);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGoogleApiClient = getmGoogleApiClient();
        context = getActivity().getApplicationContext();
        activity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        SignInButton googleButton = (SignInButton) view.findViewById(R.id.google_sign_up_button);
        setGooglePlusButtonText(googleButton, getString(R.string.google_signup_button_text));
        googleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignUp();
            }
        });
        Button signInBtn = (Button) view.findViewById(R.id.btnSignUp);
        etUser = (EditText) view.findViewById(R.id.et_correo);
        etPass = (EditText) view.findViewById(R.id.et_pass_signup);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                standardSignUp();
            }
        });


        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onCompleteDataInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignInFragment.OnFragmentInteractionListener) {
            mListener = (SignInFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }






    private void googleSignUp() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();



            PresentViewApiClient pApiClient = new PresentViewApiClient(activity, resultPVGoogleAPI);


            Map < String, String > json = new HashMap<>();
            this.googleAcct = acct;
            json.put("accountId", acct.getId());
            Log.d("LOGIN",acct.getDisplayName() );
            json.put("displayName", acct.getDisplayName());

            TelephonyManager mTelephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            simId = mTelephonyMgr.getSimSerialNumber();
            json.put("simId", simId);


            if(simId != null) {
                if(mGoogleApiClient.hasConnectedApi(Plus.API))
                    person = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);
                else person = null;
                pApiClient.requestJsonApi(PresentViewApiClient.LOGIN_BY_GOOGLE, new JSONObject(json));
            }



            else {
                Notifications.singleToast(getActivity().getApplicationContext(), "Su teléfono debe contener una tarjeta sim");
            }


        } else {
            Notifications.singleToast(getActivity().getApplicationContext(), "Fallo al utenticar");
        }
        Log.d("HERE", "HERE");
    }

    protected void setGooglePlusButtonText(SignInButton signInButton, String buttonText) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (int i = 0; i < signInButton.getChildCount(); i++) {
            View v = signInButton.getChildAt(i);

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                tv.setText(buttonText);
                return;
            }
        }
    }



    private void standardSignUp() {
        if(etPass.getText().length() < 3 || etUser.getText().length() < 3) {
            Notifications.singleToast(context, "Datos incorrectos!");
        }
        else {
            Map < String, String > json = new HashMap<>();
            TelephonyManager mTelephonyMgr = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            simId = mTelephonyMgr.getSimSerialNumber();
            json.put("simId", simId);
            json.put("user", etUser.getText().toString());
            json.put("pass", etPass.getText().toString());

            PresentViewApiClient presentViewApiClient = new PresentViewApiClient(
                    getActivity().getApplicationContext(),
                    resultPVStandardReg
            );

            presentViewApiClient.requestJsonApi(PresentViewApiClient.STANDARD_REGISTRATION, new JSONObject(json));
        }




    }

}
