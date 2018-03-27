package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

/**
 * Created by Madhur on 15/03/18.
 */

public class Introduction extends Activity{

    ImageView introductionBackgroundImage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.introduction);

        introductionBackgroundImage = (ImageView) findViewById(R.id.imageView);
        introductionBackgroundImage.setImageResource(R.drawable.train);

        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent openLoginPage = new Intent(Introduction.this,WelcomeActivity.class);
                    startActivity(openLoginPage);
                }
            }
        };
        timer.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

