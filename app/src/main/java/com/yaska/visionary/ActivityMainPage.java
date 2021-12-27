package com.yaska.visionary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;

import com.yaska.visionary.model.User;

public class ActivityMainPage extends AppCompatActivity {

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        user = (User) getIntent().getSerializableExtra("user");
//        Toast.makeText(ActivityMainPage.this, user.UserName, Toast.LENGTH_SHORT).show();




    }
}