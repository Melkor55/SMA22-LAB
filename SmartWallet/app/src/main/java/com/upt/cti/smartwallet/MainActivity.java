package com.upt.cti.smartwallet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import model.MonthlyExpenses;

public class MainActivity extends AppCompatActivity{

    private TextView entries;
    private EditText incomeField,expensesField,monthField;
    private Button updateButton,searchButton;

    // firebase
    private DatabaseReference databaseReference;
    private ValueEventListener databaseListener;
    private String currentMonth;

    Spinner spinner;
    //String[] months ;
    ArrayList<String> months = new ArrayList<>();


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

        getdata();
        System.out.println("********");
        System.out.println(months);
        System.out.println("********");
        //printMonths();

        ////
        spinner = (Spinner) findViewById(R.id.spinner);
        //spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        ArrayList<String> test = new ArrayList<>();
        test.add("1");
        test.add("2");

        //Creating the ArrayAdapter instance having the collor list



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

                if (Objects.equals(currentMonth, ""))
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
/*
    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        System.out.println("---------------Something selected !\n");
        //button = findViewById(R.id.button);
        //button.setBackgroundColor(getResources().getColor(map.get(months.get(position))));
        currentMonth = months.get(position);//arg0.getItemAtPosition(position).toString().toLowerCase();
        createNewDBListener();
        Toast.makeText(getApplicationContext(), months.get(position) , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO Auto-generated method stub
        System.out.println("---------------Nothing selected !\n");
    }
    */
/*
    private void printMonths(){
        System.out.println("********");
        for (int i = 0 ; i < months.length ; i ++)
        {
            System.out.println(months[i]);
        }
        System.out.println("********");
    }
*/
    private void getdata() {

        // calling add value event listener method
        // for getting the values from database.
        //databaseReference.addValueEventListener(new ValueEventListener() {
        //databaseListener = new ValueEventListener() {
        databaseReference.child("calendar")./*child("february").*/addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // this method is call to get the realtime
                // updates in the data.
                // this method is called when the data is
                // changed in our Firebase console.
                // below line is for getting the data from
                // snapshot of our database.

                //String value = snapshot.getValue(String.class);
                //String value = dataSnapshot.getKey();
                int i = 1;
                if(dataSnapshot.getChildrenCount() != months.size())
                {
                    months.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        //months[i++] = child.getKey();
                        months.add((String) child.getKey());
                        System.out.println(months);
                        System.out.println(child.getKey());
                    }
                }
                putDataToSpinner();
                //System.out.println("********");
                    //System.out.println(value.toString());
                //System.out.println("********");

                // after getting the value we are setting
                // our value to our text view in below line.
                //retrieveTV.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // calling on cancelled method when we receive
                // any error or we are not able to get the data.
                Toast.makeText(MainActivity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void putDataToSpinner(){
        /// spinner
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,months);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);
        ////
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                currentMonth = months.get(position);//arg0.getItemAtPosition(position).toString().toLowerCase();
                createNewDBListener();
                Toast.makeText(getApplicationContext(), months.get(position) , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
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