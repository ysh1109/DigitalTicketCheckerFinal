package com.example.madhur.digitalticketchecker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
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
import java.util.List;

/**
 * Created by root on 25/3/18.
 */

public class List_Unverified_Pnr extends Activity {
    Button get_list_unver;
    Spinner boarding_station_choice;
    TextView error;
    JSONObject get_unverified_pnrs = new JSONObject();
    EditText coach_no_val;
    ArrayList<String> train_stoppage_stations= new ArrayList<String>();
    String coach_name_to_send;
    String boarding_station_name_to_send;
    String Train_no;
    Window w;
    RadioButton for_all;
    RadioButton for_one_coach;

    BaseAdapter2 unverified_tickets_adapter;
    ArrayList<String> pnr_array_u = new ArrayList<String>();
    ArrayList<String> passenger_name_array_u = new ArrayList<String>();
    ArrayList<String> seat_no_array_u = new ArrayList<String>();
    ArrayList<String> coach_no_array_u = new ArrayList<String>();
    ArrayList<String> passenger_age_array_u = new ArrayList<String>();
    ArrayList<String> passenger_gender_array_u = new ArrayList<String>();
    ArrayList<String> verification_status_array_u = new ArrayList<String>();
    ListView parsed_data;
    public void clear_lists(){
        pnr_array_u.clear();
        passenger_age_array_u.clear();
        seat_no_array_u.clear();
        coach_no_array_u.clear();
        passenger_age_array_u.clear();
        passenger_gender_array_u.clear();
        verification_status_array_u.clear();
    }
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.unverified_seats_list);
        get_list_unver = (Button)findViewById(R.id.request_unverified_pnr_btn);
//        boarding_station_choice = (Spinner)findViewById(R.id.boarding_station_u_v_spinner);
//        coach_no_val = (EditText)findViewById(R.id.enter_coach_u_s);
        parsed_data = (ListView)findViewById(R.id.unverified_pnr_list_view);

        Train_no = getIntent().getExtras().getString("train_no");
        for_all = (RadioButton)findViewById(R.id.get_chart_for_all);
        for_one_coach = (RadioButton)findViewById(R.id.get_chart_for_coach);


//        new Get_Train_Info().execute();
        w=getWindow();
        w.setTitle("PNR LIST");
        get_list_unver.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                clear_lists();
                coach_no_val = (EditText)findViewById(R.id.enter_coach_number_pnr);
                error = (TextView)findViewById(R.id.error_text_view);


                for_all.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        coach_no_val.setVisibility(View.GONE);
                    }
                });
                for_one_coach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        coach_no_val.setVisibility(View.VISIBLE);
                        coach_no_val.requestFocus();
                    }
                });
                Log.w("coach_no_val is ",coach_no_val.getText().toString());
//                if(for_all.isChecked()){
                    try {
                        get_unverified_pnrs.put("train_no", Train_no);
                        get_unverified_pnrs.put("verification_status", "P");
                        if(for_one_coach.isChecked()&&coach_no_val.getText().toString()!=null){
                            get_unverified_pnrs.put("coach_no",coach_no_val.getText());
                        }
                        new Unverified_Seats_Background().execute();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
//                }
//                else if(for_one_coach.isChecked()){
//                    if(coach_no_val.getText().toString()!=""){
//                    try {
//                        get_unverified_pnrs.put("train_no", Train_no);
//                        get_unverified_pnrs.put("verification_status", "P");
//                        get_unverified_pnrs.put("coach_no",coach_no_val.getText());
//
//                        new Unverified_Seats_Background().execute();
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                        }
//                    }
//                    else{
//                        error.setText("Please Enter Coach Number first!");
//                    }
//                }

                Log.w("unverified is clicked","clicked");
                parsed_data.setAdapter(null);
            }
        });
    }
