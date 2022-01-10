package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yaska.visionary.database.UserDB;
import com.yaska.visionary.model.User;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout account, application, help, signout;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        account = findViewById(R.id.settings_account);
        application = findViewById(R.id.settings_appsettings);
        help = findViewById(R.id.settings_help);
        signout = findViewById(R.id.settings_signout);

        account.setOnClickListener(v -> {
            User user = (User) getIntent().getSerializableExtra("user");
            intent = new Intent(SettingsActivity.this, AccountActivity.class);
            intent.putExtra("user", user);
            this.startActivity(intent);
        });

        application.setOnClickListener(v -> {
            intent = new Intent(SettingsActivity.this, AppSettingsActivity.class);
            this.startActivity(intent);
        });
        help.setOnClickListener(v -> {
            intent = new Intent(SettingsActivity.this, AppSettingsActivity.class);
            this.startActivity(intent);
        });

        signout.setOnClickListener(v -> {
            UserDB userDB = new UserDB();
            userDB.changeLastLogin("");
            intent = new Intent(SettingsActivity.this, LoginActivity.class);
            this.finishAffinity();
            this.startActivity(intent);
        });

    }
}