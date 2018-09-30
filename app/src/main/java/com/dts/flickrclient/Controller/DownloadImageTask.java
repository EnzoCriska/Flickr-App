package com.dts.flickrclient.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.dts.flickrclient.Model.BitmapCache;
import com.dts.flickrclient.Model.Photo;

import java.io.InputStream;
import java.util.ArrayList;

public class DownloadImageTask extends AsyncTask<ArrayList<Photo>, Bitmap, ArrayList<Bitmap>> {
    private Photo photoChange;
    private String TAG = "DownLoadImageTask";
    private ArrayList<Bitmap> listBitmap;
    private CallbackIf callbackIf;
    private BitmapCache cache;

    public DownloadImageTask(CallbackIf callbackIf) {
        this.callbackIf = callbackIf;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listBitmap = new ArrayList<>();
        cache = new BitmapCache(100);
        callbackIf.onStartLoading();
    }

    protected ArrayList<Bitmap> doInBackground(ArrayList... photos) {
        ArrayList<Photo> list = photos[0];

        for (int i = 0; i < list.size(); i++) {
            Bitmap mIcon11 = null;
            photoChange = list.get(i);
            String urldisplay = photoChange.getUrlS();
            if (cache.hasBitmap(urldisplay)){
                mIcon11 = cache.getBitmap(urldisplay);
                Log.i(TAG, "Image in cache" + photoChange.getTitle());
            }else{
                try {
                    InputStream in = new java.net.URL(urldisplay).openStream();
                    mIcon11 = BitmapFactory.decodeStream(in);
//                    Log.i(TAG, "Download Image successfully " + photoChange.getTitle());
                    cache.setBitmap(urldisplay, mIcon11);
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    e.printStackTrace();
                }
            }
                onProgressUpdate(mIcon11);
                listBitmap.add(mIcon11);
//                photoChange.setBitmap(mIcon11);
        }

        return listBitmap;
    }

    @Override
    protected void onPostExecute(ArrayList<Bitmap> aVoid) {
        super.onPostExecute(aVoid);
        Log.i(TAG, "Download done!!!");
        callbackIf.onSucess(null, aVoid);
        callbackIf.onComplite(aVoid);
    }
}