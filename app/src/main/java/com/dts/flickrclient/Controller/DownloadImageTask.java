package com.dts.flickrclient.Controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.dts.flickrclient.Model.Photo;

import java.io.InputStream;
import java.util.ArrayList;

public class DownloadImageTask extends AsyncTask<ArrayList<Photo>, Bitmap, ArrayList<Bitmap>> {
    Photo photoChange;
    private String TAG = "DownLoadImageTask";
    ArrayList<Bitmap> listBitmap;
    private CallbackIf callbackIf;

    public DownloadImageTask(CallbackIf callbackIf) {
        this.callbackIf = callbackIf;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listBitmap = new ArrayList<>();
        callbackIf.onStartLoading();
    }

    protected ArrayList<Bitmap> doInBackground(ArrayList... photos) {
        ArrayList<Photo> list = photos[0];

        for (int i = 0; i < list.size(); i++) {
            photoChange = list.get(i);
            String urldisplay = photoChange.getUrlS();
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                onProgressUpdate(mIcon11);
                listBitmap.add(mIcon11);
//                photoChange.setBitmap(mIcon11);
                Log.i(TAG, "Download Image successfully " + photoChange.getTitle());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
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