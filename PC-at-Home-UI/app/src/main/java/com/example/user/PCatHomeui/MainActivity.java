package com.example.user.PCatHomeui;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    private static boolean isExit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView check_view = (TextView) findViewById(R.id.check_textview);
        TextView connect_view = (TextView) findViewById(R.id.connect_textview);
        TextView help_view = (TextView) findViewById(R.id.help_textview);
        TextView history_view = (TextView) findViewById(R.id.history_textview);
        check_view.setOnClickListener(this);
        connect_view.setOnClickListener(this);
        help_view.setOnClickListener(this);
        history_view.setOnClickListener(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build( );
    }


    @Override
    public void onClick(View v) {
        switch (v.getId( )) {
            case R.id.check_textview:
                Intent check_intent = new Intent(MainActivity.this, CheckActivity.class);
                startActivity(check_intent);
                break;
            case R.id.help_textview:
                Intent help_intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(help_intent);
                break;
            case R.id.connect_textview:
                Intent connect_intent = new Intent(MainActivity.this, ConnectActivity.class);
                startActivity(connect_intent);
                break;
            case R.id.history_textview:
                Intent history_intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(history_intent);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater( ).inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId( )) {
            case R.id.setting1:
                Toast.makeText(this, "还没有做声音设置 打我啊", Toast.LENGTH_SHORT).show( );
                break;
            case R.id.setting2:
                Toast.makeText(this, "还没有做背景设置 打我啊", Toast.LENGTH_SHORT).show( );
                break;
            case R.id.info_item:
                Intent info_intent = new Intent(MainActivity.this, InfoActivity.class);
                startActivity(info_intent);
                break;
            case R.id.quit_item:
                Toast.makeText(this, "再见吧 朋友", Toast.LENGTH_SHORT).show( );
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder( )
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build( );
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build( );
    }

    @Override
    public void onStart() {
        super.onStart( );

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect( );
        AppIndex.AppIndexApi.start(client, getIndexApiAction( ));
    }

    @Override
    public void onStop() {
        super.onStop( );

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction( ));
        client.disconnect( );
    }

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }
}
