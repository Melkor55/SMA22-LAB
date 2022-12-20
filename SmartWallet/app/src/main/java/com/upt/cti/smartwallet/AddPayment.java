package com.upt.cti.smartwallet;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

import model.Payment;

public class AddPayment extends AppCompatActivity {

    private String paymentType;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_payment);

        EditText nameField = (EditText) findViewById(R.id.nameField);
        EditText costField = (EditText) findViewById(R.id.costField);
        Button saveButton = (Button) findViewById(R.id.saveButton);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.paymentTypes, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                paymentType = parentView.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        databaseReference = FirebaseDatabase.getInstance().getReference();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference = FirebaseDatabase.getInstance().getReference().child("wallet");
                Payment payment = new Payment(getCurrentTime(),Double.parseDouble(costField.getText().toString()), nameField.getText().toString(), paymentType);
                databaseReference.push().setValue(payment);
                Toast.makeText(getApplicationContext(), "payment added" , Toast.LENGTH_LONG).show();
            }
        });
    }

    public static String getCurrentTime()
    {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-dd:HH:mm:ss");
        Date date = new Date();
        return simpleDateFormat.format(date);
    }
}