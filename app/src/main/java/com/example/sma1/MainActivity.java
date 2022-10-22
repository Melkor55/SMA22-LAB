package com.example.sma1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    EditText nameField;
    Button button;
    FloatingActionButton bSearch, bShare;
    TextView textView;
    Spinner spinner;
    String[] collors = { "red", "teal", "black", "green"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.nameField);
        button = findViewById(R.id.button);
        bSearch = findViewById(R.id.bSearch);
        bShare = findViewById(R.id.bShare);
        textView = findViewById(R.id.textView);
        spinner = (Spinner) findViewById(R.id.spinner);


        spinner.setOnItemSelectedListener((AdapterView.OnItemSelectedListener) this);

        //Creating the ArrayAdapter instance having the collor list
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,collors);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);


        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.AlertDialogTheme2)
            //.setTitle("Delete entry")
            .setMessage("How are you today ?")

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Good", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"I'm happy to hear that",Toast.LENGTH_SHORT).show();
                }
            })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("Bad", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(getApplicationContext(),"I'm sorry to hear that",Toast.LENGTH_SHORT).show();
                }
            })
            ;//.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String message;
                String name = String.valueOf(nameField.getText());
                message = "Hello " + name + " !";
                if (!name.equals(""))
                {
                    textView.setText(message);
                    alert.setTitle(message).show();
//                    alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(neededColor);
//                    alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(neededColor);
                }
                else
                {
                    String errorMessage = "You have not entered a name in the text box !!!";
                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                }


            }
        });

        bShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = String.valueOf(nameField.getText());

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,name);

                // Create intent to show chooser
                Intent chooser = Intent.createChooser(intent, "Share ");

                // Try to invoke the intent.
                try {
                    startActivity(chooser);
                } catch (ActivityNotFoundException e) {
                    System.out.println("No app to play with");
                    // Define what your app should do if no activity can handle the intent.
                }

//                if (intent.resolveActivity(getPackageManager()) != null) {
//                    startActivity(chooser);
//                }

            }
        });

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                String name = String.valueOf(nameField.getText());

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);

                if (!name.equals(""))
                {
                    intent.setData(Uri.parse("https://www.google.com/search?q=" + name));
                    startActivity(intent);
                }
                else
                {
                    String errorMessage = "You have not entered a name in the text box !!!";
                    Toast.makeText(getApplicationContext(),errorMessage,Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //Performing action onItemSelected and onNothing selected
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id)
    {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("red", R.color.red);
        map.put("teal",R.color.teal_200);
        map.put("green",R.color.green);
        map.put("black",R.color.black);

        button = findViewById(R.id.button);
        button.setBackgroundColor(getResources().getColor(map.get(collors[position])));
        Toast.makeText(getApplicationContext(),collors[position] , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO Auto-generated method stub
    }


}