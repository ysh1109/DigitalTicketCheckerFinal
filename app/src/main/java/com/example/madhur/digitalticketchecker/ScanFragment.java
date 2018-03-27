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

public class ScanFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    Button qrBtn;
    // TODO: Rename and change types of parameters

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_scan, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle onSavedInstance){
                qrBtn = (Button)getView().findViewById(R.id.scanner);

        qrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scn = new Intent("android.intent.action.QRCODESCANNER");
                startActivity(scn);
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event


}
