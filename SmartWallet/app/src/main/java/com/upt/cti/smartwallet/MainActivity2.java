package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import model.Payment;
import ui.PaymentAdapter;

public class MainActivity2 extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private List<Payment> payments = new ArrayList<>();
    private TextView status;
    private Button nextButton, prevButton;
    private FloatingActionButton addButton;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        status = (TextView) findViewById(R.id.status);
        prevButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        addButton = (FloatingActionButton) findViewById(R.id.addButton);
        listView = (ListView) findViewById(R.id.listView);

        PaymentAdapter adaptor = new PaymentAdapter(this,R.layout.item_payment, payments);
        listView.setAdapter(adaptor);


        databaseReference = FirebaseDatabase.getInstance().getReference("wallet");

        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    Payment payment = dataSnapshot.getValue(Payment.class);
                    System.out.println(payment);
                    payments.add(payment);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity2.this, AddPayment.class));
            }
        });
    }


}
