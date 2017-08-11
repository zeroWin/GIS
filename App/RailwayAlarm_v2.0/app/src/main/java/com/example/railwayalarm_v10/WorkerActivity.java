package com.example.railwayalarm_v10;

/**
 * 名称：工人界面
 * 功能：开启WorkerService
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class WorkerActivity extends AppCompatActivity {

    public static int mode = 0;
    public static int last_mode = 0;

    public static String input = null;

    private MsgReceiver msgReceiver;

    public static WorkerActivity _instance = null;

//    private Intent bcIntent = new Intent("com.example.railwayalarm_v10.CLIENTRECEIVER");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker);

        //若正常关闭，接收返回警报级别
        Intent reIntent = getIntent();
        last_mode = reIntent.getIntExtra("extra_data",0);
        mode = last_mode;

        //动态注册广播接收器
        msgReceiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.railwayalarm_v10.RECEIVER");
        registerReceiver(msgReceiver,intentFilter);


//        Intent intent = getIntent();
//        String input = intent.getStringExtra("inputNum");
//        Intent startIntent = new Intent(this,WorkerService.class);
//        startIntent.putExtra("input",input);
//        Toast.makeText(this,String.valueOf(last_mode),Toast.LENGTH_LONG).show();
//        startIntent.putExtra("lastMode",last_mode);
//        startIntent.putExtra("input",123);
//        startService(startIntent);

//        Intent intent1 = new Intent(this,WorkerListenerServer.class);
//        startService(intent1);
    }

    public class MsgReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, final Intent intent){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        last_mode = mode;
                        mode = intent.getIntExtra("mode",0);
                        if (mode!=last_mode){
                            Clear_last_Alarm(last_mode);
                            Alarm_Mode(mode);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void Clear_last_Alarm(int last_mode){
        switch (last_mode){
            case 1:
                if(RedAlarmActivity.state_flag == 1)
                    RedAlarmActivity._instance.alarmStop();
                break;
            case 2:
                if(OrangeAlarmActivity.state_flag == 1)
                    OrangeAlarmActivity._instance.alarmStop();
                break;
            case 3:
                if(YellowAlarmActivity.state_flag == 1)
                    YellowAlarmActivity._instance.alarmStop();
                break;
            default:
                break;
        }
    }

    private void Alarm_Mode(int mode){
        switch (mode){
            case 0:
                Intent mainIntent = new Intent(WorkerActivity.this,WorkerActivity.class);
                startActivity(mainIntent);
                break;
            case 1:
                Intent redIntent = new Intent(WorkerActivity.this,RedAlarmActivity.class);
                startActivity(redIntent);
                break;
            case 2:
                Intent orangeIntent = new Intent(WorkerActivity.this,OrangeAlarmActivity.class);
                startActivity(orangeIntent);
                break;
            case 3:
                Intent yellowIntent = new Intent(WorkerActivity.this,YellowAlarmActivity.class);
                startActivity(yellowIntent);
                break;
            default:
                break;
        }
    }

    @Override
    public void onBackPressed(){
        //退出弹框
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(R.drawable.ic_launcher)
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent stopIntent = new Intent(WorkerActivity.this,WorkerService.class);
                        stopService(stopIntent);

                        Intent intent1 = new Intent(WorkerActivity.this,WorkerListenerServer.class);
                        stopService(intent1);

                        Intent intent2 = new Intent(WorkerActivity.this,WorkerReceiver.class);
                        PendingIntent pi = PendingIntent.getBroadcast(WorkerActivity.this,0,intent2,0);
                        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        alarmManager.cancel(pi);

                        Intent backIntent = new Intent(WorkerActivity.this,MainActivity.class);
                        startActivity(backIntent);

                        finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
