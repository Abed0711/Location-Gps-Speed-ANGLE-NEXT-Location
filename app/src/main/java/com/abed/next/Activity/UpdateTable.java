package com.abed.next.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abed.next.DbManager.DBManager;
import com.abed.next.DbManager.DBManagerLatLon;
import com.abed.next.DbManager.DBManagerUpdate;
import com.abed.next.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class UpdateTable extends AppCompatActivity {

    private LinearLayout lnrDynamicEditTextHolder;
    private Button btnUpdate;
    public int length;

    EditText ed, ed_time;
    TextView ed_Name;

    List<EditText> allEdsLon = new ArrayList<EditText>();
    List<EditText> allEdsLat = new ArrayList<EditText>();
    List<EditText> allEdName = new ArrayList<EditText>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_table);

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder1);
        btnUpdate = findViewById(R.id.buttonUpdate);

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.open();

        DBManagerUpdate dbManagerUpdate = new DBManagerUpdate(getApplicationContext());
        dbManagerUpdate.open();

        Cursor cursor = dbManager.fetch();
        cursor.moveToFirst();
        length = Integer.parseInt(cursor.getString(0));
        dbManager.close();

        DBManagerLatLon dbManagerLatLon = new DBManagerLatLon(getApplicationContext());
        dbManagerLatLon.open();
        Cursor cursorU = dbManagerLatLon.fetchData();
        cursorU.moveToFirst();

        String[] Sname = new String[100];
        int count = 0;
        while (cursorU.moveToNext()) {
            Sname[count] = cursorU.getString(1);
            count++;

        }

        for (int j = 0; j < length; j++) {
            ed_Name = new TextView(getApplicationContext());
            ed_Name.setText(Sname[j]);
            ed_Name.setId(j + 1);
            ed_Name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed_Name);

            ed_time = new EditText(getApplicationContext());
            ed_time.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
            ed_time.setHint("Enter Lat");
            allEdsLat.add(ed_time);
            ed_time.setId(j + 1);

            ed_time.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed_time);

            ed = new EditText(getApplicationContext());
            ed.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_CLASS_NUMBER);
            ed.setHint("Enter Lon");
            allEdsLon.add(ed);
            ed.setId(j + 1);
            ed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed);

        }
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbManagerUpdate.insertSInfo("frmupdate", "0", "0");
                for (int i = 0; i < length; i++) {
                    dbManagerUpdate.insertSInfo(Sname[i], allEdsLat.get(i).getText().toString(), allEdsLon.get(i).getText().toString());
                }
                dbManagerUpdate.close();

                Intent intent_main = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent_main);
                lnrDynamicEditTextHolder.removeAllViews();

            }
        });
    }
}