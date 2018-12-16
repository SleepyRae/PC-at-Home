package com.example.user.PCatHomeui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectActivity extends AppCompatActivity{

    /*private MyWifiManager myWifiManager = null;
    private String serverIP = "", resultIP = "";
    private ScanIPThread scanThread = null;
    private ProgressBar progressBar;*/

    private Button button = null;
    private EditText editText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        int height = getWindow().getWindowManager().getDefaultDisplay()
                .getHeight();
        int width = getWindow().getWindowManager().getDefaultDisplay()
                .getWidth();*/
        setContentView(R.layout.connect_layout);

        button = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipAddr = editText.getText().toString();
                MyApplication app = (MyApplication) getApplication();
                app.setIpAddr(ipAddr);
                Intent backToMain = new Intent(ConnectActivity.this, MainActivity.class);
                startActivity(backToMain);
            }
        });

       /* progressBar = (ProgressBar) findViewById(R.id.progressBar);
        myWifiManager = new MyWifiManager(ConnectActivity.this);
        scanThread = new ScanIPThread();
        scanThread.start();*/
    }

   /* @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1000:
                    Toast.makeText(ConnectActivity.this, "找到可连接PC", Toast.LENGTH_SHORT).show(); // 找到可连接PC
                    Intent controlIntent = new Intent(ConnectActivity.this, ReConnectActivity.class);
                    // 将可以连接的ip发过去
                    controlIntent.putExtra("ip", (String) msg.obj);
                    startActivity(controlIntent);
                    finish();
                    break;
                case 2000:
                    Toast.makeText(ConnectActivity.this, "没有找到可连接PC", Toast.LENGTH_SHORT).show(); // 没有找到可连接PC
                    Intent reScanIntent = new Intent(ConnectActivity.this, ReConnectActivity.class);
                    startActivity(reScanIntent);
                    finish();
                    break;
                default:
                    progressBar.setMax(254);
                    progressBar.setProgress(msg.what);
                    break;
            }
            super.handleMessage(msg);
        }
    };


    // 扫描连接的WiFi所在网段开启了30000端口的C类ip
    // 例如，wifi的ip是192.168.1.1 则扫描 192.168.1.1-192.168.1.254
    class ScanIPThread extends Thread {
        @Override
        public void run() {
            serverIP = myWifiManager.getServerIp();
            int t = serverIP.lastIndexOf(".") + 1;
            resultIP = serverIP.substring(0, t);
            boolean flag = false;
            for (int i = 1; i < 255; i++) {
                try {
                    Socket socket = new Socket();
                    InetSocketAddress s = new InetSocketAddress(resultIP + i,
                            30000);
                    socket.connect(s, 50);
                    Message message = new Message();
                    message.what = 1000;
                    message.obj = resultIP + i;
                    handler.sendMessage(message);
                    flag = true;
                    socket.close();
                    break;
                } catch (IOException e) {
                    handler.sendEmptyMessage(i);
                }
            }
            if (!flag) {
                handler.sendEmptyMessage(2000);
            }
            super.run();
        }
    }*/
}
