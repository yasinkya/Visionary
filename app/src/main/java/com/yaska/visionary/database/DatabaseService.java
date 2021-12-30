package com.yaska.visionary.database;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yaska.visionary.model.User;

import java.util.List;
import java.util.Map;

public class DatabaseService {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference();

    public interface UserCallback {
        void onCallback(User returnUser);
    }

    public interface CheckPassCallback {
        void onCallBack(String result);
    }

    public interface GetUserCallback{
        void onCallback(User getUser);
    }
    
    public interface GetLastUserCallback {
        void onCallBack(String getLastUser);
    }

    public interface GetCitiesCallback{
        void onCallback(List<String> getCities);
    }

    public interface GetTheatersMapCallback{
        void onCallback(Map<String, List<String>> getTheatersMap);
    }

}
