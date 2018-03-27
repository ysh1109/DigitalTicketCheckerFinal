package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.internal.NavigationMenu;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.support.v7.app.AlertDialog.*;

/**
 * Created by Madhur on 16/03/18.
 */

public class QRCodeScanner extends Activity {

    Button bCharts;
    Button bscan;
    Button bGetPNRList;
    TextView tvBoardingTrainNumberShow;
    TextView tvBoardingStationShow;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcodescanner);
        final View a = getCurrentFocus();

        bCharts = (Button) findViewById(R.id.bCharts);
        bGetPNRList = (Button) findViewById(R.id.bGetPNRList);
        tvBoardingTrainNumberShow = (TextView)findViewById(R.id.tvBoardingTrainNumberShow);
        tvBoardingStationShow = (TextView)findViewById(R.id.tvBoardingStationShow);

        Bundle gotBoardingTrainNumberBasket = getIntent().getExtras();
        String boardingTrainNumber = gotBoardingTrainNumberBasket.getString("etBoardingTrainNumberBasketKey");
        tvBoardingTrainNumberShow.setText(boardingTrainNumber);

        Bundle gotBoardingStationBasket = getIntent().getExtras();
        String boardingStation = gotBoardingStationBasket.getString("etBoardingStationBasketKey");
        tvBoardingStationShow.setText(boardingStation);

        bscan = (Button)findViewById(R.id.scanner);
        bscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanQR(a);
            }
        });


    }

    public void scanBar(View v) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(QRCodeScanner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(QRCodeScanner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        Builder downloadDialog = new Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }

            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }
    //on ActivityResult method

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String pnr = "";
                for (int i = 0; i < 10; i++) {
                    if (contents.charAt(i) != ' ') {
                        pnr += contents.charAt(i);
                    } else {
                        break;
                    }

                }
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + pnr + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();

                Bundle pnrBasket = new Bundle();
                pnrBasket.putString("pnrBasketKey", pnr);

                Intent openREST2 = new Intent(QRCodeScanner.this, REST.class);
                openREST2.putExtras(pnrBasket);
                startActivity(openREST2);
            }
        }


        bCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QRCodeScanner.this, Charts.class);
                startActivity(intent);
            }
        });

        /*bGetPNRList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openPNRLIST = new Intent(QRCodeScanner.this,PNRList.class);
                startActivity(openPNRLIST);
            }
        });
        */
    }

}


