package com.example.user.PCatHomeui;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.TimePicker;
import android.widget.Toast;


public class LightActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.light_layout);
        Button settime = (Button) findViewById(R.id.set_time_button);
        settime.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        TimePickerDialog time = new TimePickerDialog(LightActivity.this, new OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(LightActivity.this, hourOfDay + "hour " + minute + "minute", Toast.LENGTH_SHORT).show();
            }
        }, 18, 25, true);
        time.show();
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
