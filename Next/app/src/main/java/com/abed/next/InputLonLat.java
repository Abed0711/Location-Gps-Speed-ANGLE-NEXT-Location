package com.abed.next;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InputLonLat extends AppCompatActivity {
    private LinearLayout lnrDynamicEditTextHolder;
    private Button btnAdd;


    EditText ed,ed_time;

    List<EditText> allEdsLon = new ArrayList<EditText>();
    List<EditText> allEdsLat = new ArrayList<EditText>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_lon_lat);

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        btnAdd = findViewById(R.id.button);

        Intent intent = getIntent();
        String str = intent.getStringExtra("howManyshipMsgKey");
        int length = Integer.parseInt(str);


            for (int j = 0; j < length; j++) {
                //TextView
                final TextView rowTextView = new TextView(this);
                rowTextView.setText("Ship #" +(j+1));
                lnrDynamicEditTextHolder.addView(rowTextView);
                ed_time = new EditText(InputLonLat.this);
                ed_time.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                ed_time.setHint("Enter Lat");
                allEdsLat.add(ed_time);
                ed_time.setId(j+1);

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
                //Edit Text forTime


            }
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] stringsLon = new String[(allEdsLon.size())];
                String[] stringsLat = new String[(allEdsLat.size())];
                ArrayList<String> myList = new ArrayList<String>();
                for (int i = 0; i < allEdsLat.size(); i++) {
                    stringsLon[i] = String.valueOf(allEdsLon.get(i).getText());
                    stringsLat[i] = String.valueOf(allEdsLat.get(i).getText());
                    //Toast.makeText(InputLonLat.this, i+" : Speed->"+stringsLon[i]+"\n Time->"+stringsLat[i], Toast.LENGTH_LONG).show();
                }


                Intent intent_Lon = new Intent(getApplicationContext(), MainActivity.class);
                intent_Lon.putExtra("stringLon", stringsLon);
                intent_Lon.putExtra("stringLat", stringsLat);

                Intent intent_main = new Intent(InputLonLat.this, MainActivity.class);
                startActivity(intent_main);

                startActivity(intent_Lon);
                lnrDynamicEditTextHolder.removeAllViews();
                //startActivity(intentLat);




            }
        });
    }
}