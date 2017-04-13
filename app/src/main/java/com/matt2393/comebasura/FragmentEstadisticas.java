package com.matt2393.comebasura;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentEstadisticas extends Fragment implements Aceptar_resultado{
    TextView botes,botellas,puntos;
    int cantBotes,cantBotellas,cantPuntos;
    TextInputEditText password;
    final String pass="hola1999mundo";
    SharedPreferences preferences;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_estadisticas,container,false);

        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        cantBotes=preferences.getInt("",0);

        cantPuntos=preferences.getInt("PUNTOS",0);
        cantBotes=preferences.getInt("BOTESREC",0);
        cantBotellas=preferences.getInt("BOTELLASREC",0);

        botes=(TextView)view.findViewById(R.id.boteRecTotal);
        botellas=(TextView)view.findViewById(R.id.botellasRecTotal);
        puntos=(TextView)view.findViewById(R.id.totalPuntos);
        password=(TextInputEditText)view.findViewById(R.id.passwordAdmin);
        Button acep=(Button)view.findViewById(R.id.aceptarPassword);

        botes.setText(String.valueOf(cantBotes));
        botellas.setText(String.valueOf(cantBotellas));
        puntos.setText(String.valueOf(cantPuntos));

        acep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equalsIgnoreCase(pass)) {
                    password.setText("");
                    Dialog_descontar diag = new Dialog_descontar();
                    diag.show(getActivity().getSupportFragmentManager(), "descontador");

                }
                else{
                    Toast.makeText(getActivity(),"Incorrecto, vuelva a ingresar",Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void aceptado() {

    }

    @Override
    public void descontar() {
        cantPuntos=preferences.getInt("PUNTOS",0);
        cantBotes=preferences.getInt("BOTESREC",0);
        cantBotellas=preferences.getInt("BOTELLASREC",0);

        botes.setText(String.valueOf(cantBotes));
        botellas.setText(String.valueOf(cantBotellas));
        puntos.setText(String.valueOf(cantPuntos));

    }
}
