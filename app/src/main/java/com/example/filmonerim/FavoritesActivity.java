package com.example.filmonerim;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import static com.example.filmonerim.App.CHANNEL_1_ID;

public class FavoritesActivity extends AppCompatActivity {

    NotificationManagerCompat notificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        Button btn = findViewById(R.id.button);
        notificationManager=NotificationManagerCompat.from(this);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FavoritesActivity.this,"helo",Toast.LENGTH_LONG).show();


                Notification notification= new NotificationCompat.Builder(FavoritesActivity.this,CHANNEL_1_ID)
                        .setContentTitle("WELCOME")
                        .setContentText("We glad to see u ")
                        .setSmallIcon(R.drawable.mov)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                        .build();

                notificationManager.notify(1,notification);
            }
        });
    }
}