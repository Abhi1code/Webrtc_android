package com.socket.webrtc;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ServiceManager extends BroadcastReceiver {

    Context mContext;
    private final String BOOT_ACTION = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // All registered broadcasts are received by this
        Toast.makeText(context, "System Booted", Toast.LENGTH_SHORT).show();
        Intent mServiceIntent = new Intent(context, LocationService.class);
        context.startService(mServiceIntent);
        Intent act = new Intent(context, Login.class);
        context.startActivity(act);
    }


    private void startService() {
        //here, you will start your service
        Intent mServiceIntent = new Intent();
        mServiceIntent.setAction("com.bootservice.test.DataService");
        mContext.startService(mServiceIntent);
    }
}
