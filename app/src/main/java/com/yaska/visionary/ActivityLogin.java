package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yaska.visionary.database.FirebaseCallback;
import com.yaska.visionary.database.UserDB;

import java.util.Objects;

public class ActivityLogin extends AppCompatActivity {

    boolean signupclicked;
    UserDB userdataBase = new UserDB();
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // -------- Set Full Screen
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Objects.requireNonNull(getSupportActionBar()).hide();


        LinearLayoutCompat laysignup = findViewById(R.id.lay_signup);
        laysignup.setVisibility(View.GONE);

        EditText et_username = findViewById(R.id.et_username);
        EditText et_password = findViewById(R.id.et_pass);
        EditText et_name = findViewById(R.id.et_name);
        EditText et_surname = findViewById(R.id.et_surname);
        EditText et_mail = findViewById(R.id.et_mail);

        CardView btn_login = findViewById(R.id.btn_login);
        TextView logintext = findViewById(R.id.et_login);
        TextView btn_signup = findViewById(R.id.btn_signup);
        TextView btn_forgot = findViewById(R.id.btn_forgotpass);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = et_username.getText().toString();
                String password = et_password.getText().toString();

                if (signupclicked){
                    Toast.makeText(ActivityLogin.this, "kayıt ol", Toast.LENGTH_SHORT).show();

                    String name = et_name.getText().toString();
                    String surname = et_surname.getText().toString();
                    String mail = et_mail.getText().toString();

                    if(username.equals("") || password.equals("") || name.equals("") || surname.equals("") || mail.equals("")){
                        Toast.makeText(ActivityLogin.this, "Bilgileri Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        Toast.makeText(ActivityLogin.this, "kayıt olunuyo", Toast.LENGTH_SHORT).show();
                        userdataBase.new_user(new User(name, surname, mail, password), username);
                    }

                }
                else{
                    if(username.equals("") || password.equals("")){
                        Toast.makeText(ActivityLogin.this, "Bilgileri Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                    }
                    else{
//                        String result = userdataBase.check_paswd(username, password);
                        userdataBase.check(username, password, new FirebaseCallback() {
                            @Override
                            public void onCallback(String loginResult) {
                                String result = userdataBase.loginResult;
                                Toast.makeText(ActivityLogin.this, result, Toast.LENGTH_SHORT).show();

                            }

                        });
//                        String result = userdataBase.check_paswd(username, password);


//                        if (result.equals("ok")){
//                            Toast.makeText(ActivityLogin.this, "Welcome "+username, Toast.LENGTH_SHORT).show();
//                        }
//                        else if (result.equals("erpsw")){
//                            Toast.makeText(ActivityLogin.this, "Wrong Password! "+username, Toast.LENGTH_SHORT).show();
//                        }
//                        else if (result.equals("nouser")){
//                            Toast.makeText(ActivityLogin.this, "Username Not Found "+username, Toast.LENGTH_SHORT).show();
//
//                        }
                    }

                }
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if (signupclicked){
                    laysignup.setVisibility(View.GONE);
                    btn_forgot.setEnabled(true);
                    btn_signup.setText("Sign Up");
                    logintext.setText("Login");
                    signupclicked = false;
                }
                else{
                    laysignup.setVisibility(View.VISIBLE);
                    btn_signup.setText("Login");
                    logintext.setText("Sign Up");
                    btn_forgot.setEnabled(false);
                    signupclicked = true;
                }
            }
        });

        btn_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ActivityLogin.this, "unutma amk", Toast.LENGTH_SHORT).show();
            }
        });







    }
}