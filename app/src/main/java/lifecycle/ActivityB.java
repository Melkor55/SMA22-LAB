package lifecycle;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.sma1.MainActivity;
import com.example.sma1.R;

public class ActivityB extends AppCompatActivity {

    Button go_to_A, go_to_B, go_to_C ;
    Button go_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        Log.d(TAG, "onCreate B");

        go_to_A = findViewById(R.id.go_to_A);
        go_to_B = findViewById(R.id.go_to_B);
        go_to_C = findViewById(R.id.go_to_C);
        go_home = findViewById(R.id.go_home);

        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActivityB.this, MainActivity.class);
                startActivity(intent);
            }
        });

        go_to_A.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( ActivityB.this, ActivityA.class);
                startActivity(intent);
            }
        });

        go_to_B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityB.this, ActivityB.class);
                startActivity(intent);
            }
        });

        go_to_C.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityB.this, ActivityC.class);
                startActivity(intent);
            }
        });
    }
}