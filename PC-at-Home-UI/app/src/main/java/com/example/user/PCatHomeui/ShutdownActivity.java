package com.example.user.PCatHomeui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ShutdownActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btn_shutdown, btn_restart, btn_cancel, btn_close;
    private Socket clientSocket;// 客户端socket
    private DataOutputStream dataOutput = null;// 客户端发送数据
    private DataInputStream dataInput = null;// 客户端接收数据
    private String connIP = "";
    private ConnThread connThread = null;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(ShutdownActivity.this, (String) msg.obj,
                            Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shutdown_layout);

        btn_shutdown = (Button) findViewById(R.id.btnShutdown);
        btn_restart = (Button) findViewById(R.id.btnReboot);
        btn_cancel = (Button) findViewById(R.id.btnCancel);
        btn_close = (Button) findViewById(R.id.btnClose);

        Intent intent = getIntent();
        connIP = intent.getStringExtra("ip");

        btn_shutdown.setOnClickListener(this);
        btn_restart.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 连接服务器
        switch (v.getId()) {
            case R.id.btnShutdown:
                final String shutdown = "shutdown";
                if (connThread != null) {
                    connThread.interrupt();
                }
                connThread = new ConnThread(connIP, 30000, shutdown);
                connThread.start();
                break;
            case R.id.btnReboot:
                final String reboot = "reboot";
                if (connThread != null) {
                    connThread.interrupt();
                }
                connThread = new ConnThread(connIP, 30000, reboot);
                connThread.start();
                break;
            case R.id.btnCancel:
                final String cancel = "cancel";
                if (connThread != null) {
                    connThread.interrupt();
                }
                connThread = new ConnThread(connIP, 30000, cancel);
                connThread.start();
                break;
            case R.id.btnClose:
                finish();
                break;
            default:
                break;
        }
    }

    class ConnThread extends Thread {
        private String ip;
        private int port;
        private String content;

        public ConnThread(String ip, int port, String content) {
            this.ip = ip;
            this.port = port;
            this.content = content;
        }

        @Override
        public void run() {
            try {
                clientSocket = new Socket(ip, port);
                while (true) {
                    dataOutput = new DataOutputStream(
                            clientSocket.getOutputStream());
                    dataInput = new DataInputStream(
                            clientSocket.getInputStream());
                    String msg = "";
                    if ((dataOutput != null) && (!content.equals(""))) {
                        dataOutput.writeUTF(content);
                    }
                    msg = dataInput.readUTF();
                    if (msg != null && !"".equals(msg)) {
                        Message message = new Message();
                        message.what = 1;
                        message.obj = msg;
                        handler.sendMessage(message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (dataOutput != null) {
                        dataOutput.close();
                    }
                    if (dataInput != null) {
                        dataInput.close();
                    }
                    if (clientSocket != null) {
                        clientSocket = null;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            super.run();
        }
    }
}
