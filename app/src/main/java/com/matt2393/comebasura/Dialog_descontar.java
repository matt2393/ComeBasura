package com.matt2393.comebasura;


import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Dialog_descontar extends DialogFragment{

    SharedPreferences preferences;
    TextInputEditText puntos;
    int puntosTotales,puntosDescontar;
    Aceptar_resultado acp;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());

        View view=getActivity().getLayoutInflater().inflate(R.layout.fragment_descontar_puntos,null);

        puntosTotales=preferences.getInt("PUNTOS",0);


        puntos=(TextInputEditText)view.findViewById(R.id.puntosDescontar);
        Button desc=(Button)view.findViewById(R.id.descontar);

        desc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!puntos.getText().toString().equalsIgnoreCase("")) {
                    puntosDescontar = Integer.parseInt(puntos.getText().toString());

                    if (puntosTotales >= puntosDescontar) {
                        SharedPreferences.Editor editar=preferences.edit();
                        puntosTotales-=puntosDescontar;
                        editar.putInt("PUNTOS",puntosTotales);
                        editar.apply();
                        editar.commit();

                        acp=(Aceptar_resultado) getActivity().getSupportFragmentManager().getFragments().get(1);
                        acp.descontar();
                        dismiss();
                    } else {
                        Toast.makeText(getActivity(), "No tienes los puntos necesarios", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    Toast.makeText(getActivity(),"Ingrese los puntos a descontar",Toast.LENGTH_LONG).show();
                }
            }
        });

        AlertDialog.Builder al=new AlertDialog.Builder(getActivity());
        al.setView(view);


        return al.create();
    }
}
