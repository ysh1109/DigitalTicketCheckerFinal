package com.example.madhur.digitalticketchecker;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;


public class ChartsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ImageButton btnRAC_chart;
    ImageButton mark_as_vacant_btn;
    ImageButton btnVacant_chart;
    ImageButton btnVerified_chart;
    Window w;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    String Train_no;

    public ChartsFragment() {
        // Required empty public constructor
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
        Train_no = getArguments().getString("train_no");

        return inflater.inflate(R.layout.fragment_charts, container, false);

    }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){


        btnRAC_chart = (ImageButton)getView().findViewById(R.id.rac_image_btn);
        btnRAC_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openCHARTSRAC = new Intent("android.intent.action.DISPLAYLISTV");
                Bundle tn = new Bundle();
                tn.putString("train_no", Train_no);
                openCHARTSRAC.putExtras(tn);
                startActivity(openCHARTSRAC);
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

        btnVacant_chart = (ImageButton)getView().findViewById(R.id.vacant_seats_image_btn);
        btnVacant_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openLISTSEATASVACANT = new Intent("android.intent.action.LISTVACANTSEATS");
                Bundle x = new Bundle();
                x.putString("train_no", Train_no);
                openLISTSEATASVACANT.putExtras(x);
                startActivity(openLISTSEATASVACANT);
            }
        });

        btnVerified_chart = (ImageButton)getView().findViewById(R.id.verified_tickets_image_btn);
        btnVerified_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent checked = new Intent("android.intent.action.CHARTSCHECKED");
                Bundle b = new Bundle();
                b.putString("train_no", Train_no);
                checked.putExtras(b);
                startActivity(checked);
            }
        });
    }


}
