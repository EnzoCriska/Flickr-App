package com.dts.flickrclient.Controller;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dts.flickrclient.Model.Constant;
import com.dts.flickrclient.Model.JsonPares;
import com.dts.flickrclient.Model.Photo;
import com.dts.flickrclient.Presenter.PhotoGallery;

import java.util.ArrayList;

public class ServiceGetdata extends IntentService  implements CallbackIf, Constant{
    private String TAG = "Service Getdata";
    private ArrayList oldList = new ArrayList();
    private ArrayList<Bitmap> bitmapList  = new ArrayList<>();
    private ArrayList<Photo> dataList  = new ArrayList<>();
    public ServiceGetdata() {
        super("ServiceGetdata");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG,"On handele Intent" );
        String str = intent.getStringExtra("Hello");
        Log.i(TAG, "String test "+ str);
        Bundle bundle = intent.getExtras();
        oldList = bundle.getParcelableArrayList("oldList");
        new LoadDataAsynTask(this).execute(PhotoGallery.buildUrl(METHOD,null));
    }

    @Override
    public void onStartLoading() {
        Log.i(TAG, "on start Loading rêpad...");
    }

    @Override
    public void onSucess(String s, ArrayList list) {
        Log.i(TAG, "on Success Loading repead...");
        if (list != null){
            bitmapList = list;
            Log.i(TAG, "Dowload new bitmap..");
            Toast.makeText(this, "DOWNLOADING SUCESSFULY! "+ bitmapList.size(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "LOADING SUCESSFULY!", Toast.LENGTH_SHORT).show();
            dataList = JsonPares.parseJson(s);
            equalsData(oldList, dataList);
        }
    }



    @Override
    public void onComplite(ArrayList arrayList) {
        if (arrayList == null) {
            new DownloadImageTask(this).execute(dataList);
        }else{
            //Gửi ảnh đến activity
            Log.i(TAG, "On Complite Dowload Bitmap, send to Acitvity...");
            Intent intent = new Intent(this, PhotoGallery.class);
            intent.setAction("GET_NEW_BITMAP_IMAGE");
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("ListBitmap", bitmapList);
            intent.putExtras(bundle);
            sendBroadcast(intent);
        }
    }

    private void equalsData(ArrayList oldList, ArrayList<Photo> dataList) {
        for (int i = 0; i < oldList.size(); i++) {
            int j = 0;
            while (dataList.size() > 0 || j < dataList.size()) {
                if (oldList.get(i).equals(dataList.get(j))) {
                    Log.i(TAG, "Data " + j + "list data is aready and remove it.");
                    dataList.remove(j);
                }
            }
        }
    }


}
