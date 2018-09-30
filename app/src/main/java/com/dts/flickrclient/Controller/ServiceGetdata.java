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
import com.dts.flickrclient.Presenter.Notic;
import com.dts.flickrclient.Presenter.PhotoGallery;

import java.util.ArrayList;

public class ServiceGetdata extends IntentService  implements CallbackIf, Constant{
    private String TAG = "Service Getdata";
    private ArrayList oldList;
    private ArrayList<Bitmap> bitmapList  = new ArrayList<>();
    private ArrayList<Photo> dataList  = new ArrayList<>();
    public ServiceGetdata() {
        super("ServiceGetdata");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i(TAG,"On handele Intent" );
        oldList = new ArrayList();


        Bundle bundle = intent.getExtras();
        oldList = bundle.getParcelableArrayList("oldList");
        new LoadDataAsynTask(this).execute(PhotoGallery.buildUrl(METHOD,null, null, null));
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
            Log.i(TAG, "Start parse Json");
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

            Notic notic = new Notic(this, arrayList);
            notic.showNotic("Flickr Client", "new Image");

        }
    }

    private void equalsData(ArrayList oldList, ArrayList<Photo> dataList) {
        for (int i = 0; i < oldList.size(); i++) {
            int j = 0;
            while (dataList.size() > 0 && j < dataList.size()) {
                if (oldList.get(i).equals(dataList.get(j))) {
                    Log.i(TAG, "Data " + j + "list data is aready and remove it.");
                    dataList.remove(j);
                }
                j++;
            }
        }
    }


}
