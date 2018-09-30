package com.dts.flickrclient.Presenter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.dts.flickrclient.Controller.NewData;

public class ReceiverDataFromService extends BroadcastReceiver{
    private NewData newData;
    private String TAG = "RECEIVER";

    public  ReceiverDataFromService(NewData mnewData){
        this.newData = mnewData;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, intent.getStringExtra("Test") + "intent is success.");


        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            newData.startServiceWithSys();
        }

    }
}
