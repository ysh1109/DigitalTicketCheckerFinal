package com.example.madhur.digitalticketchecker;

/**
 * Created by root on 24/3/18.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class BaseAdapter2 extends BaseAdapter {

    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    private static ArrayList pnr, seat_no, coach_no, verification_status, passenger_name, pass_age, pass_gender;
    private static LayoutInflater inflater = null;

    public BaseAdapter2(Activity a, ArrayList pnr_l, ArrayList passenger_name_l, ArrayList seat_no_l, ArrayList coach_no_l, ArrayList verification_status_l, ArrayList passenger_age_l, ArrayList passenger_gender_l) {
        activity = a;
        this.pnr = pnr_l;
        this.seat_no = seat_no_l;
        this.coach_no = coach_no_l;
        this.verification_status = verification_status_l;
        this.passenger_name = passenger_name_l;
        this.pass_age = passenger_age_l;
        this.pass_gender = passenger_gender_l;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return pnr.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(R.layout.row_listitem, null);

        TextView t1 = (TextView) vi.findViewById(R.id.pnr); // pnr
        String pnr_s = pnr.get(position).toString();
        t1.setText(pnr_s);

        TextView t2 = (TextView) vi.findViewById(R.id.passenger_name_t_v); // passenger_name
        String passenger_name_s = passenger_name.get(position).toString();
        t2.setText(passenger_name_s);

        TextView t3 = (TextView) vi.findViewById(R.id.seat_no); // seat_no
        String seat_no_s = seat_no.get(position).toString();
        t3.setText(seat_no_s);

        TextView t4 = (TextView) vi.findViewById(R.id.coach_no); // coach_no
        String coach_no_s = coach_no.get(position).toString();
        t4.setText(coach_no_s);


        TextView t6 = (TextView) vi.findViewById(R.id.passenger_age);
        String passenger_age_s = pass_age.get(position).toString();
        t6.setText(passenger_age_s);

        TextView t7 = (TextView) vi.findViewById(R.id.passenger_gender);
        String passenger_gender_s = pass_gender.get(position).toString();
        t7.setText(passenger_gender_s);
        return vi;

    }
}