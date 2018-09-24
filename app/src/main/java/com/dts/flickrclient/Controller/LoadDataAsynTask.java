package com.dts.flickrclient.Controller;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class LoadDataAsynTask extends AsyncTask<String, String, String> {
    private CallbackIf mCallback;

    public LoadDataAsynTask(CallbackIf mCallback) {
        this.mCallback = mCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mCallback.onStartLoading();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            connection.setSSLSocketFactory(socketFactory);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            int resCode = connection.getResponseCode();
            if (resCode == 200) {
                String response = "";
                InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                response = StringUtils.convertStreamToString(inputStream);

                return response;
            } else {

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (mCallback == null) {
            return;
        } else {
            mCallback.onSucess(s, null);
            mCallback.onComplite(null);
        }
    }

}
