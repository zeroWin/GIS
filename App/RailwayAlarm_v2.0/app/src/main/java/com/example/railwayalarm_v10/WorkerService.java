package com.example.railwayalarm_v10;

/**
 * 名称：WorkerService
 * 功能：启动定位，将位置信息发送给服务器，接收警报信号，开启警报模式
 */

/**
 * 警报描述：
 * 1.安全模式：无警报时持续此模式，保持服务基本功能
 * 2.黄色警报：三级警报，警报方式：震动+响铃，警报频率：
 * 3.橙色警报：二级警报，警报方式：震动+响铃，警报频率：
 * 4.红色警报：一级警报，警报方式：震动+响铃，警报频率：
 */

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;

import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.app.NotificationCompat;

import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import java.net.Socket;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.lang.*;


public class WorkerService extends Service {

    private LocationManager locationManager;
    private LocationManager gpsLocationManager;
    private LocationManager networkLocationManager;
    private Location location;
    private Location gpsLocation;
    private Location networkLocation;
    private static final int MINTIME = 1000;
    private static final int MININSTANCE = 1;
    private static Context mContext;
    private  int star_number;
    private long time;
    private double latitude = -1;
    private double longitude;
    private String AlarmInfo = "安全";
    private Notification notification;
    private int mode = 0;
    private int lastMode = 0;
    private Intent bIntent = new Intent("com.example.railwayalarm_v10.RECEIVER");
    private Sender mBinder = new Sender();

    private static String client_num;
    private static int firsttime;

    private String Addr;
    private int port;
//    private ClientReceiver clientReceiver;

    public WorkerService(){

    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){

        flags = START_STICKY;

        Intent intent1 = new Intent(this,WorkerService.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,intent1,0);
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("铁路预警")
                .setContentText("工人")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .build();

        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        startForeground(1,notification);

        Addr = Variable.socketAddr;
        port = Variable.socketPort;

//        lastMode = intent.getIntExtra("lastMode",0);
//        if ((client_num == null)&&(lastMode==0)){
            Object number = intent.getExtras().get("inputNum");
//            Toast.makeText(this,String.valueOf(number),Toast.LENGTH_LONG).show();
            client_num = (String.valueOf(number));
//        }
//        client_num="asd";

//        Toast.makeText(this,client_num,Toast.LENGTH_LONG).show();
//        Toast.makeText(this,String.valueOf(lastMode),Toast.LENGTH_LONG).show();
//        Toast.makeText(this,String.valueOf(firsttime),Toast.LENGTH_LONG).show();

//        client_num = "asd";
//        //动态注册广播接收器
//        clientReceiver = new ClientReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        intentFilter.addAction("com.example.railwayalarm_v10.CLIENTRECEIVER");
//        registerReceiver(clientReceiver,intentFilter);

        mContext = WorkerService.this.getApplicationContext();
        gpsLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        gpsLocation = gpsLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MINTIME,MININSTANCE,locationListener);
        gpsLocationManager.addGpsStatusListener(listener);

        return super.onStartCommand(intent,flags,startID);
    }

    public void Gps_init(){
        gpsLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        gpsLocation = gpsLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        gpsLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MINTIME,MININSTANCE,locationListener);
    }

    public void Network_init(){
        networkLocationManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
        networkLocation = networkLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        networkLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MINTIME,MININSTANCE,locationListener);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        if (gpsLocationManager!=null){
            //关闭程序时将监听器移除
            gpsLocationManager.removeUpdates(locationListener);
            gpsLocationManager.removeGpsStatusListener(listener);
        }
        if (networkLocationManager!=null){
            networkLocationManager.removeUpdates(locationListener);
        }
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //更新当前设备的位置信息
            showLocation(location);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    GpsStatus.Listener listener = new GpsStatus.Listener(){
        @Override
        public void onGpsStatusChanged(int event){
            switch (event)
            {
                case GpsStatus.GPS_EVENT_FIRST_FIX:break;
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    GpsStatus gpsStatus = gpsLocationManager.getGpsStatus(null);
                    Iterable<GpsSatellite> allgps = gpsStatus.getSatellites();
                    Iterator<GpsSatellite> items = allgps.iterator();

                    int count = 0;
                    int count_signal=0;
                    while (items.hasNext()){
                        GpsSatellite tmp = (GpsSatellite) items.next();
                        if (tmp.usedInFix())
                            count_signal++;
                        count++;
                    }
                    star_number = count_signal;
                    if (count_signal>4)
                    {
                        Gps_init();
                    }
                    else
                    {
                        Network_init();
                    }
                    break;
                case GpsStatus.GPS_EVENT_STARTED:break;
                case GpsStatus.GPS_EVENT_STOPPED:break;
            }
        }
    };

    private void showLocation(final Location location) {
        time = location.getTime();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        MyThread myThread = new MyThread();
        myThread.start();
    }

    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
//            lastMode = mode;
            mode = msg.what;
            if (mode!=lastMode)
                bIntent.putExtra("mode",mode);
            else
                bIntent.putExtra("mode",0);
            sendBroadcast(bIntent);
        }
    };

    public class MyThread extends Thread{
        @Override
        public void run(){
                try {
                    int i = 0;
//                    if (latitude!=-1)
                        i = acceptServer();
                    Message msg = new Message();
                    msg.what = i;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    private int acceptServer() throws IOException{
//        Socket socket = new Socket("192.168.90.17",9090);
//        Socket socket = new Socket("1622l81m38.51mypc.cn",26647);//此处为服务器地址+端口
        Socket socket = new Socket(Addr,port);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);


        dos.writeInt(0);//0000：工人------1111：火车
        dos.writeUTF(client_num);
        dos.writeLong(time);
        dos.writeDouble(longitude);
        dos.writeDouble(latitude);

        int num = 0;
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        num = dis.readInt();
        socket.shutdownOutput();
        socket.close();

        dos.close();
        dis.close();
        return num;
    }

    public class ClientReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        Bundle b = intent.getExtras();
//                        client_num = b.toString();
//                            client_num=intent.getExtras();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public class Sender extends Binder{
        public Sender send_mode(){
            return Sender.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return mBinder;
    }
}
