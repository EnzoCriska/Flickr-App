package com.dts.flickrclient.Presenter;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.dts.flickrclient.Controller.CallbackIf;
import com.dts.flickrclient.Controller.DownloadImageTask;
import com.dts.flickrclient.Controller.LoadDataAsynTask;
import com.dts.flickrclient.Controller.NewData;
import com.dts.flickrclient.Controller.ServiceGetdata;
import com.dts.flickrclient.Model.Constant;
import com.dts.flickrclient.Model.GPSTracker;
import com.dts.flickrclient.Model.JsonPares;
import com.dts.flickrclient.Model.Photo;
import com.dts.flickrclient.R;

import java.util.ArrayList;

public class PhotoGallery extends AppCompatActivity implements CallbackIf, Constant, NewData, ShowFullitf{
    private ArrayList<Photo> list;
    private ArrayList<Bitmap> listBitmap;
    private RecyclerView recyclerView;
    private Switch aSwitch;
    private AdapterRecycle adapter;
    private ProgressDialog progress;
    private ReceiverDataFromService receiverDataFromService;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
    private boolean startwithDevice = false;
    private SharedPreferences pre;

    private String TAG = "Photo Gallery";

    private static final Uri ENDPOINT = Uri
            .parse("https://api.flickr.com/services/rest/")
            .buildUpon()
            .appendQueryParameter("api_key", "cfcdde9577bce83d20b23d02bcd939a5")
            .appendQueryParameter("format", "json")
            .appendQueryParameter("nojsoncallback", "1")
            .appendQueryParameter("extras", "url_s")
            .build();
    String URL1 = "https://vnexpress.net/";

    public PhotoGallery() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_gallery);
        init();
        Intent intent = getIntent();
        Log.i(TAG, "New Data  " + intent.getStringExtra("hello"));
        pre=getSharedPreferences ("status",MODE_PRIVATE);
        startwithDevice = pre.getBoolean("startWithSystem", false);
        Log.i(TAG, "status is " + startwithDevice);
        if (startwithDevice){
            aSwitch.setChecked(true);

            this.registerReceiver(receiverDataFromService, new IntentFilter("android.intent.action.BOOT_COMPLETED"));
            Log.w("Register", "RegisterBroadcast");
        }else{
            aSwitch.setChecked(false);
            this.unregisterReceiver(receiverDataFromService);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (list.size() == 0) new LoadDataAsynTask(this).execute(buildUrl(METHOD,null, null, null));
    }

    public void init() {
//        requestLocationPermissions();
        list = new ArrayList<>();
        listBitmap = new ArrayList<>();
        recyclerView = findViewById(R.id.recycler);
        aSwitch = findViewById(R.id.runbackground);
        receiverDataFromService = new ReceiverDataFromService(this);
        adapter = new AdapterRecycle(this);
        progress = new ProgressDialog(this);

//        getPhotos();
        aSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pre =getSharedPreferences("status", MODE_PRIVATE);
                SharedPreferences.Editor edit=pre.edit();
                edit.putBoolean("startWithSystem", aSwitch.isChecked());
                edit.commit();
                Log.i(TAG, "Save status...");
            }
        });
    }

    private void requestLocationPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    requestLocationPermissions();
                }
                break;
            default:
                break;
        }
    }

    public static String buildUrl(String method, String query, String lat, String lon) {
        Uri.Builder uriBuilder = ENDPOINT.buildUpon()
                .appendQueryParameter("method", method);
        if (method.equals(SEACH_METHOD)) {
            if (query != null){
                uriBuilder.appendQueryParameter("text", query);
            }else{
                uriBuilder.appendQueryParameter("lat", lat);
                uriBuilder.appendQueryParameter("lon", lon);
            }
        }
        return uriBuilder.build().toString();
    }

    public void recyclerUp(){
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        adapter.setiData(new AdapterRecycle.IData() {


            @Override
            public int getCount() {
                return listBitmap.size();
            }

            @Override
            public Bitmap getItem(int pos) {
                return listBitmap.get(pos);
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStartLoading() {
        progress.show();
    }

    @Override
    public void onSucess(String s, ArrayList list1) {
        if (s == null){
            listBitmap = list1;
            Toast.makeText(this, "DOWNLOADING SUCESSFULY! "+ listBitmap.size(), Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "LOADING SUCESSFULY!", Toast.LENGTH_SHORT).show();
            list = JsonPares.parseJson(s);
        }
    }

    @Override
    public void onComplite(ArrayList arrayList) {
        progress.dismiss();
        if (arrayList == null) {
            new DownloadImageTask(this).execute(list);
        }else{

            recyclerUp();
            adapter.notifyDataSetChanged();
        }
        alarmMgr = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        startService();
        Log.i(TAG, "get Service");

        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1000*60,
                1000 * 60*5, alarmIntent);
    }

    public void startService(){
        Bundle bundle = new Bundle();

        bundle.putParcelableArrayList("oldList",list);
        Intent intent = new Intent(this, ServiceGetdata.class);
        intent.putExtras(bundle);
        intent.putExtra("Hello", "hi....");
        alarmIntent = PendingIntent.getService(this, 0, intent, 0);
    }

    @Override
    public void update(ArrayList list1) {

        list1.addAll(listBitmap);
        listBitmap.clear();
        listBitmap.addAll(list1);
//        adapter.notifyDataSetChanged();
    }

    @Override
    public void startServiceWithSys() {
        Toast.makeText(this, "Reboot...", Toast.LENGTH_LONG).show();
        startService();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_bar, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.searchMenu).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Toast.makeText(PhotoGallery.this, "SEARCHING>>> " + s, Toast.LENGTH_SHORT).show();
                new LoadDataAsynTask(PhotoGallery.this).execute(buildUrl(SEACH_METHOD,s, null, null));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(Log.i(TAG, "SEARCH with :" + s));
                return false;
            }
        });
        MenuItem view = menu.findItem(R.id.renew);
        view.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
//                new LoadDataAsynTask(PhotoGallery.this).execute(buildUrl(METHOD,null, null, null));
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        MenuItem locationfind = menu.findItem(R.id.findLocation);
        locationfind.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                GPSTracker locationDevice = new GPSTracker(PhotoGallery.this);
                String lat = locationDevice.getLat();
                String lon = locationDevice.getLon();
                String url = buildUrl(SEACH_METHOD,null, String.valueOf(lat), String.valueOf(lon));
                new LoadDataAsynTask(PhotoGallery.this).execute(url);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void showfull(int pos) {
        Intent intent = new Intent(PhotoGallery.this, Image_screen_full.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("PhotoFull", list.get(pos));
        bundle.putParcelable("Bitmap", listBitmap.get(pos));
        intent.putExtra("Bundle", bundle);
        startActivity(intent);
    }
}
