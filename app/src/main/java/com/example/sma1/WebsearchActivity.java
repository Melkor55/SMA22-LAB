package com.example.sma1;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuPresenter;

public class WebsearchActivity extends AppCompatActivity {


    public static String EXTRA_URL = "";
    private MenuPresenter view;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_websearch);

        // simple way to extract clipboard data (string)
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = clipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        String url = item.getText().toString();

        // check url for validity
        if (!url.contains("https://goo.gl/images/"))
            Toast.makeText(this, "URL not valid. Try another.", Toast.LENGTH_SHORT).show();
        else {
            // start background service to download in background
            if (view.getId() == R.id.load_background) {
                Intent intent = new Intent(this, ImageIntentService.class);
                intent.putExtra(EXTRA_URL, url);
                startService(intent);
            }
            // start foreground service to download with notification
            else if (view.getId() == R.id.load_foreground) {
                Intent startIntent = new Intent(this, ForegroundImageService.class);
                startIntent.setAction(ForegroundImageService.STARTFOREGROUND_ACTION);
                startIntent.putExtra(EXTRA_URL, url);
                startService(startIntent);
            }
        }
    }


}