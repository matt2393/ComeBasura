package com.matt2393.comebasura;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class Dialog_aceptar extends DialogFragment {

    private final String boteAl="Bote de aluminio";
    private final String botella="Botella de plastico";

    private int puntosGuardados,puntosGanados,botesRec,botellasRec;
    private TextView ac,error,puntGan,puntosAdd,objeto,tit_puntos;


    Aceptar_resultado acpres,acpdesc;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {



        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());


        puntosGuardados=preferences.getInt("PUNTOS",0);
        botesRec=preferences.getInt("BOTESREC",0);
        botellasRec=preferences.getInt("BOTELLASREC",0);

        String dato=getArguments().getString("tipo","otro");

        if(dato.equalsIgnoreCase(boteAl) || dato.equalsIgnoreCase(botella)) {
            if(dato.equalsIgnoreCase(boteAl)) {
                puntosGanados = 20;
                botesRec++;
            }
            else {
                puntosGanados = 5;
                botellasRec++;
            }
        }
        else
            puntosGanados = 0;

        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.dlalog_aceptar_codigo,null);
        Button aceptar=(Button)view.findViewById(R.id.aceptar_respuesta);

        ac=(TextView)view.findViewById(R.id.titulo_aceptar);
        error=(TextView)view.findViewById(R.id.error_codigo);
        puntGan=(TextView)view.findViewById(R.id.puntosGanados);
        objeto=(TextView)view.findViewById(R.id.objetoReciclado);
        puntosAdd=(TextView)view.findViewById(R.id.puntos_add);
        tit_puntos=(TextView)view.findViewById(R.id.tit_puntos);

        if(puntosGanados!=0){


            ac.setText("Aceptado");
            error.setVisibility(View.GONE);
            puntGan.setVisibility(View.VISIBLE);
            objeto.setVisibility(View.VISIBLE);
            puntosAdd.setVisibility(View.VISIBLE);
            tit_puntos.setVisibility(View.VISIBLE);
            aceptar.setText("OK");
            puntosGuardados+=puntosGanados;
            SharedPreferences.Editor editar=preferences.edit();
            editar.putInt("PUNTOS",puntosGuardados);
            editar.putInt("BOTESREC",botesRec);
            editar.putInt("BOTELLASREC",botellasRec);
            editar.apply();
            editar.commit();

            puntGan.setText(String.valueOf(puntosGanados));
            objeto.setText(dato);
            puntosAdd.setText(String.valueOf(puntosGuardados));
        }
        else{
            ac.setText("Error ! ! !");
            error.setText("El codigo QR escaneado no es el Correcto\nIntente denuevo");
            error.setVisibility(View.VISIBLE);
            puntGan.setVisibility(View.GONE);
            objeto.setVisibility(View.GONE);
            puntosAdd.setVisibility(View.GONE);
            tit_puntos.setVisibility(View.GONE);
            aceptar.setText("Reintentar");
        }



        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Log.i("CANTIDAD de frag",getActivity().getSupportFragmentManager().getFragments().size()+"");
                //try{
                acpres=(Aceptar_resultado)getActivity().getSupportFragmentManager().getFragments().get(0);
                acpdesc=(Aceptar_resultado)getActivity().getSupportFragmentManager().getFragments().get(1);
                acpres.aceptado();
                acpdesc.descontar();
/*

                }catch (ClassCastException CCex){
                    Log.i("ERROR","mal");
                }*/
            }
        });

        alert.setView(view);
        return alert.create();
    }
}
