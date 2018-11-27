package com.example.user.smarthomeui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;


public class testopencv extends AppCompatActivity {

    Button btnProcess;
    Bitmap srcBitmap;
    Bitmap grayBitmap;
    ImageView imgHuaishi;
    private static boolean flag = true;
    private static boolean isFirst = true;
 //   private static final String TAG = "MainActivity";

    //OpenCV库加载并初始化成功后的回调函数
    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {

        @Override
        public void onManagerConnected(int status) {
            // TODO Auto-generated method stub
            switch (status) {
                case BaseLoaderCallback.SUCCESS:
      //              Log.i(TAG, "成功加载");
                    break;
                default:
                    super.onManagerConnected(status);
     //               Log.i(TAG, "加载失败");
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testopencv);
        initUI();
        btnProcess.setOnClickListener(new ProcessClickListener());
    }


//    @Override
    //  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    //    getMenuInflater().inflate(R.menu., menu);
    //  return true;
    // }

    public void initUI() {
        btnProcess = (Button) findViewById(R.id.btn_gray_process);
        imgHuaishi = (ImageView) findViewById(R.id.img_huaishi);
    //    Log.i(TAG, "initUI sucess...");

    }

    public void procSrc2Gray() {
        Mat rgbMat = new Mat();
        Mat grayMat = new Mat();
        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.image);
        grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Config.RGB_565);
        Utils.bitmapToMat(srcBitmap, rgbMat);//convert original bitmap to Mat, R G B.
        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
       // Log.i(TAG, "procSrc2Gray sucess...");
    }

    private class ProcessClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (isFirst) {
                procSrc2Gray();
                isFirst = false;
            }
            if (flag) {
                imgHuaishi.setImageBitmap(grayBitmap);
                btnProcess.setText("查看原图");
                flag = false;
            } else {
                imgHuaishi.setImageBitmap(srcBitmap);
                btnProcess.setText("灰度化");
                flag = true;
            }
        }

    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        //load OpenCV engine and init OpenCV library
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_4, getApplicationContext(), mLoaderCallback);
      //  Log.i(TAG, "onResume sucess load OpenCV...");
//		new Handler().postDelayed(new Runnable(){
//
//			@Override
//			public void run() {
//				// TODO Auto-generated method stub
//				procSrc2Gray();
//			}
//
//		}, 1000);


    }
}
