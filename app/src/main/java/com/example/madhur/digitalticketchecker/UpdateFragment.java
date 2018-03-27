package com.example.madhur.digitalticketchecker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class UpdateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ImageButton mark_as_vacant_btn;
    ImageButton verify_by_pnr_btn;
    String Train_no;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    public UpdateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Train_no = getArguments().getString("train_no");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_update, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        verify_by_pnr_btn = (ImageButton)getView().findViewById(R.id.verify_seat_pnr_image_btn);
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


        mark_as_vacant_btn = (ImageButton)getView().findViewById(R.id.mark_seat_as_vacant_image_btn);
        mark_as_vacant_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLISTSEATASVACANT = new Intent("android.intent.action.MAKESEATVACANT");

                Bundle tn = new Bundle();
                tn.putString("train_no", Train_no);
                openLISTSEATASVACANT.putExtras(tn);

                startActivity(openLISTSEATASVACANT);
            }
        });
    }


}
