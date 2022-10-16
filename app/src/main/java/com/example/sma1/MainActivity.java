package com.example.sma1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nameField;
    Button button;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameField = findViewById(R.id.nameField);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.textView);

        AlertDialog.Builder alert = new AlertDialog.Builder(this,R.style.AlertDialogTheme2)
            //.setTitle("Delete entry")
            .setMessage("How are you today ?")

            // Specifying a listener allows you to take an action before dismissing the dialog.
            // The dialog is automatically dismissed when a dialog button is clicked.
            .setPositiveButton("Good", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Continue with delete operation
                }
            })

            // A null listener allows the button to dismiss the dialog and take no further action.
            .setNegativeButton("Bad", null)
            ;//.show();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name,message;
                name = String.valueOf(nameField.getText());
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
    }


}