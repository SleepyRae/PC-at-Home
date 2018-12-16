package com.example.user.PCatHomeui;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class CameraActivity extends AppCompatActivity {

    private Switch monitorSwitch = null;
    private Socket clientSocket = null;
    private Handler mHandler = null;
    private ReceiveThread receiveThread = null;
    private RenderThread renderThread = null;
    private ImageView iv;
    private Bitmap bitmap;
    private String hostIP = null;

    @SuppressLint("HandlerLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor_layout);
        monitorSwitch = (Switch) findViewById(R.id.switch1);
        iv = (ImageView) findViewById(R.id.monitorFrame);

        // 允许在主线程发送http请求
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());

        monitorSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                MyApplication app = (MyApplication) getApplication();
                hostIP = app.getIpAddr();
                if (hostIP != null) {
                    if (b) {
                        // 开启摄像头
                        try {
                            URL openCameraURL = new URL("http://" + hostIP + ":8080/testConnect?command=cameraOn");
                            HttpURLConnection connection = (HttpURLConnection) openCameraURL.openConnection();
                            InputStream inputStream = connection.getInputStream();
                            String line;
                            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                            while ((line = br.readLine()) != null) {
                                System.out.println(line);
                            }

                            clientSocket = new Socket(hostIP, 2333);
                            receiveThread = new ReceiveThread(clientSocket);
                            receiveThread.start();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        // 关闭摄像头
                        try {
                            // 直接关闭客户端socket, 服务器自动关闭
                            clientSocket.close();
                            clientSocket = null;
                            iv.setImageBitmap(null);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(CameraActivity.this, "请设置服务器IP地址", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        iv.setImageBitmap(bitmap);
                        break;
                    default:
                        break;
                }
                super.handleMessage(msg);
            }
        };
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class ReceiveThread extends Thread {

        private final RenderThread renderThread;

        private final Socket serverSocket;

        private final BlockingQueue<byte[]> frameQueue = new LinkedBlockingDeque<>();

        DataInputStream dataInput;

        private ReceiveThread(Socket s) throws IOException {
            this.serverSocket = s;
            this.renderThread = new RenderThread(frameQueue);
            dataInput = new DataInputStream(s.getInputStream());
        }

        @Override
        public void run() {
            try {
                renderThread.start();
                dataInput = new DataInputStream(serverSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            while (serverSocket.isConnected()) {
                try {
                    int size = dataInput.readInt();
                    byte[] frame = new byte[size];
                    int len = 0;
                    while (len < size) {
                        len += dataInput.read(frame, len, size - len);
                    }
                    frameQueue.add(frame);
                    Log.i("size", frameQueue.size() + "");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            renderThread.interrupt();
        }

    }

    private class RenderThread extends Thread {
        private final BlockingQueue<byte[]> frameQueue;

        RenderThread(BlockingQueue<byte[]> queue) {
            this.frameQueue = queue;
        }

        @Override
        public void run() {
            while (true) {
                ByteArrayOutputStream outPut = new ByteArrayOutputStream();
                try {
                    byte[] data = frameQueue.take();
                    bitmap = BitmapFactory.decodeByteArray(data, 0,
                            data.length);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outPut);
                    Message message = new Message();
                    message.what = 1;
                    mHandler.sendMessage(message);
                } catch (InterruptedException e) {
                    // 退出
                }
            }
        }
    }
}
