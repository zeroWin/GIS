package com.example.railwayalarm_v10;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class OrangeAlarmActivity extends AppCompatActivity {

    private int last_mode = 2;
    private ImageButton OrangeAlarmStop;

    int hit;
    private SoundPool soundPool;
    private Vibrator vibrator;

    public static OrangeAlarmActivity _instance = null;
    public static int state_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orange_alarm);
        OrangeAlarmStop = (ImageButton)findViewById(R.id.orange);
        OrangeAlarmStop.setOnClickListener(new OrangeButtonListener());

        _instance = this;
        alarmInit();
    }

    class OrangeButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if (v==OrangeAlarmStop){
                alarmStop();
                Intent intent = new Intent(OrangeAlarmActivity.this,WorkerActivity.class);
                intent.putExtra("extra_data",last_mode);
                startActivity(intent);
//                Intent intent1 = new Intent(OrangeAlarmActivity.this,WorkerService.class);
//                startService(intent1);
                finish();
                onDestroy();
            }
        }
    }

    public void alarmInit(){
        state_flag = 1;
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000,5000,1000,5000},1);

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC,100);
        hit = soundPool.load(OrangeAlarmActivity.this,R.raw.alert1,0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                soundPool.play(hit,1,1,0,-1,1.0f);
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
