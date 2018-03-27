package com.example.madhur.digitalticketchecker;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
/**
 * Created by root on 25/3/18.
 */

public class Base_activity extends AppCompatActivity {

    private TextView t_v_nav;

    ScanFragment scan_nav;
    ChartsFragment charts_nav;
    UpdateFragment update_nav;
    TextView train_display;
    Integer menu_entry=0;
    String The_Train_Number;
    @Override
    protected void onCreate(@Nullable Bundle nInstance) {
        super.onCreate(nInstance);
        setContentView(R.layout.base_activity);

        The_Train_Number = getIntent().getExtras().getString("train_no");

        train_display = (TextView)findViewById(R.id.display_train_number);
        train_display.setText(The_Train_Number);

        Bundle d = new Bundle();
        d.putString("train_no", The_Train_Number);

        scan_nav = new ScanFragment();
        charts_nav = new ChartsFragment();

        scan_nav.setArguments(d);
        charts_nav.setArguments(d);

        set_view(scan_nav);

        BottomNavigationView b_n = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        b_n.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_scan:
                        set_view(scan_nav);
                        return true;
                    case R.id.navigation_charts:
                        menu_entry = 1;
                        set_view(charts_nav);
                        return true;
                    default:
                        return false;
                }
            }
        });
    }
    private void set_view(Fragment f){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_frame_1, f);
        ft.commit();
    }
}
