package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Madhur on 19/03/18.
 */

public class Charts extends Activity implements View.OnClickListener{

    ImageView image,image1, image2,image3,image4;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.charts);

        /*
        image = findViewById(R.id.imageView);
        image.setImageResource(R.drawable.ic_search_black_24dp);
        image1 = findViewById(R.id.imageview1);
        image1.setImageResource(R.drawable.ic_av_timer_black_24dp);
        image2 = findViewById(R.id.imageView2);
        image2.setImageResource(R.drawable.ic_check_circle_black_24dp);
        image3 = findViewById(R.id.imageView3);
        image3.setImageResource(R.drawable.ic_event_seat_black_24dp);
        image4 = findViewById(R.id.imageview4);
        image4.setImageResource(R.drawable.ic_accessibility_black_24dp);
        */

        Button bChartRAC = (Button)findViewById(R.id.bChartRAC);
        bChartRAC.setOnClickListener(this);
        Button bChartWaitlist = (Button)findViewById(R.id.bChartWaitlist);
        bChartWaitlist.setOnClickListener(this);
        Button bChartChecked = (Button)findViewById(R.id.bChartChecked);
        bChartChecked.setOnClickListener(this);
        Button bChartVacant = (Button)findViewById(R.id.bChartsVacant);
        bChartVacant.setOnClickListener(this);
        Button bMarkAsVacant = (Button)findViewById(R.id.bMarkAsVacant);
        bMarkAsVacant.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bChartRAC:
                Intent openCHARTSRAC = new Intent("android.intent.action.DISPLAYLISTV");
                startActivity(openCHARTSRAC);
                break;
            case R.id.bChartWaitlist:
                Intent openCHARTSWAITLIST = new Intent("android.intent.action.CHARTSWAITLIST");
                startActivity(openCHARTSWAITLIST);
                break;
            case R.id.bChartChecked:
                Intent openCHARTSCHECKED = new Intent("android.intent.action.CHARTSCHECKED");
                startActivity(openCHARTSCHECKED);
                break;
            case R.id.bChartsVacant:
                Intent openCHARTSVACANT = new Intent("android.intent.action.CHARTSVACANT");
                startActivity(openCHARTSVACANT);
                break;
            case R.id.bMarkAsVacant:
                Intent openMARKASVACANT = new Intent("android.intent.action.MARKASVACANT");
                startActivity(openMARKASVACANT);
                break;
        }

    }
}
