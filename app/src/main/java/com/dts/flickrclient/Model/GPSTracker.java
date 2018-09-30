
package com.dts.flickrclient.Model;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;


public final class GPSTracker implements LocationListener {
    private final static int MIN_TIME = 1500;
    private final static int MIN_DISTANCE = 1;
    private Context mContext;
    private LocationManager mLocationManager;
    private boolean canGetLocation = false;
    private String mLat;
    private String mLon;

    public GPSTracker(Context context) {
        mContext = context;
        creat();
    }

    public boolean isCanGetLocation() {
        return canGetLocation;
    }

    public String getLat() {
        return mLat;
    }

    public String getLon() {
        return mLon;
    }

    private void creat() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        getLocation();
    }

    private void getLocation() {
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

    // Lấy thông tin tọa độ thông qua cảm biến LocationProvider (GPS/Network)
    private void getLocationInfo(String provider) {
        try {
            // Đăng ký cập nhật tọa độ
            mLocationManager.requestLocationUpdates(provider, MIN_TIME, MIN_DISTANCE, this);

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

    public void cancleLocation() {
        try {
            mLocationManager.removeUpdates(this); // Hủy cập nhật tọa độ
        } catch (SecurityException secE) {
            // Toast.makeText(mContext, "Location isn't avaiable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        mLat = String.valueOf(location.getLatitude());
        mLon = String.valueOf(location.getLongitude());
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
