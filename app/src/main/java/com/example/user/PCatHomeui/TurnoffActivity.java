package com.example.user.PCatHomeui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

public class TurnoffActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_turnoff);
        TextView turnoff_view = (TextView) findViewById(R.id.turnoff_textview);
        TextView setttime_view = (TextView) findViewById(R.id.settime_textview);
        turnoff_view.setOnClickListener(this);
        setttime_view.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId( )) {
            case R.id.turnoff_textview:
                Intent turnoff_intent = new Intent(TurnoffActivity.this, ScanActivity.class);
                startActivity(turnoff_intent);
                break;
            case R.id.settime_textview:
                Intent settime_intent = new Intent(TurnoffActivity.this, SettimeActivity.class);
                startActivity(settime_intent);
                break;
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

}
