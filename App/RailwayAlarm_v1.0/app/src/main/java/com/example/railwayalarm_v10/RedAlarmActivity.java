package com.example.railwayalarm_v10;

/**
 * vibrator.vibrate(new long[]{10000,5000,10000,5000},0);
 * 第二个参数为2
 * 先执行停10000，振5000，后循环执行停10000，振5000
 * 单位：ms
 */

/**
 * soundPool.play(hit,1,1,0,-1,(float)2);
 * play函数参数描述：
 * 参数1：int soundID --> 音乐资源的ID
 * 参数2：float leftVolume --> 左声道音量大小，相对值，取值范围：0.0f—1.0f
 * 参数3：float rightVolume --> 右声道音量大小，相对值，取值范围：0.0f—1.0f
 * 参数4：int priority --> 优先级：值越大优先级越高，0的优先级最低，有效参数
 * 参数5：int loop --> 是否循环播放，取值不限，负数：无穷播放（建议-1）；非负数：循环次数，例，0表示循环0次，即执行一次
 * 参数6：float rate --> 播放速率，取值范围：0.5f—2.0f
 */

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class RedAlarmActivity extends AppCompatActivity {

    private int last_mode = 1;
    private ImageButton RedAlarmStop;

    int hit;
    private SoundPool soundPool;
    private Vibrator vibrator;

    public static RedAlarmActivity _instance = null;
    public static int state_flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_alarm);
        RedAlarmStop = (ImageButton)findViewById(R.id.red);
        RedAlarmStop.setOnClickListener(new RedButtonListener());

        _instance = this;
        alarmInit();
    }

    class RedButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if (v==RedAlarmStop){
                alarmStop();
                Intent intent = new Intent(RedAlarmActivity.this,WorkerActivity.class);
                intent.putExtra("extra_data",last_mode);
                startActivity(intent);
//                Intent intent1 = new Intent(RedAlarmActivity.this,WorkerService.class);
//                startService(intent1);
                RedAlarmActivity.this.finish();
                onDestroy();
            }
        }
    }

    public void alarmInit(){
        state_flag = 1;
        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(new long[]{1000,10000,1000,10000},1);

        soundPool = new SoundPool(1,AudioManager.STREAM_MUSIC,100);
        hit = soundPool.load(this,R.raw.alert1,0);

        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                soundPool.play(hit,1,1,0,-1,0.5f);
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
