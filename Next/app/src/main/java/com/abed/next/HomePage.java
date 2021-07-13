package com.abed.next;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import static android.view.View.OnClickListener;

public class HomePage extends AppCompatActivity {

    Button btnNxt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        btnNxt = findViewById(R.id.nextBtn);

        //Button to first layout to seccond

        btnNxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,InputLonLat.class);
                startActivity(intent);
            }
        });
    }
}