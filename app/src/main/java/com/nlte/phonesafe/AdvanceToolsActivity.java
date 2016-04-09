package com.nlte.phonesafe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdvanceToolsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_tools);
    }
    public void locationQuery(View view){
        Intent intent = new Intent(AdvanceToolsActivity.this, AddressQueryActivity.class);
        startActivity(intent);
    }
    public void rocket(View view){
        Intent intent = new Intent(AdvanceToolsActivity.this, RocketActivity.class);
        startActivity(intent);
    }

}
