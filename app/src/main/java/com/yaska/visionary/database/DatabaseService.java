package com.yaska.visionary.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseService {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    public interface UserDBCallback {
        void onUserDBCallback(String loginResult);
    }

}
