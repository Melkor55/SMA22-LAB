package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import model.MonthlyExpenses;

public class MainActivity extends AppCompatActivity {

    private TextView entries;
    private EditText incomeField,expensesField,monthField;
    private Button updateButton,searchButton;

    // firebase
    private DatabaseReference databaseReference;
    private ValueEventListener databaseListener;
    private String currentMonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entries = findViewById(R.id.entriesInfo);
        incomeField = findViewById(R.id.editTextIncome);
        expensesField = findViewById(R.id.editTextExpenses);
        monthField = findViewById(R.id.editTextMonth);
        updateButton = findViewById(R.id.buttonUpdate);
        searchButton = findViewById(R.id.buttonSearch);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!monthField.getText().toString().isEmpty())
                {
                    currentMonth = monthField.getText().toString().toLowerCase();

                    entries.setText("Searching ...");
                    createNewDBListener();
                }
                else
                {
                    Toast.makeText(MainActivity.this,"Search field may not be empty",Toast.LENGTH_SHORT).show();
                }
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentMonth = monthField.getText().toString().toLowerCase();
                if((!incomeField.getText().toString().isEmpty())&&(!expensesField.getText().toString().isEmpty())) {
                    databaseReference.child("calendar").child(currentMonth).child("income").setValue(Float.parseFloat(incomeField.getText().toString()), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Toast.makeText(MainActivity.this, "Data could not be updated. " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Data has been updated. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    databaseReference.child("calendar").child(currentMonth).child("expenses").setValue(Float.parseFloat(expensesField.getText().toString()), new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                            if (error != null) {
                                Toast.makeText(MainActivity.this, "Data could not be updated. " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.this, "Data has been updated. ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    entries.setText("Updating ...");
                }
                //createNewDBListener_UpdateDB();
            }
        });
    }

    private void createNewDBListener() {
        // remove previous databaseListener
        if (databaseReference != null && currentMonth != null && databaseListener != null)
            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);

        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MonthlyExpenses monthlyExpense = dataSnapshot.getValue(MonthlyExpenses.class);
                // explicit mapping of month name from entry key
                monthlyExpense.month = dataSnapshot.getKey();

                incomeField.setText(String.valueOf(monthlyExpense.getIncome()));
                expensesField.setText(String.valueOf(monthlyExpense.getExpenses()));
                entries.setText("Found entry for " + currentMonth.toString());
            }


            @Override
            public void onCancelled(DatabaseError error) {
            }
        };

        // set new databaseListener
        databaseReference.child("calendar").child(currentMonth).addValueEventListener(databaseListener);
    }

    private void createNewDBListener_UpdateDB() {
        // remove previous databaseListener
        if (databaseReference != null && currentMonth != null && databaseListener != null)
            databaseReference.child("calendar").child(currentMonth).removeEventListener(databaseListener);

        databaseListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                MonthlyExpenses monthlyExpense = dataSnapshot.getValue(MonthlyExpenses.class);
                // explicit mapping of month name from entry key
                monthlyExpense.month = dataSnapshot.getKey();

                monthlyExpense.setIncome(Float.parseFloat(incomeField.getText().toString()));
                monthlyExpense.setExpenses(Float.parseFloat(expensesField.getText().toString()));

                databaseReference.child("calendar").child(currentMonth).setValue(monthlyExpense);

                entries.setText("Updated entry for " + currentMonth.toString());
            }


            @Override
            public void onCancelled(DatabaseError error) {
            }
        };

        // set new databaseListener
        databaseReference.child("calendar").child(currentMonth).addValueEventListener(databaseListener);
    }
}