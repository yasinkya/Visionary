package com.example.filmonerim;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Firebase reference definition
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();

        // in activity fetch username,pass, button login
        EditText userName=(EditText)findViewById(R.id.userName),
                 pass=(EditText)findViewById(R.id.pass);
        RelativeLayout login = (RelativeLayout)findViewById(R.id.btnLog);

        //Login Button click event
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uName=userName.getText().toString(),
                        psw=pass.getText().toString();
                if(uName.equals("")) // cant empty
                    Toast.makeText(LoginActivity.this,"insert username",Toast.LENGTH_SHORT).show();
                else if(psw.equals(""))// cant empty
                    Toast.makeText(LoginActivity.this,"insert pass",Toast.LENGTH_SHORT).show();
                else {// check in database
                    User user= new User(uName,psw);
                    Toast.makeText(LoginActivity.this, user.UserName, Toast.LENGTH_SHORT).show();
                    database.child("users").child("1").setValue(user);
                }

            }
        });
    }
}