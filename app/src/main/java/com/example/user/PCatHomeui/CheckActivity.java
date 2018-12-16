package com.example.user.PCatHomeui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CheckActivity extends AppCompatActivity implements View.OnClickListener{

    private List<Appliances> appliancesList = new ArrayList<Appliances>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.check_layout);
        initAppliances(); // 初始化数据
        AppliancesAdapter adapter = new AppliancesAdapter(CheckActivity.this, R.layout.appliances_item, appliancesList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        TextView check_view = (TextView) findViewById(R.id.new_appliance_textview);
        check_view.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Appliances appliances = appliancesList.get(position);
                Toast.makeText(CheckActivity.this, "PC连接中......", Toast.LENGTH_SHORT).show();
                switch (appliances.getName()){
                    case "远程关机":
                        Intent turnoff_intent = new Intent(CheckActivity.this,TurnoffActivity.class);
                        startActivity(turnoff_intent);
                        break;
                    case "定时关机":
                        Intent turnoffontime_intent = new Intent(CheckActivity.this,TurnoffActivity.class);
                        startActivity(turnoffontime_intent);
                        break;
                    case "摄像头管理":
                        Intent monitor_intent = new Intent(CheckActivity.this, CameraActivity.class);
                        startActivity(monitor_intent);
                        break;
                    case "使用情况":
                       // Intent microphone_intent = new Intent(CheckActivity.this,MicrophoneActivity.class);
                      //  startActivity(microphone_intent);
                        break;
                }


            }
        });
    }
    private void initAppliances() {
        Appliances remote_turnoff = new Appliances("远程关机");
        appliancesList.add(remote_turnoff);
        Appliances turnoff_ontime = new Appliances("定时关机");
        appliancesList.add(turnoff_ontime);
        Appliances camera = new Appliances("摄像头管理");
        appliancesList.add(camera);
        Appliances report = new Appliances("使用情况");
        appliancesList.add(report);
    }
    public void onClick(View v) {
        Intent connect_intent = new Intent(CheckActivity.this,ConnectActivity.class);
        startActivity(connect_intent);
    }
}
