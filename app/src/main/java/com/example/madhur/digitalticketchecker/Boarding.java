package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Madhur on 16/03/18.
 */

public class Boarding extends Activity {

    EditText etBoardingTrainNumber;
    EditText etBoardingStation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.boardingpage);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                //WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button bLogin = (Button) findViewById(R.id.bBoardingSubmit);
        etBoardingTrainNumber = (EditText) findViewById(R.id.etBoardingTrainNumber);
        etBoardingStation = (EditText) findViewById(R.id.etBoardingStation);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle etBoardingTrainNumberBasket = new Bundle();
                etBoardingTrainNumberBasket.putString("etBoardingTrainNumberBasketKey",etBoardingTrainNumber.getText().toString());

                Bundle etBoardingStationBasket = new Bundle();
                etBoardingStationBasket.putString("etBoardingStationBasketKey",etBoardingStation.getText().toString());

                Intent openQRCodeScanner = new Intent(Boarding.this,QRCodeScanner.class);
                openQRCodeScanner.putExtras(etBoardingTrainNumberBasket);
                openQRCodeScanner.putExtras(etBoardingStationBasket);
                startActivity(openQRCodeScanner);
                finish();
            }
        });
    }
}
