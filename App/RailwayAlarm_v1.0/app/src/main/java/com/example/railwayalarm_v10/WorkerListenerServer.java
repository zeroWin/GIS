package com.example.railwayalarm_v10;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;

public class WorkerListenerServer extends Service {
    public WorkerListenerServer() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startID){

        flags = START_STICKY;
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);

//        int Minutes = 1*10*6000;
//        long keetTime = SystemClock.elapsedRealtime()+Minutes;
        long firstTime = SystemClock.elapsedRealtime();
        firstTime += 30*60*1000;

        Intent intent1 = new Intent(this,WorkerReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(this,0,intent1,0);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,keetTime,pi);
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,firstTime,30*60*1000,pi);

        return super.onStartCommand(intent,flags,startID);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
