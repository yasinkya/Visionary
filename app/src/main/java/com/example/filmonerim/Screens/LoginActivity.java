    package com.example.filmonerim.Screens;

    import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

    import com.example.filmonerim.R;
    import com.example.filmonerim.model.User;
    import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.filmonerim.NotificationChannel.App.CHANNEL_1_ID;

    public class LoginActivity extends AppCompatActivity {

    boolean isLogin;

    NotificationManagerCompat notificationManager;

    //Firebase reference definition
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference();
    FirebaseDatabase database= FirebaseDatabase.getInstance();

    RelativeLayout check;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            // -------- Set Full Screen
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

            notificationManager = NotificationManagerCompat.from(this);

            //Check before log
            check = (RelativeLayout)findViewById(R.id.check);
            check.setVisibility(View.GONE);
            isAnyLog();


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
                    else {

                        // READ
                        User check =new User();
                        dbRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot ds:snapshot.child("Users").getChildren()){
                                    long sayac=1;
                                    check.UserName=ds.child("UserName").getValue().toString();
                                    check.Password=ds.child("Password").getValue().toString();
                                    if(check.UserName.equals(uName)){
                                        if(check.Password.equals(psw)){
                                            Toast.makeText(LoginActivity.this,"Login Succesful",Toast.LENGTH_SHORT).show();

                                            changeIntent(ds.getKey(),uName);
                                            isLogin = true;
                                            break;
                                        }
                                        else{
                                            Toast.makeText(LoginActivity.this,"Wrong Password, try again",Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                    }
                                    else
                                        sayac++;
                                    if(sayac==snapshot.child("Users").getChildrenCount()){
                                        Toast.makeText(LoginActivity.this,"Wrong Username, try again",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                }
            });

        }




//Uygulama açıldığında daha önce giriş yapan kullanıcı var mı kontrol et
        private void isAnyLog(){
            dbRef.addValueEventListener(new ValueEventListener(){

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    long sayac=1;
                    for(DataSnapshot ds : snapshot.child("Users").getChildren()){
                        if(Boolean.parseBoolean( ds.child("LOGIN").getValue().toString())){
                            changeIntent(ds.getKey(),ds.child("UserName").getValue().toString().toUpperCase());
                            break;
                        }
                        else{
                            if(sayac==snapshot.child("Users").getChildrenCount()){
                                Toast.makeText(LoginActivity.this,"WELCOME, Please Login First",Toast.LENGTH_LONG).show();
                                check.setVisibility(View.VISIBLE);
                                break;
                            }
                            else
                                sayac++;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }


// Ekran Değiştir ve Giriş yapılan kullanıcının giriş bilgisini db'de değiştir
        private void changeIntent(String key,String userName){
            dbRef.child("Users").child(key).child("LOGIN").setValue("true");
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("key",key);
            finishAffinity();

        //NOTIFICATION FOR LOG IN
            Notification notification= new NotificationCompat.Builder(LoginActivity.this,CHANNEL_1_ID)
                    .setContentTitle("WELCOME")
                    .setContentText("We glad to see you "+userName)
                    .setSmallIcon(R.drawable.mov)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();

            notificationManager.notify(1,notification);

            this.startActivity(intent);
        }


// When Back Pressed dont close first
        int pressed=0;
        @Override
        public void onBackPressed() {

            if(!isLogin){
                //dont close
                if(pressed==0){
                    Toast.makeText(LoginActivity.this,"Press Again For Close App",Toast.LENGTH_LONG).show();
                    pressed++;
                }
                else
                    System.exit(0);
            }
            else
                super.onBackPressed();
        }
    }
