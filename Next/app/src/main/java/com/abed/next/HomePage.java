package com.abed.next;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
        this.deleteDatabase("SHIP.DB");

        DBManager dbManager = new DBManager(getApplicationContext());
        dbManager.open();

        DBManagerLatLon dbManagerLatLon = new DBManagerLatLon(getApplicationContext());
        dbManagerLatLon.open();

        btnNxt = findViewById(R.id.nextBtn);
        howManyShip1 = (EditText) findViewById(R.id.howManyShip);

        SQLiteHelper DB = new SQLiteHelper(this);


        btnNxt.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                howManyShipPassValueNext = howManyShip1.getText().toString();

               // Boolean checkdlt = DB.deleteData(0);

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

