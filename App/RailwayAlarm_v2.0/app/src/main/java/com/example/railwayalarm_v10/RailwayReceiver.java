package com.example.railwayalarm_v10;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.Vibrator;
import android.widget.Toast;

import static android.Manifest.permission.VIBRATE;

public class RailwayReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        Intent intent1 = new Intent(context,RailwayService.class);
        context.startService(intent1);
//        throw new UnsupportedOperationException("Not yet implemented");
    }
}
