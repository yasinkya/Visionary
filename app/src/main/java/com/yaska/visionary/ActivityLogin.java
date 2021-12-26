package com.yaska.visionary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import com.yaska.visionary.database.UserDB;

import java.util.Objects;

public class ActivityLogin extends AppCompatActivity {

    boolean signupclicked;
    UserDB userdataBase = new UserDB();
    User user;

    EditText et_username;
    EditText et_password;
    EditText et_name;
    EditText et_surname;
    EditText et_mail;

    CardView btn_login;
    TextView logintext;
    TextView btn_signup;
    TextView btn_forgot;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        LinearLayoutCompat laysignup = findViewById(R.id.lay_signup);
        laysignup.setVisibility(View.GONE);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_pass);
        et_name = findViewById(R.id.et_name);
        et_surname = findViewById(R.id.et_surname);
        et_mail = findViewById(R.id.et_mail);

        btn_login = findViewById(R.id.btn_login);
        logintext = findViewById(R.id.et_login);
        btn_signup = findViewById(R.id.btn_signup);
        btn_forgot = findViewById(R.id.btn_forgotpass);

        btn_login.setOnClickListener(v -> {

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
                    userdataBase.new_user(new User(name, surname, mail, username, password), username);
                }

            }
            else{
                if(username.equals("") || password.equals("")){
                    Toast.makeText(ActivityLogin.this, "Bilgileri Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                }
                else{
                    userdataBase.check_user(username, returnUser -> {
                        if (userdataBase.returnUser != null){
                            user = userdataBase.returnUser;
                            if (user.Password.equals(password)){
                                Toast.makeText(ActivityLogin.this, "Welcome", Toast.LENGTH_SHORT).show();

                            }
                            else
                                Toast.makeText(ActivityLogin.this, "Wrong Password", Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(ActivityLogin.this, "User Not Found", Toast.LENGTH_SHORT).show();

                    });

                }

            }
        });

        btn_signup.setOnClickListener(v -> {
            clean_fields();
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
        });

        btn_forgot.setOnClickListener(v ->
                Toast.makeText(ActivityLogin.this, "unutma amk", Toast.LENGTH_SHORT).show());


    }

    private void clean_fields(){
        et_username.setText("");
        et_username.requestFocus();
        et_password.setText("");
        et_name.setText("");
        et_surname.setText("");
        et_mail.setText("");
    }
}