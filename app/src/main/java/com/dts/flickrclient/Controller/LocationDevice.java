package com.dts.flickrclient.Controller;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationDevice implements LocationListener{
    private Context context;
    double lat, lon;
    private LocationManager mLocationManager;
    private LocationListener locationListener;
    private String TAG = "LocationDevice";
    private boolean canGetLocation = false;

    public LocationDevice(Context context) {

        this.context = context;
        creat();
    }

    private void creat() {
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = mLocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = mLocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnabled && !isNetworkEnabled) {
            canGetLocation = false;
        } else if (isNetworkEnabled && !isGPSEnabled) {
            canGetLocation = true;
            getLocationInfo(LocationManager.NETWORK_PROVIDER);
        } else {
            canGetLocation = true;
            getLocationInfo(LocationManager.GPS_PROVIDER);
        }

    }


    private void getLocationInfo(String provider) {
        try {
            // Đăng ký cập nhật tọa độ
            mLocationManager.requestLocationUpdates(provider, 1, 1000, this);

            // Khởi tạo đối tượng Location lưu trữ thông tin vị trí tọa độ
            Location location = mLocationManager.getLastKnownLocation(provider);
            if (location != null) {
                onLocationChanged(location);
            } else {
                // Toast.makeText(mContext, "Location is NULL", Toast.LENGTH_SHORT).show();
            }
        } catch (SecurityException secE) {
            // Toast.makeText(mContext, "Location isn't avaiable", Toast.LENGTH_SHORT).show();
        }
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }


    @Override
    public void onLocationChanged(Location location) {
        Log.i(TAG, "Location: " + location.toString());
        lat = location.getLatitude();
        lon = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}