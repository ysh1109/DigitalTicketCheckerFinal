package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by root on 24/3/18.
 */

public class List_Seat_As_Vacant extends Activity {
    EditText Coach_Number;
    EditText Seat_Number;
    Button make_vacant;
    StringBuilder stringBuilder = new StringBuilder();
    String[] values_list = new String[2];
    String Train_no;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_seat_as_vacant);
        make_vacant = (Button)findViewById(R.id.confirm_vacant_btn);
        Coach_Number = (EditText)findViewById(R.id.vacant_list_coach_no);
        Seat_Number = (EditText)findViewById(R.id.seat_no);
        Train_no = getIntent().getExtras().getString("train_no");
        make_vacant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    values_list[0] = Coach_Number.getText().toString();
                    values_list[1] = Seat_Number.getText().toString();
                    Log.w("MARK SEAT : ", "IT HAS BEEN CLICKED");
                    Log.w("values list is : ",values_list[0].toString());
                    Log.w("Values[1] is : ",values_list[1].toString());
                    new BackgroundTask_s_a_v().execute();

            }
        });

    }
    class BackgroundTask_s_a_v extends AsyncTask< Void, Void, String >{
        String request_url;
        String JSON_STRING;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            request_url = "http://159.89.163.178/passengers/";
        }
        @Override
        protected String doInBackground(Void... voids){
        try {
            URL url = new URL(request_url);
            JSONObject post_vac = new JSONObject();
            String to_put = values_list[0]+","+values_list[1]+","+Train_no;
            Log.w("to_put is : ", to_put);
            post_vac.put("mark_as_vacant", to_put);
            Log.w("post_vac is : ", post_vac.toString());
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            OutputStream write_request = httpURLConnection.getOutputStream();
            write_request.write(post_vac.toString().getBytes());
            write_request.flush();
            httpURLConnection.connect();

            InputStream RAC = (InputStream) httpURLConnection.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(RAC));

            while((JSON_STRING = bufferedReader.readLine())!= null)
            {
                stringBuilder.append(JSON_STRING + "\n");
            }
            Log.w("Response is : ",stringBuilder.toString());
            bufferedReader.close();
            RAC.close();
            httpURLConnection.disconnect();
            return stringBuilder.toString().trim();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
            return null;}
    }
}


