package com.abed.next.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
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
import com.abed.next.R;

import java.util.ArrayList;
import java.util.List;

public class InputLonLat extends AppCompatActivity {
    private LinearLayout lnrDynamicEditTextHolder;
    private Button btnAdd;
    public int length;

    EditText ed, ed_time, ed_Name;

    List<EditText> allEdsLon = new ArrayList<EditText>();
    List<EditText> allEdsLat = new ArrayList<EditText>();
    List<EditText> allEdName = new ArrayList<EditText>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_lon_lat);

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        btnAdd = findViewById(R.id.button);

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.open();


        Cursor cursor = dbManager.fetch();
        cursor.moveToFirst();
        length = Integer.parseInt(cursor.getString(0));
        dbManager.close();

        for (int j = 0; j < length; j++) {
            final TextView rowTextView = new TextView(this);
            rowTextView.setText("Ship #" + (j + 1));
            lnrDynamicEditTextHolder.addView(rowTextView);

            ed_Name = new EditText(InputLonLat.this);
            ed_Name.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ed_Name.setHint("Enter Name Of Ship");
            allEdName.add(ed_Name);
            ed_Name.setId(j + 1);
            ed_Name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed_Name);

            ed_time = new EditText(InputLonLat.this);
            ed_time.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
            ed_time.setHint("Enter Lat");
            allEdsLat.add(ed_time);
            ed_time.setId(j + 1);

            ed_time.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed_time);
            ed = new EditText(InputLonLat.this);
            ed.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL|InputType.TYPE_CLASS_NUMBER);
            ed.setHint("Enter Lon");
            allEdsLon.add(ed);
            ed.setId(j + 1);
            ed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed);

        }


        btnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                DBManagerLatLon dbManagerLatLon = new DBManagerLatLon(getApplicationContext());
                dbManagerLatLon.open();

                dbManagerLatLon.insertSInfo("0", "0", "0");

                for (int i = 0; i < length; i++) {
                    dbManagerLatLon.insertSInfo(allEdName.get(i).getText().toString(), allEdsLat.get(i).getText().toString(), allEdsLon.get(i).getText().toString());
                }
                dbManagerLatLon.close();
                lnrDynamicEditTextHolder.removeAllViews();
                Intent intent_main = new Intent(InputLonLat.this, MainActivity.class);
                startActivity(intent_main);

            }
        });
    }

}