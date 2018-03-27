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

public class Vacant_Seats_Adapter extends BaseAdapter {

    private Activity activity;
    // private ArrayList&lt;HashMap&lt;String, String&gt;&gt; data;
    private static ArrayList seat_no, coach_no;
    private static LayoutInflater inflater = null;

    public Vacant_Seats_Adapter(Activity a, ArrayList seat_no_l, ArrayList coach_no_l) {
        activity = a;
        this.seat_no = seat_no_l;
        this.coach_no = coach_no_l;
        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        return seat_no.size();
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
            vi = inflater.inflate(R.layout.row_list_item_1, null);

        TextView t1 = (TextView) vi.findViewById(R.id.vacant_list_seat_no); // seat_no
        String seat_no_s = seat_no.get(position).toString();
        t1.setText(seat_no_s);

        TextView t2 = (TextView) vi.findViewById(R.id.vacant_list_coach_no); // coach_no
        String coach_no_s = coach_no.get(position).toString();
        t2.setText(coach_no_s);

        return vi;

    }
}