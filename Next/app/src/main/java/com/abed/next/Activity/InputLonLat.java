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

        DBManagerLatLon dbManagerLatLon = new DBManagerLatLon(getApplicationContext());
        dbManagerLatLon.open();
        //dbManagerLatLon.deleteInfo();

        Cursor cursor = dbManager.fetch();
        cursor.moveToFirst();
        length = Integer.parseInt(cursor.getString(0));
        dbManager.close();


        for (int j = 0; j < length; j++) {
            //TextView
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
            ed_time.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ed_time.setHint("Enter Lat");
            allEdsLat.add(ed_time);
            ed_time.setId(j + 1);

            ed_time.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed_time);
            //EditText for Speed
            ed = new EditText(InputLonLat.this);
            ed.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
            ed.setHint("Enter Lon");
            allEdsLon.add(ed);
            ed.setId(j + 1);
            ed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            lnrDynamicEditTextHolder.addView(ed);

        }


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] stringsName = new String[allEdName.size()];
                String[] stringsLon = new String[(allEdsLon.size())];
                String[] stringsLat = new String[(allEdsLat.size())];
                ArrayList<String> myList = new ArrayList<String>();
                dbManagerLatLon.insertSInfo("Abed", "0", "0");


                for (int i = 0; i < allEdsLat.size(); i++) {
                    stringsName[i] = String.valueOf(allEdName.get(i).getText());
                    stringsLon[i] = String.valueOf(allEdsLon.get(i).getText());
                    stringsLat[i] = String.valueOf(allEdsLat.get(i).getText());
                    //Toast.makeText(InputLonLat.this, i+" : Speed->"+stringsLon[i]+"\n Time->"+stringsLat[i], Toast.LENGTH_LONG).show();

                    dbManagerLatLon.insertSInfo(stringsName[i], stringsLat[i], stringsLon[i]);
                    // dbManagerLatLon.updateInfo(i,"Abed",stringsLat[i],stringsLon[i]);
                    // dbManagerLatLon.insertSInfo("Abed",stringsLat[i],stringsLon[i]);
                    //Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                }

                dbManagerLatLon.close();


//                Intent intent_Lon = new Intent(getApplicationContext(), MainActivity.class);
//                intent_Lon.putExtra("stringLon", stringsLon);
//                intent_Lon.putExtra("stringLat", stringsLat);
                lnrDynamicEditTextHolder.removeAllViews();

                Intent intent_main = new Intent(InputLonLat.this, MainActivity.class);
                startActivity(intent_main);
//
//                startActivity(intent_Lon);


                //startActivity(intentLat);


            }
        });
    }

}