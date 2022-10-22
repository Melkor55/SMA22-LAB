package com.example.sma1;

import android.app.IntentService;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ImageIntentService extends IntentService {
//    public ImageIntentService(String name) {
//        super(name);
//    }

    public ImageIntentService() {
        super("ImageIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String param = intent.getStringExtra(WebsearchActivity.EXTRA_URL);
            handleDownloadAction(param);
        }
    }

    private void handleDownloadAction(String url) {
        // start task on separate thread
        //new DownloadImageTask().execute(url);
        //.execute("https://news.nationalgeographic.com/content/dam/news/2018/05/17/you-can-train-your-cat/02-cat-training-NationalGeographic_1484324.ngsversion.1526587209178.adapt.1900.1.jpg");

        try {
            String longURL = URLTools.getLongUrl(url);
            Bitmap bmp = null;
            try {
                InputStream in = new URL(longURL).openStream();
                bmp = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }

            // simulate longer job ...
            Thread.sleep(5000);

            ((MyApplication) getApplicationContext()).setBitmap(bmp);
            // start second activity to show result
            Intent intent = new Intent(getApplicationContext(), ImageActivity.class);
            startActivity(intent);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
