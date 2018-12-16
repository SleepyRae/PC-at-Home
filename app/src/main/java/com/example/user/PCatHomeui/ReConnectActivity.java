package com.example.user.PCatHomeui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class ReConnectActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_rescan, btn_close;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.reconnect_layout);

        btn_rescan = (Button) findViewById(R.id.btnReScan);
        btn_close = (Button) findViewById(R.id.btnClose);

        btn_rescan.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnReScan:
                Intent intent = new Intent(ReConnectActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btnClose:
                finish();
                break;
            default:
                break;
        }
    }
}
