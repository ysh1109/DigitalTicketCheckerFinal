package com.example.madhur.digitalticketchecker;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
 * Created by root on 24/3/18.
 */

public class ChartRAC extends Activity{


    BaseAdapter2 adapter;
    ArrayList<String> pnr_array = new ArrayList<String>();
    ArrayList<String> passenger_name_array = new ArrayList<String>();
    ArrayList<String> seat_no_array = new ArrayList<String>();
    ArrayList<String> coach_no_array = new ArrayList<String>();
    ArrayList<String> passenger_age_array = new ArrayList<String>();
    ArrayList<String> passenger_gender_array = new ArrayList<String>();
    ArrayList<String> verification_status_array = new ArrayList<String>();
    ListView parsed_json;
    String json_url;

    String Train_no;
    TextView title;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_rac);
        title = (TextView)findViewById(R.id.RAC_title);
        title.setText("RAC Chart");
        Train_no = getIntent().getExtras().getString("train_no");

        new BackgroundTask().execute();
    }
    StringBuilder stringBuilder = new StringBuilder();
    class BackgroundTask extends AsyncTask<Void, Void, String > {
        String JSON_STRING;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            json_url = "http://159.89.163.178/passengers/";
        }
        @Override
        protected String  doInBackground(Void... voids) {
            try {
                URL url = new URL(json_url);
                JSONObject get_rac = new JSONObject();
                get_rac.put("ticket_status", "RAC 01");
                get_rac.put("train_no", Train_no);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                OutputStream write_request = httpURLConnection.getOutputStream();
                write_request.write(get_rac.toString().getBytes());
                write_request.flush();
                httpURLConnection.connect();

                InputStream RAC = (InputStream) httpURLConnection.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(RAC));
                Log.w("response is : ", bufferedReader.toString());

                while((JSON_STRING = bufferedReader.readLine())!= null)
                {
                    stringBuilder.append(JSON_STRING + "\n");
                }

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
            return stringBuilder.toString().trim();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result );
//            Log.w("result is :",result.toString());
//            Log.w("stringBuilder is : ", stringBuilder.toString());
            int count=0;

            try{
                JSONArray json =  new JSONArray(result);
//                Log.w("json is : ", json.toString());
//                Log.w("JSON_STRING is : ", result);

                while(count<json.length()) {
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

                //displaying result in listview
                parsed_json = (ListView) findViewById(R.id.lvParseJSON);
                adapter = new BaseAdapter2(ChartRAC.this, pnr_array, passenger_name_array, seat_no_array, coach_no_array, verification_status_array, passenger_age_array, passenger_gender_array);
                parsed_json.setAdapter(null);
                parsed_json.setAdapter(adapter);
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
