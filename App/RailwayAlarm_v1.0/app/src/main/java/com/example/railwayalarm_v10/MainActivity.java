package com.example.railwayalarm_v10;

/**
 * 名称：登录界面
 * 功能：工人按钮：跳转工人界面；火车按钮：跳转火车界面
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private ImageButton worker;
    private ImageButton railway;
    private int i = 0;
    private int TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.background_main);
        int bwidth = bitmap.getWidth();
        int bheigh = bitmap.getHeight();

        worker = (ImageButton)findViewById(R.id.worker);
        railway = (ImageButton)findViewById(R.id.railway);
        ViewGroup.LayoutParams margin_worker = new ViewGroup.MarginLayoutParams(worker.getLayoutParams());

        initGPS();

        worker.setOnClickListener(new ButtonListener());
        railway.setOnClickListener(new ButtonListener());

    }

    class ButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            if (v==worker){
                Intent intent = new Intent(MainActivity.this,WorkerLoginActivity.class);
                startActivity(intent);
            }
            if (v==railway){
                Intent intent = new Intent(MainActivity.this,RailwayLoginActivity.class);
                startActivity(intent);
            }
        }
    }

    //打开GPS设置界面
    private void initGPS(){
        LocationManager locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            Toast.makeText(this,"请打开GPS",Toast.LENGTH_LONG).show();

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("请打开GPS");
            dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intentGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    Intent intentGPS = new Intent(Settings.ACTION_SECURITY_SETTINGS);
                    startActivityForResult(intentGPS,0);
                }
            });
            dialog.setNegativeButton("退出", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            dialog.show();
        }
    }

    public static final void openGPS(Context context){}

    public boolean onKeyDown(int keyCode, KeyEvent event){
        PackageManager pm = getPackageManager();
        ResolveInfo homeInfo = pm.resolveActivity(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_HOME),0);

        if (keyCode == KeyEvent.KEYCODE_BACK){
            ActivityInfo ai = homeInfo.activityInfo;
            Intent startIntent = new Intent(Intent.ACTION_MAIN);
            startIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            startIntent.setComponent(new ComponentName(ai.packageName,ai.name));
            startActivity(startIntent);
            finish();
            return true;
        }else
            return super.onKeyDown(keyCode,event);
    }


    @Override
    public void onDestroy(){
        super.onDestroy();
    }
}
