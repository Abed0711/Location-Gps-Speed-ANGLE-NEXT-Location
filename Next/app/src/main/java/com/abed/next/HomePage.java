package com.abed.next;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLException;

import static android.view.View.OnClickListener;

public class HomePage extends AppCompatActivity {

    Button btnNxt;
    EditText howManyShip1;
    String howManyShipPassValueNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.open();


        btnNxt = findViewById(R.id.nextBtn);
        howManyShip1 = (EditText) findViewById(R.id.howManyShip);


        btnNxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                howManyShipPassValueNext = howManyShip1.getText().toString();



                dbManager.insert(howManyShipPassValueNext);
                dbManager.update(howManyShipPassValueNext);
                Toast.makeText(getApplicationContext(), "Updated successfully!", Toast.LENGTH_SHORT).show();
                dbManager.close();


                Intent intent = new Intent(HomePage.this, InputLonLat.class);
                startActivity(intent);
            }
        });
    }


}

