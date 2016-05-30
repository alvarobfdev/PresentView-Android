package com.apps.alvarobanofos.presentview.Helpers;

import android.content.Context;
import android.widget.Toast;

import com.apps.alvarobanofos.presentview.InitialActivity;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.PresentViewApiClient;
import com.apps.alvarobanofos.presentview.PresentViewApiClient.RegisterFromGoogleResult;
import com.apps.alvarobanofos.presentview.SignInFragment;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.plus.model.people.Person;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alvarobanofos on 22/2/16.
 */
public class Registration {

    private static Registration registration = null;

    public void registerByGoogleCompleteData(GoogleSignInAccount account, Person person, String simId, SignInFragment.OnFragmentInteractionListener listener, String email) {

        int gender = 2;
        String name = "";
        if(person!=null) {
            gender = person.getGender();
        }
        int type_register = InitialActivity.REGISTER_FROM_GOOGLE;
        String id = "";
        if(account == null) {
            type_register = InitialActivity.REGISTER_FROM_FORM;
        }
        else {
            id = account.getId();
            if(email == null) {
                email = account.getEmail();
            }
            name = account.getDisplayName();
        }


        Object params[] = {type_register, id, email, gender, simId, name};
        listener.onFragmentInteraction(SignInFragment.COMPLETE_DATA, params);

        /*CompleteRegisterData completeRegisterData = new CompleteRegisterData();
        if(person.getBirthday() == null) {
            completeRegisterData.add(CompleteRegisterData.BIRTHDATE_VIEW);
        }

        if(person.getGender() != Person.Gender.FEMALE && person.getGender() != Person.Gender.MALE) {
            completeRegisterData.add(CompleteRegisterData.GENDER_VIEW);
        }

        completeRegisterData.throwFragment();*/

    }

    public void registerWithGoogle(final Context context, Object... params) {
        PresentViewApiClient presentViewApiClient = new PresentViewApiClient(context, new PresentViewApiClient.JsonApiRequestListener() {
            @Override
            public void jsonApiRequestResult(Object object) {
                RegisterFromGoogleResult registerFromGoogleResult = (RegisterFromGoogleResult) object;
                if(registerFromGoogleResult.getStatus() == 1) {
                    if(registerFromGoogleResult.isAlreadyRegistered())
                        Notifications.singleToast(context, "Ya existe una cuenta registrada para este dispositivo!", Toast.LENGTH_LONG);
                    else {
                        Login.getInstance().loginUser(context, registerFromGoogleResult.getUser());
                    }
                }
                else {
                    Notifications.singleToast(context, "Fallo al registrar usuario.");
                }
            }
        });

        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("google_id", (String) params[0]);
        jsonData.put("email", (String) params[1]);
        jsonData.put("gender", ""+params[2]);
        jsonData.put("birthdate", (String) params[3]);
        jsonData.put("provincia", ""+params[4]);
        jsonData.put("ciudad", ""+params[5]);
        jsonData.put("sim_id", (String) params[6]);
        jsonData.put("name", ""+params[7]);
        jsonData.put("surname", ""+params[8]);



        presentViewApiClient.requestJsonApi(PresentViewApiClient.REGISTER_FROM_GOOGLE, new JSONObject(jsonData));
    }

    public void standardRegister(final Context context, Object... params) {
         PresentViewApiClient.JsonApiRequestListener resultSignUp = new PresentViewApiClient.JsonApiRequestListener() {
            @Override
            public void jsonApiRequestResult(Object object) {
                RegisterFromGoogleResult registerFromGoogleResult  = (RegisterFromGoogleResult) object;
                if(registerFromGoogleResult.getStatus() == 1) {
                    if(!registerFromGoogleResult.isRegistered_ok()) {
                        Notifications.singleToast(context, registerFromGoogleResult.getMessage());
                    }
                    else {
                        Login.getInstance().loginUser(context, registerFromGoogleResult.getUser());
                    }
                }
                else {
                    Notifications.singleToast(context, "Fallo al registrar! Vuelve a intentarlo en unos instantes.");

                }
            }
        };

        PresentViewApiClient presentViewApiClient = new PresentViewApiClient(context, resultSignUp);
        Map<String, String> jsonData = new HashMap<>();
        jsonData.put("email", (String) params[0]);
        jsonData.put("gender", ""+params[1]);
        jsonData.put("birthdate", (String) params[2]);
        jsonData.put("provincia", ""+params[3]);
        jsonData.put("ciudad", ""+params[4]);
        jsonData.put("sim_id", (String) params[5]);
        jsonData.put("name", (String) params[6]);
        jsonData.put("surname", (String) params[7]);

        presentViewApiClient.requestJsonApi(PresentViewApiClient.STANDARD_REGISTRATION_COMPLETE, new JSONObject(jsonData));
    }

    public static Registration getInstance() {
        if(registration == null) {
            registration = new Registration();
        }

        return registration;
    }
}
