package com.example.railwayalarm_v10;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;

import android.location.Criteria;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.os.Vibrator;
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


public class RailwayService extends Service {

    private LocationManager locationManager;
    private LocationManager gpsLocationManager;
    private LocationManager networkLocationManager;
    private Location location;
    private Location gpsLocation;
    private Location networkLocation;

    private long gpsTime;
    private double gpsLatitude;
    private double gpsLongitude;

    private long networkTime;
    private double networkLatitude;
    private double networkLongitude;

    private long time;
    private double latitude = -1;
    private double longitude;

    private static final int MINTIME = 1000;
    private static final int MININSTANCE = 1;
    private static Context mContext;
    private  int star_number;
    private static String client_num ;

    private String AlarmInfo = "安全";
    private Notification notification;
    private int last_loaction;

    public RailwayService _instance;


    public RailwayService(){

    }

    @Override
    public void onCreate(){
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){

        flags = START_STICKY;

        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle("铁路预警")
                .setContentText("火车")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher))
                .build();

        notification.flags = Notification.FLAG_SHOW_LIGHTS;
        startForeground(1,notification);


        if (client_num==null){
            Object number = intent.getExtras().get("input");
            Toast.makeText(this,String.valueOf(number),Toast.LENGTH_LONG).show();
            client_num = (String.valueOf(number));
        }


        mContext = RailwayService.this.getApplicationContext();
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
//            time =location.getTime();
//            latitude = location.getLatitude();
//            longitude = location.getLongitude();
//            MyThread myThread = new MyThread();
//            myThread.start();
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


    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            super.handleMessage(msg);
        }
    };

    public void showLocation(final Location location){
        time = location.getTime();
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        MyThread myThread = new MyThread();
        myThread.start();
    }
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
        //Socket socket = new Socket("39.108.69.200",9090);
        Socket socket = new Socket("172.27.35.1",9090);
        OutputStream os = socket.getOutputStream();
        DataOutputStream dos = new DataOutputStream(os);

        dos.writeInt(1);//0000：工人------1111：火车
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

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
