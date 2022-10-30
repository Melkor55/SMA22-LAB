package com.example.sma1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DisplayChargingStatus extends AppCompatActivity {

    Button button,batteryStatusButton ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_charging_status);

        button = findViewById(R.id.button);
        batteryStatusButton = findViewById(R.id.batteryStatusButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayChargingStatus.this, MainActivity.class);
                startActivity(intent);
            }
        });

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null,intentFilter);

        batteryStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;

                int chargePlug = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
                boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;

                String chargingStatus, chargingType;

                if (isCharging)
                    chargingStatus = "battery is charging";
                else
                    chargingStatus = "battery is not charging";

                if (usbCharge)
                    chargingType = "connected to USB";
                else if (acCharge)
                    chargingType = "connected to AC";
                else
                    chargingType = "not connected";


                Intent newIntent = new Intent(DisplayChargingStatus.this, MainActivity.class);
                newIntent.putExtra("status",status);
                PendingIntent pendingIntent = PendingIntent.getActivity(DisplayChargingStatus.this, 0, newIntent, 0);

                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(DisplayChargingStatus.this, MainActivity.CHANNEL_ID)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Charging status :")
                        .setContentText(chargingStatus).
                        setSubText(chargingType)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(DisplayChargingStatus.this);

                notificationManager.notify(MainActivity.notificationId, mBuilder.build());
            }
        });



    }
}