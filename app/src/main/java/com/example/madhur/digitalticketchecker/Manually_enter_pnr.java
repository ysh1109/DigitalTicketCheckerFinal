package com.example.madhur.digitalticketchecker;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
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

/**
 * Created by root on 26/3/18.
 */

public class Manually_enter_pnr extends Activity {
    String pnr_number;
    EditText pnr_no_entered;
    Button verify_ticket;
    TextView error;
    TextView done;
    @Override
    protected void onCreate(@Nullable Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.enter_pnr_verify);

        verify_ticket = (Button)findViewById(R.id.verify_by_only_pnr_btn);
        pnr_no_entered = (EditText)findViewById(R.id.manual_enter_pnr_no);
        error = (TextView)findViewById(R.id.manual_pnr_error);
        done=(TextView)findViewById(R.id.successfully_updated_pnr);

        verify_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pnr_no_entered.getText()!=null){
                    pnr_number = pnr_no_entered.getText().toString();
                    error.setText("");
                    new send_pnr_to_server().execute();
                }
                else{
                    verify_ticket.setText("Please Enter The PNR NUMBER FIRST");
                }
            }
        });

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

                sending_request.put("update_values",pnr_number);

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
            super.onPostExecute(result );
            Log.w("result is ", result);

            if(result.equals("INVALID REQUEST")){
                error.setText("INVALID PNR ENTER AGAIN");
            }
//            if(result.equals("UPDATED")||result.equals("Updated")){
            else{
                done.setText("SUCESSFULY VERIFIED");
            }
        }
    }
}
