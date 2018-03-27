package com.example.madhur.digitalticketchecker;

/**
 * Created by Madhur on 16/03/18.
 */

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class REST extends AppCompatActivity {

    EditText etEx;
    String gotPnrBread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest);

        Bundle gotPnrBasket = getIntent().getExtras();
        gotPnrBread = gotPnrBasket.getString("pnrBasketKey");


        try {
            new ParseTask().execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class ParseTask extends AsyncTask<Void, Void, String> {


        StringBuilder response_final= new StringBuilder();

        private ParseTask() throws MalformedURLException {
        }

        @Override
        protected String doInBackground(Void... params) {
            try {

                String site_url_json = "http://159.89.163.178/passengers/";
                URL url = new URL(site_url_json);
                JSONObject jo = new JSONObject();
                jo.put("update_values",gotPnrBread);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                OutputStream refVarOut = urlConnection.getOutputStream();
                refVarOut.write(jo.toString().getBytes());
                refVarOut.flush();
                urlConnection.connect();


                InputStream ip = (InputStream) urlConnection.getContent();
                BufferedReader br1 = new BufferedReader(new InputStreamReader(ip));

                String responseSingle;
                while ((responseSingle = br1.readLine())!=null)
                {
                    response_final.append(responseSingle);
                }

                //tvEx.setText(urlConnection.getResponseMessage().toString());
                StringBuffer buffer = new StringBuffer();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return response_final.toString().trim();
        }



        protected void onPostExecute(String strJson)
        {
            super.onPostExecute(strJson);
            String result_json_text = "SUCCESSFULLY VERIFIED!";
            Log.w("FOR_LOG", result_json_text);
            TextView textView = (TextView) findViewById(R.id.showtext);
            textView.setText(result_json_text);
        }
    }
}