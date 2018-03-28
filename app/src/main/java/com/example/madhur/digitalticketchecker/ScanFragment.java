package com.example.madhur.digitalticketchecker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class ScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button qrBtn;
    Button unverified_chart;
    Button verify_by_pnr_btn;
    String Train_no;
    // TODO: Rename and change types of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Train_no = getArguments().getString("train_no");
        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle onSavedInstance){
                qrBtn = (Button)getView().findViewById(R.id.scanner);

        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scn = new Intent("android.intent.action.QRCODESCANNER");
                Bundle tn = new Bundle();
                tn.putString("train_no", Train_no);
                scn.putExtras(tn)
                startActivity(scn);
            }
        });

        unverified_chart = (Button)getView().findViewById(R.id.bGetPNRList);
        unverified_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chrt = new Intent("android.intent.action.UNVERIFIEDPNR");
                Bundle tn = new Bundle();
                tn.putString("train_no", Train_no);
                chrt.putExtras(tn);
                startActivity(chrt);
            }
        });

        verify_by_pnr_btn = (Button)getView().findViewById(R.id.manual_enter_pnr_no);
        verify_by_pnr_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent manualUpdate = new Intent("android.intent.action.MANUALYENTERPNR");

                Bundle tn = new Bundle();
                tn.putString("train_no", Train_no);
                manualUpdate.putExtras(tn);

                startActivity(manualUpdate);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event


}
