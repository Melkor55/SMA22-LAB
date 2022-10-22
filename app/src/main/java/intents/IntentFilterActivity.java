package intents;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.example.sma1.R;

public class IntentFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_filter);

        TextView textView = (TextView) findViewById(R.id.textView);
        Uri uri = getIntent().getData();
        if ( uri != null)
              textView.setText(uri.toString());
    }
}