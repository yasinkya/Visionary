package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
            showDialog("update");
        });
        btn_delete.setOnClickListener(v -> {
            showDialog("delete");
        });




    }
    void showDialog(String opt){
        final Dialog dialog = new Dialog(AccountActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        final AppCompatButton btn_cancel, btn_yes;

        switch (opt){
            case "delete":
                dialog.setContentView(R.layout.dialog_delete);

                break;
            case "update":
                dialog.setContentView(R.layout.dialog_update);
                break;
        }

        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_yes = dialog.findViewById(R.id.btn_yes);

        btn_cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });

        btn_yes.setOnClickListener(v -> {
            switch (opt){
                case "delete":
                    userDB.delete_user(user.UserName);
                    userDB.changeLastLogin("");
                    Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
                    dialog.dismiss();
                    this.finishAffinity();
                    this.startActivity(intent);

                    break;
                case "update":
                    userDB.update_user(new User(et_name.getText().toString(), et_surname.getText().toString(),
                            et_mail.getText().toString(), user.UserName, et_password.getText().toString()));
                    dialog.dismiss();
                    Toast.makeText(AccountActivity.this, "Account Updated", Toast.LENGTH_SHORT).show();
                    break;
            }
        });

        dialog.show();

    }
}