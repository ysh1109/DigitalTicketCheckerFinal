package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by Madhur on 16/03/18.
 */

public class LoginPage extends Activity {


    ImageView introductionBackgroundImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.loginpage);


        introductionBackgroundImage = (ImageView) findViewById(R.id.imageView3);
        introductionBackgroundImage.setImageResource(R.drawable.train);

        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final EditText etLoginName = (EditText) findViewById(R.id.etLoginName);
        final EditText etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                etLoginName.getText().toString();
                etLoginPassword.getText().toString();

                if ((etLoginName.getText().toString().equals(""))&&(etLoginPassword.getText().toString().equals(""))) {

                    Intent openBoarding = new Intent("android.intent.action.BOARDING");
                    startActivity(openBoarding);
                    finish();
                }else{
                    bLogin.setText("Wrong Password");
                }
            }
        });
    }
}
