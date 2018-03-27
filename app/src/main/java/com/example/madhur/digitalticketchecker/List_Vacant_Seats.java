package com.example.madhur.digitalticketchecker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.inputmethodservice.InputMethodService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.madhur.digitalticketchecker.R;
import com.example.madhur.digitalticketchecker.Vacant_Seats_Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by root on 24/3/18.
 */

public class List_Vacant_Seats extends Activity {
    Vacant_Seats_Adapter v_s_adapter;
    String coach="";
    String JSON_STRING;
    ListView vacant_coach_list_view;
    JSONObject request_vacant_coaches;
    String requestURL_v_s="";
    StringBuilder vacant_coach_response=new StringBuilder();
    String Train_no;

    ArrayList coach_names = new ArrayList<String>();
    ArrayList seat_numbers = new ArrayList<String>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vacant_seats_list);

        final RadioGroup seat_category = (RadioGroup) findViewById(R.id.vacant_seats_in);
//        final RadioButton coach_by_name = (RadioButton)findViewById(R.id.specific_coach);
//        final RadioButton all_the_coaches = (RadioButton)findViewById(R.id.all_coaches);

        final Button vacant_seat_finder = (Button)findViewById(R.id.list_vacant_seats_btn);
//        final EditText enter_coach = (EditText)findViewById(R.id.vacant_list_coach_name);

        Train_no = getIntent().getExtras().getString("train_no");

         vacant_coach_list_view= (ListView)findViewById(R.id.list_view_vacant_coach);
//        coach_by_name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enter_coach.setVisibility(View.VISIBLE);
//            }
//        });
//        all_the_coaches.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                try {
//
//                    //emptying old requests
//                    Iterator keys = request_vacant_coaches.keys();
//                    while(keys.hasNext())
//                        request_vacant_coaches.remove(keys.next().toString());
//
//                    enter_coach.setVisibility(View.GONE);
//                    request_vacant_coaches.put("train_no",Train_no);
//                    request_vacant_coaches.put("verification_status", "U");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        enter_coach.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                enter_coach.setText("");
//            }
//        });
        vacant_seat_finder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                coach_names.clear();
                seat_numbers.clear();

//                if(coach_by_name.isChecked()){
//                    coach = enter_coach.getText().toString();
                    request_vacant_coaches = new JSONObject();
                    try {
//                        request_vacant_coaches.put("coach_no",coach);
                        request_vacant_coaches.put("train_no",Train_no);
                        request_vacant_coaches.put("verification_status","U");
                    } catch (JSONException e) {
                        e.printStackTrace();
//                    }
                }
//                InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                new BackgroundTask_v_c().execute();
            }
        });

    }
    public class BackgroundTask_v_c extends AsyncTask< Void, Void, String>{

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            requestURL_v_s="http://159.89.163.178/passengers/";
        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(requestURL_v_s);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                OutputStream write_request = httpURLConnection.getOutputStream();
                write_request.write(request_vacant_coaches.toString().getBytes());
                write_request.flush();
                httpURLConnection.connect();

                InputStream RAC = (InputStream) httpURLConnection.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(RAC));
                Log.w("response is : ", bufferedReader.toString());

                while((JSON_STRING = bufferedReader.readLine())!= null)
                {
                    vacant_coach_response.append(JSON_STRING + "\n");
                }

                bufferedReader.close();
                RAC.close();
                httpURLConnection.disconnect();
                return vacant_coach_response.toString().trim();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            int count=0;
            try {
                JSONArray res = new JSONArray(result);
                Log.w("res is : ", res.toString());
                while(count<res.length()){
                    JSONObject JO = res.getJSONObject(count);
                    coach_names.add(JO.getString("coach_no").toString());
                    seat_numbers.add(JO.getString("seat_no").toString());
                    count++;
                }
                Log.w("coach_names : ", coach_names.toString());
                Log.w("seat_numbers : ", seat_numbers.toString());
                v_s_adapter = new Vacant_Seats_Adapter(List_Vacant_Seats.this,coach_names,seat_numbers);
                vacant_coach_list_view.setAdapter(null);
                vacant_coach_list_view.setAdapter(v_s_adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
