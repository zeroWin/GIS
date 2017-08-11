package com.example.railwayalarm_v10;

/**
 * 名称：火车界面
 * 功能：开启RailwayService
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class RailwayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_railway);

        Intent intent = getIntent();
        String input = intent.getStringExtra("inputNum");
        Intent startIntent = new Intent(this,RailwayService.class);
        startIntent.putExtra("input",input);
        startService(startIntent);

        Intent receiverIntent = new Intent(this,RailwayListenerService.class);
        startService(receiverIntent);

    }

    @Override
    public void onBackPressed(){
        //退出弹框
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //点击确认
                        Intent stopIntent = new Intent(RailwayActivity.this,RailwayService.class);
                        stopService(stopIntent);
                        Intent intent1 = new Intent(RailwayActivity.this,RailwayListenerService.class);
                        stopService(intent1);
                        Intent intent2 = new Intent(RailwayActivity.this,RailwayReceiver.class);
                        PendingIntent pi = PendingIntent.getBroadcast(RailwayActivity.this,0,intent2,0);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pi);
                        Intent backIntent = new Intent(RailwayActivity.this,MainActivity.class);
                        startActivity(backIntent);
                        finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
