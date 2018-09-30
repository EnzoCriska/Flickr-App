package com.dts.flickrclient.Presenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.dts.flickrclient.Model.Photo;
import com.dts.flickrclient.R;

public class Image_screen_full extends AppCompatActivity {

    private String TAG = "Image_screen_full";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "Full creen...");
        setContentView(R.layout.full_screen_image);
        ImageView image = findViewById(R.id.fullImage);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Bundle");
        Photo photo = bundle.getParcelable("PhotoFull");
        Bitmap bitmap = bundle.getParcelable("Bitmap");
        Bitmap.createScaledBitmap(bitmap, Integer.parseInt(photo.getWidthS()), Integer.parseInt(photo.getHeightS()), true);
        image.setImageBitmap(bitmap);

    }
}
