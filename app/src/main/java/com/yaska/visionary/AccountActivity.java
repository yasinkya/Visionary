package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.text.InputType;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.yaska.visionary.database.UserDB;
import com.yaska.visionary.model.User;

public class AccountActivity extends AppCompatActivity {

    User user;
    UserDB userDB;
    TextView tv_username, showpass;
    EditText et_password, et_name, et_surname, et_mail;
    CardView btn_update, btn_delete;
    boolean ispassShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        user = (User) getIntent().getSerializableExtra("user");
        userDB = new UserDB();

        tv_username = findViewById(R.id.et_acusername);
        et_password = findViewById(R.id.et_acpass);
        et_name = findViewById(R.id.et_acname);
        et_surname = findViewById(R.id.et_acsurname);
        et_mail = findViewById(R.id.et_acmail);
        showpass = findViewById(R.id.showpass);

        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);

        tv_username.setText(user.UserName);
        et_password.setText(user.Password);
        et_name.setText(user.Name);
        et_surname.setText(user.UserName);
        et_mail.setText(user.Mail);

        showpass.setOnClickListener(v -> {
            if (ispassShow){
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                showpass.setBackgroundResource(R.drawable.icon_showpassw);
                ispassShow = false;
            }
            else{
                et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                showpass.setBackgroundResource(R.drawable.icon_hidepassw);
                ispassShow = true;
            }
        });

        btn_update.setOnClickListener(v -> {
            userDB.update_user(new User(et_name.getText().toString(), et_surname.getText().toString(),
                    et_mail.getText().toString(), user.UserName, et_password.getText().toString()));
        });



    }
}