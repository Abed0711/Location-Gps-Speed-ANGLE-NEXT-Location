package com.abed.next;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InputLonLat extends AppCompatActivity {
    private LinearLayout lnrDynamicEditTextHolder;
    private EditText edtNoCreate;
    private Button btnCreate1;
    private Button btnAdd;


    EditText ed;

    List<EditText> allEds = new ArrayList<EditText>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_lon_lat);

        lnrDynamicEditTextHolder = (LinearLayout) findViewById(R.id.lnrDynamicEditTextHolder);
        edtNoCreate = (EditText) findViewById(R.id.edtNoCreate);
        btnCreate1 = (Button) findViewById(R.id.btnCreate);
        btnAdd = findViewById(R.id.button);


        btnCreate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtNoCreate.getText().toString().length() >= 0) {
                    try {
                        lnrDynamicEditTextHolder.removeAllViews();
                    } catch (Throwable e) {
                        e.printStackTrace();
                    }
                }
                int length = Integer.parseInt(edtNoCreate.getText().toString());
                for (int i = 0; i < length; i++) {
                    ed = new EditText(InputLonLat.this);
                    allEds.add(ed);
                    ed.setId(i + 1);
                    ed.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    lnrDynamicEditTextHolder.addView(ed);
                }
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] strings = new String[(allEds.size())];
                        for (int i = 0; i < allEds.size(); i++) {
                            strings[i] = String.valueOf(allEds.get(i).getText());
                            Toast.makeText(InputLonLat.this, strings[i], Toast.LENGTH_LONG).show();
                        }

                    }
                });


            }


        });

    }
}