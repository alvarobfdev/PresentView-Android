package com.apps.alvarobanofos.presentview;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.apps.alvarobanofos.presentview.Adapters.MunicipiosAdapter;
import com.apps.alvarobanofos.presentview.Adapters.ProvinciasAdapter;
import com.apps.alvarobanofos.presentview.Helpers.MunicipiosFactory;
import com.apps.alvarobanofos.presentview.Helpers.Notifications;
import com.apps.alvarobanofos.presentview.Helpers.ProvinciasFactory;
import com.apps.alvarobanofos.presentview.Models.Municipio;
import com.apps.alvarobanofos.presentview.Models.Provincia;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CompleteDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CompleteDataFragment extends android.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context applicationContext;
    private OnCompleteDataFragmentListener mListener;

    Spinner spinner_provincias;
    Spinner spinner_municipios;
    Button btnCompleteData;
    EditText editTextBirthdate;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CompleteDataFragment() {
        // Required empty public constructor
    }


    public static CompleteDataFragment newInstance() {
        //CompleteDataFragment fragment = new CompleteDataFragment();
        /*Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return new CompleteDataFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_complete_date, container, false);
        applicationContext = getActivity().getApplicationContext();
        spinner_provincias = (Spinner) view.findViewById(R.id.sp_provincias);
        spinner_municipios = (Spinner) view.findViewById(R.id.sp_municipios);
        spinner_municipios.setVisibility(View.INVISIBLE);
        btnCompleteData = (Button) view.findViewById(R.id.btnCompleteData);
        editTextBirthdate = (EditText) view.findViewById(R.id.et_birthdate);

        loadProvincias();
        loadProvinciasEvent();
        btnCompleteDataEvent();

        return view;
    }

    private void loadProvincias() {

        try {
            Provincia[] provincias = ProvinciasFactory.load(getActivity().getApplicationContext());

            ProvinciasAdapter provinciasAdapter = new ProvinciasAdapter(applicationContext, R.layout.custom_spinner, ProvinciasFactory.toArrayList(provincias));
            spinner_provincias.setAdapter(provinciasAdapter);
            ArrayList<String> municipios = new ArrayList<>();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }



    }

    private void loadProvinciasEvent() {
        spinner_provincias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position > 0) {
                    Provincia provincia = (Provincia) spinner_provincias.getSelectedItem();
                    loadMunicipios(provincia.getId());
                }
                else {
                    spinner_municipios.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadMunicipios(int provinciaId) {
        try {
            ArrayList<Municipio> AllMunicipios = new ArrayList<Municipio>(Arrays.asList(MunicipiosFactory.load(getActivity().getApplicationContext())));
            ArrayList<Municipio> municipios = new ArrayList<>();
            for(Municipio municipio : AllMunicipios) {
                if(municipio.getCPRO() == provinciaId) {
                    municipios.add(municipio);
                }
            }
            MunicipiosAdapter municipiosAdapter = new MunicipiosAdapter(applicationContext, R.layout.custom_spinner, municipios);
            spinner_municipios.setAdapter(municipiosAdapter);
            spinner_municipios.setVisibility(View.VISIBLE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void btnCompleteDataEvent() {
        btnCompleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    Object[] params = {
                            editTextBirthdate.getText().toString(),
                            ((Provincia) spinner_provincias.getSelectedItem()).getId(),
                            ((Municipio) spinner_municipios.getSelectedItem()).getCMUN(),
                    };
                    mListener.onCompleteDataInteraction(params);
                } else {
                    Notifications.singleToast(applicationContext, applicationContext.getString(R.string.fail_form));
                }
            }
        });
    }

    private boolean validateForm() {

        Provincia provincia = (Provincia) spinner_provincias.getSelectedItem();
        Municipio municipio = (Municipio) spinner_municipios.getSelectedItem();

        return (isValidDate(editTextBirthdate.getText().toString()) &&
                provincia != null &&
                provincia.getId() > 0 &&
                municipio != null &&
                municipio.getCMUN() > 0);




    }

    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCompleteDataFragmentListener) {
            mListener = (OnCompleteDataFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnCompleteDataFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCompleteDataFragmentListener {
        void onCompleteDataInteraction(Object... params);
    }






}
