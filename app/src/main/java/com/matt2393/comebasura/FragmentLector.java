package com.matt2393.comebasura;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;


public class FragmentLector extends Fragment implements Aceptar_resultado,Control_Pages {

    private final String boteAl = "Bote de aluminio";
    private final String botella = "Botella de plastico";

    SurfaceView lector;
    Bundle datos;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;

    SurfaceHolder.Callback call;
    boolean sw=true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lector, container, false);

        datos = new Bundle();
        lector = (SurfaceView) view.findViewById(R.id.lector_qr);

        barcodeDetector = new BarcodeDetector.Builder(getContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(getContext(), barcodeDetector)
                .setRequestedPreviewSize(300, 300)
                .setAutoFocusEnabled(true)
                .build();

        Log.e("ERRORBARCODE",barcodeDetector.isOperational()+"");
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                SparseArray<Barcode> srf = detections.getDetectedItems();
                if (srf.size() > 0 && sw) {
                    datos.putString("tipo", srf.valueAt(0).displayValue);
                    Dialog_aceptar diag = new Dialog_aceptar();
                    diag.setArguments(datos);
                    diag.setCancelable(false);
                    diag.show(getActivity().getSupportFragmentManager(), "Mensaje");

                  /*  if(boteAl.equalsIgnoreCase(srf.valueAt(0).displayValue)){
                        datos.putString("tipo","Aluminio");
                        Dialog_aceptar diag=new Dialog_aceptar();
                        diag.setArguments(datos);
                        diag.show(getActivity().getSupportFragmentManager(),"Mensaje");
                        Log.i("DATO","Aluminio");
                    }
                    else if(botella.equalsIgnoreCase(srf.valueAt(0).displayValue)){
                        datos.putString("tipo","Plastico");
                        Dialog_aceptar diag=new Dialog_aceptar();
                        diag.setArguments(datos);
                        diag.show(getActivity().getSupportFragmentManager(),"Mensaje");
                        Log.i("DATO","Plastico");
                    }*/
                    sw=false;
                }
                //Log.i("BarCode","capturando datos");
            }

        });

        call=new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(lector.getHolder());
                    } catch (IOException ex) {
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.CAMERA},1);
                    Toast.makeText(getContext(), "Error de permisos", Toast.LENGTH_SHORT).show();
                }

                Log.i("Camara", "se inicio");
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                Log.i("Camara", "cambio");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();

                Log.i("Camara", "se cerro");
            }
        };
        lector.getHolder().addCallback(call);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        sw=true;
    }

    @Override
    public void onPause() {
        super.onPause();
        sw=false;
    }

    @Override
    public void aceptado() {
        sw=true;

    }

    @Override
    public void descontar() {

    }

    @Override
    public void pause() {
        sw=false;
        Log.i("ControlPages","PAUSE");
    }

    @Override
    public void resume() {
        sw=true;
        Log.i("ControlPages","RESUME");
    }

}
