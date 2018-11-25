package com.example.user.smarthomeui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ConnectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connect_layout);
        Intent warning1_intent = new Intent(ConnectActivity.this,WarningActivity.class);
        startActivity(warning1_intent);
    }
}
