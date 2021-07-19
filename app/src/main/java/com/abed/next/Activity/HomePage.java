package com.abed.next.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.abed.next.DbManager.DBManager;
import com.abed.next.DbManager.DBManagerLatLon;
import com.abed.next.Models.SQLiteHelper;
import com.abed.next.R;

import static android.view.View.OnClickListener;

public class HomePage extends AppCompatActivity {

    Button btnNxt;
    EditText howManyShip1;
    String howManyShipPassValueNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        this.deleteDatabase("SHIP.DB");

        btnNxt = findViewById(R.id.nextBtn);
        howManyShip1 = (EditText) findViewById(R.id.howManyShip);

        btnNxt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                DBManager dbManager = new DBManager(HomePage.this);
                dbManager.open();

                DBManagerLatLon dbManagerLatLon = new DBManagerLatLon(HomePage.this);
                dbManagerLatLon.open();
                howManyShipPassValueNext = howManyShip1.getText().toString();

                if (howManyShipPassValueNext.isEmpty()) {
                    Toast.makeText(HomePage.this, "Enter How Many Ship!", Toast.LENGTH_SHORT).show();
                } else {
                    dbManager.insert(howManyShipPassValueNext);
                    dbManager.update(howManyShipPassValueNext);
                    Toast.makeText(HomePage.this, "Please wait ..", Toast.LENGTH_SHORT).show();
                    dbManager.close();

                    Intent intent = new Intent(HomePage.this, InputLonLat.class);
                    startActivity(intent);
                }
            }
        });
    }
}

