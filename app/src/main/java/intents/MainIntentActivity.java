package intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sma1.MainActivity;
import com.example.sma1.R;

import lifecycle.ActivityA;

public class MainIntentActivity extends AppCompatActivity {

    Button button1,button2,button3,button4 ;
    Button go_to_intent_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_intent);

        button1 =findViewById( R.id.button1 );
        button2 =findViewById( R.id.button2 );
        button3 =findViewById( R.id.button3 );
        button4 =findViewById( R.id.button4 );
        go_to_intent_filter =findViewById( R.id.go_to_filter );

        go_to_intent_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainIntentActivity.this,IntentFilterActivity.class);
                startActivity(intent);
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(intent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:00401213456"));
                startActivity(intent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("MSA.LAUNCH", Uri.parse("https://www.google.com"));
                startActivity(intent);
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("MSA.LAUNCH", Uri.parse("tel:00401213456"));
                startActivity(intent);
            }
        });
    }
}