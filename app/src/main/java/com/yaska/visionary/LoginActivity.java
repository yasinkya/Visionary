package com.yaska.visionary;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.yaska.visionary.database.UserDB;
import com.yaska.visionary.model.User;

public class LoginActivity extends AppCompatActivity {

    boolean signupclicked;
    String lastUser, checkResult;
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

        checkLastLogin();

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

            // Sing up
            if (signupclicked){
                String name = et_name.getText().toString();
                String surname = et_surname.getText().toString();
                String mail = et_mail.getText().toString();

                if(username.equals("") || password.equals("") || name.equals("") || surname.equals("") || mail.equals("")){
                    Toast.makeText(LoginActivity.this, "Bilgileri Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(LoginActivity.this, "kayıt olunuyor", Toast.LENGTH_SHORT).show();
                    user = new User(name, surname, mail, username, password);
                    userdataBase.new_user(user, username);
//                    userdataBase.change_last_login(username);
                    changeIntent(user);
                }

            }
            // Login
            else{
                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Bilgileri Boş Bırakmayınız!", Toast.LENGTH_SHORT).show();
                }
                else{
                    userdataBase.checkUserPass(username, password, result -> {
                        checkResult = userdataBase.checkResult;
                        if(checkResult.equals("ok")){
                            userdataBase.getUser(username, getUser -> {
                                user = userdataBase.returnUser;
                                makeToast("Welcome "+user.UserName);
                                changeIntent(user);

                            });
                        }
                        else if (checkResult.equals("wp"))
                            Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(LoginActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();


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

        btn_forgot.setOnClickListener(v -> {
            showResetPassDialog();
        });




    }

    private void makeToast(String mes){
        Toast.makeText(LoginActivity.this, mes, Toast.LENGTH_SHORT).show();
    }

    void showResetPassDialog(){
        final Dialog dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_forgot_passwd);

        final EditText et_username, et_respas, et_newpas;
        et_username = dialog.findViewById(R.id.et_resusername);
        et_respas = dialog.findViewById(R.id.et_respass);

        final CardView btnreset = dialog.findViewById(R.id.btn_reset);

        btnreset.setOnClickListener(v -> {
            String username, newpas;
            username = et_username.getText().toString();
            newpas = et_respas.getText().toString();

            userdataBase.check_user(username, returnUser -> {
                if(userdataBase.returnUser == null)
                    makeToast("User Not Found!");
                else{
                    userdataBase.update_user(username, "Password", newpas);
                    makeToast("Password Updated");
                    dialog.dismiss();
                    userdataBase.getUser(username, getUser -> {
                        user = userdataBase.returnUser;
                        makeToast("Welcome "+user.UserName);
                        changeIntent(user);

                    });

                }
            });




        });
        dialog.show();

    }

    private void clean_fields(){
        et_username.setText("");
        et_username.requestFocus();
        et_password.setText("");
        et_name.setText("");
        et_surname.setText("");
        et_mail.setText("");
    }

    // Ekran Değiştir ve Giriş yapılan kullanıcının giriş bilgisini db'de değiştir
    private void changeIntent(User user){
        Intent intent = new Intent(LoginActivity.this, MainPageActivity.class);
        intent.putExtra("user", user);
        finishAffinity();

        this.startActivity(intent);
    }


    private void checkLastLogin(){
        userdataBase.getLastUser(getLastUser -> {
            lastUser = userdataBase.lastUser;
            if(!lastUser.equals("")){
                userdataBase.getUser(lastUser, getUser -> {
                    user = userdataBase.returnUser;
                    changeIntent(user);
                });
            }
        });
    }

    // When Back Pressed dont close at first
    int pressed=0;
    @Override
    public void onBackPressed() {
        if (pressed == 0){
            Toast.makeText(LoginActivity.this, "Press again for exit", Toast.LENGTH_SHORT).show();
            pressed++;
        }
        else{
            super.onBackPressed();
        }
    }
}