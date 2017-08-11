package com.example.railwayalarm_v10;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class YellowAlarmActivity extends AppCompatActivity {

    private int last_mode = 3;
    private ImageButton YellowAlarmStop;

    int hit;
    private SoundPool soundPool;
    private Vibrator vibrator;

    public static YellowAlarmActivity _instance = null;
    public static int state_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yellow_alarm);
        YellowAlarmStop = (ImageButton)findViewById(R.id.yellow);
        YellowAlarmStop.setOnClickListener(new YellowButtonListener());

        _instance = this;
        alarmInit();
    }

    class YellowButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if (v==YellowAlarmStop){
                alarmStop();
                Intent intent = new Intent(YellowAlarmActivity.this,WorkerActivity.class);
                intent.putExtra("extra_data",last_mode);
                startActivity(intent);
//                Intent intent1 = new Intent(YellowAlarmActivity.this,WorkerService.class);
//                startService(intent1);
                finish();
                onDestroy();
            }
        }
    }

    public void alarmInit(){
        state_flag = 1;
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000,1000,1000,1000},1);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,100);
        hit = soundPool.load(YellowAlarmActivity.this,R.raw.alert1,0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                soundPool.play(hit,1,1,0,-1,2.0f);
            }
        });
    }

    public void alarmStop(){
        state_flag = 0;
        vibrator.cancel();
        soundPool.stop(hit);
    }
    @Override
    public void onBackPressed(){

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        finish();
    }
}
