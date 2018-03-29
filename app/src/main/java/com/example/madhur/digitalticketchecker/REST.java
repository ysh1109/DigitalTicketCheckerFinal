package com.example.madhur.digitalticketchecker;

/**
 * Created by Madhur on 16/03/18.
 */

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class REST extends AppCompatActivity {

    EditText etEx;
    String gotPnrBread;
    TextView the_pnr;
    BaseAdapter2 seats_from_pnr;
    ArrayList<String> pnr_array = new ArrayList<String>();
    ArrayList<String> passenger_name_array = new ArrayList<String>();
    ArrayList<String> seat_no_array = new ArrayList<String>();
    ArrayList<String> coach_no_array = new ArrayList<String>();
    ArrayList<String> passenger_age_array = new ArrayList<String>();
    ArrayList<String> passenger_gender_array = new ArrayList<String>();
    ArrayList<String> verification_status_array = new ArrayList<String>();
    ListView parsed_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rest);

        Bundle gotPnrBasket = getIntent().getExtras();
        gotPnrBread = gotPnrBasket.getString("pnrBasketKey");
        the_pnr = (TextView)findViewById(R.id.the_pnr_no_display);
        the_pnr.setText(gotPnrBread);

        new Get_pass_from_pnr().execute();
    }

    protected class Get_pass_from_pnr extends AsyncTask<Void, Void, String> {


        StringBuilder response_final= new StringBuilder();

        @Override
        protected String doInBackground(Void... params) {
            try {

                String site_url_json = "http://159.89.163.178/passengers/";
                URL url = new URL(site_url_json);
                JSONObject jo = new JSONObject();
                jo.put("pnr",gotPnrBread);
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
            Log.w("QORRES ",strJson.toString());
            JSONArray json = null;
            try {
                json = new JSONArray(strJson);
                int count = 0;
                while (count < json.length()) {
                    JSONObject JO = json.getJSONObject(count);
                    pnr_array.add(JO.getString("pnr"));
                    passenger_name_array.add(JO.getString("passenger_name"));
                    seat_no_array.add(JO.getString("seat_no"));
                    coach_no_array.add(JO.getString("coach_no"));
                    passenger_age_array.add(JO.getString("passenger_age"));
                    passenger_gender_array.add(JO.getString("gender"));
                    verification_status_array.add(JO.getString("verification_status"));
                    count++;
                }
                parsed_json = (ListView)findViewById(R.id.pnr_names);
                seats_from_pnr = new BaseAdapter2(REST.this, pnr_array, passenger_name_array, seat_no_array, coach_no_array, verification_status_array, passenger_age_array, passenger_gender_array);
                parsed_json.setAdapter(seats_from_pnr);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Button a = (Button)findViewById(R.id.confirm_btn_pnr);
            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new send_pnr_to_server().execute();
                }
            });

        }
    }
    protected class send_pnr_to_server extends AsyncTask<Void, Void, String>{
        String dest_url = "http://159.89.163.178/passengers/";
        JSONObject sending_request = new JSONObject();
        StringBuilder response=new StringBuilder();
        String rr;
        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL dest = new URL(dest_url);

                sending_request.put("update_values",gotPnrBread);

                HttpURLConnection con = (HttpURLConnection)dest.openConnection();
                con.setRequestMethod("POST");

                OutputStream out = con.getOutputStream();
                out.write(sending_request.toString().getBytes());
                out.flush();
                con.connect();

                InputStream resp=(InputStream)con.getContent();
                BufferedReader breader = new BufferedReader(new InputStreamReader(resp));
                while((rr=breader.readLine())!=null){
                    response.append(rr);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response.toString().trim();
        }

        @Override
        protected void onPostExecute(String result){
            TextView up = (TextView)findViewById(R.id.successful_scan_pnr);
            TextView error_verify = (TextView)findViewById(R.id.error_in_pnr);
            if(result.equalsIgnoreCase("\"Updated\"")||result.equalsIgnoreCase("Updated"))
            {
                up.setText("VERIFIED PNR SUCCESSFULLY");
            }
            else{
                error_verify.setText("INVALID PNR NO.");
            }

        }
    }
}