//    protected class Get_Train_Info extends AsyncTask<Void, Void, String>{
//        String request_url = "http://159.89.163.178/passengers/";
//        JSONObject train_info_json = new JSONObject();
//        StringBuilder train_info = new StringBuilder();
//        @Override
//        protected String doInBackground(Void... voids){
//            try {
//                URL url = new URL(request_url);
//                HttpURLConnection train_info_connection = (HttpURLConnection)url.openConnection();
//                train_info_connection.setRequestMethod("POST");
//
//                train_info_json.put("train_info", Train_no);
//                OutputStream put_info_req = train_info_connection.getOutputStream();
//
//
//                put_info_req.write(train_info_json.toString().getBytes());
//                put_info_req.flush();
//                train_info_connection.connect();
//
//                InputStream t_i = (InputStream) train_info_connection.getContent();
//                BufferedReader br = new BufferedReader(new InputStreamReader(t_i));
//
//                String r_l="";
//                while((r_l=br.readLine())!=null){
//                    train_info.append(r_l);
//                }
//                br.close();
//                t_i.close();
//                train_info_connection.disconnect();
//
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//            return train_info.toString().trim();
//        }
//        @SuppressLint("LongLogTag")
//        protected void onPostExecute(String result){
//            Log.w("result is : ", result);
//            int count=0;
//            try {
//                JSONArray res = new JSONArray(result);
//                Log.w("res is : ", res.toString());
//                JSONObject JO = res.getJSONObject(0);
//                JSONArray k = new JSONArray();
//                k=JO.getJSONArray("stopages_list");
//                Log.w("json_array is : ",k.toString());
//                int i=0;
//                while(i<k.length()){
//                train_stoppage_stations.add(k.getString(i));i++;}
//                Log.w("train_stoppage_stations is : ", train_stoppage_stations.toString());
//                ArrayAdapter<String> stoppages_data = new ArrayAdapter<String>(List_Unverified_Pnr.this, android.R.layout.simple_spinner_item, train_stoppage_stations);
//                stoppages_data.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                boarding_station_choice.setAdapter(stoppages_data);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    protected class Unverified_Seats_Background extends AsyncTask<Void, Void, String>{
        String request_url="http://159.89.163.178/passengers/";
        StringBuilder pnrs_unverified_response = new StringBuilder();
        @Override
        protected String doInBackground(Void... voids){
            try {
                URL url = new URL(request_url);
                HttpURLConnection pnr_info_connection = (HttpURLConnection)url.openConnection();
                pnr_info_connection.setRequestMethod("POST");

                //get_unverified_pnrs.put("boarding_station", boarding_station_name_to_send);
                //get_unverified_pnrs.put("coach_no", coach_name_to_send);

                OutputStream put_info_req = pnr_info_connection.getOutputStream();


                put_info_req.write(get_unverified_pnrs.toString().getBytes());
                put_info_req.flush();
                pnr_info_connection.connect();

                InputStream p_i = (InputStream) pnr_info_connection.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(p_i));

                String r_l="";
                while((r_l=br.readLine())!=null){
                    pnrs_unverified_response.append(r_l);
                }
                br.close();
                p_i.close();
                pnr_info_connection.disconnect();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return pnrs_unverified_response.toString().trim();
        }
        protected void onPostExecute(String result){
            Log.w("MainResult is : ", result);
            int count=0;
            try {
                JSONArray res = new JSONArray(result);
                Log.w("res is : ", res.toString());
                while(count<res.length()) {
                    JSONObject JO = res.getJSONObject(count);
                    pnr_array_u.add(JO.getString("pnr"));
                    passenger_name_array_u.add(JO.getString("passenger_name"));
                    coach_no_array_u.add(JO.getString("coach_no"));
                    seat_no_array_u.add(JO.getString("seat_no"));
                    passenger_age_array_u.add(JO.getString("passenger_age"));
                    passenger_gender_array_u.add(JO.getString("gender"));
                    verification_status_array_u.add(JO.getString("verification_status"));
                    count++;
                }

                unverified_tickets_adapter = new BaseAdapter2(List_Unverified_Pnr.this, pnr_array_u, passenger_name_array_u, seat_no_array_u, coach_no_array_u, verification_status_array_u, passenger_age_array_u, passenger_gender_array_u);
                parsed_data.setAdapter(null);
                parsed_data.setAdapter(unverified_tickets_adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
