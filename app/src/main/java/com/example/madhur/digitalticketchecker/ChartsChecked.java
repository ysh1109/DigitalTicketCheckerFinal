package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Madhur on 19/03/18.
 */

public class ChartsChecked extends Activity{
    BaseAdapter2 verified_seats_adapter;
    ArrayList<String> pnr_array = new ArrayList<String>();
    ArrayList<String> passenger_name_array = new ArrayList<String>();
    ArrayList<String> seat_no_array = new ArrayList<String>();
    ArrayList<String> coach_no_array = new ArrayList<String>();
    ArrayList<String> passenger_age_array = new ArrayList<String>();
    ArrayList<String> passenger_gender_array = new ArrayList<String>();
    ArrayList<String> verification_status_array = new ArrayList<String>();
    ListView parsed_json;
    String Train_no;
    TextView title;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.verified_seats_chart);

        title=(TextView)findViewById(R.id.ChartChecked_title);
        title.setText("Checked Seats");

        Bundle bundle = getIntent().getExtras();
        Train_no = bundle.getString("train_no");
        Log.w("tra_no is : ",Train_no);
        new get_verified_seats_background().execute();
    }

    protected class get_verified_seats_background extends AsyncTask<Void, Void, String>{

        StringBuilder verified_seats_response = new StringBuilder();
        String req_url = "http://159.89.163.178/passengers/";
        JSONObject request_for_verified = new JSONObject();

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL ur = new URL(req_url);
                HttpURLConnection new_con = (HttpURLConnection)ur.openConnection();
                new_con.setRequestMethod("POST");
                request_for_verified.put("verification_status", "V");
                request_for_verified.put("train_no", Train_no);

                OutputStream send_r = new_con.getOutputStream();
                send_r.write(request_for_verified.toString().getBytes());
                send_r.flush();
                new_con.connect();

                InputStream response = (InputStream)new_con.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(response));
                String r;
                while((r=br.readLine())!=null){
                    verified_seats_response.append(r);
                }
                br.close();
                response.close();
                new_con.disconnect();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.w("resp_is : ",verified_seats_response.toString());
            return verified_seats_response.toString().trim();
        }

        @Override
        protected void onPostExecute(String result){
            try {
                JSONArray json = new JSONArray(result);

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
                parsed_json = (ListView)findViewById(R.id.verified_seats_list_view);
                verified_seats_adapter = new BaseAdapter2(ChartsChecked.this, pnr_array, passenger_name_array, seat_no_array, coach_no_array, verification_status_array, passenger_age_array, passenger_gender_array);
                parsed_json.setAdapter(verified_seats_adapter
                );
            }
            catch (JSONException e){
                e.printStackTrace();
            }

        }
    }
}
