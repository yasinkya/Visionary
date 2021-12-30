package com.yaska.visionary.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TheaterDB extends DatabaseService {

    DatabaseReference theaterRef;
    public List<String> cities = new ArrayList<>();
    public Map<String, List<String>> theatersMap = new HashMap<>();


    public TheaterDB(){
        theaterRef = ref.child("Movie Theaters");
    }

    public void getCities(final GetCitiesCallback getCitiesCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    cities.add(ds.getKey());
                }
                getCitiesCallback.onCallback(cities);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
        theaterRef.addListenerForSingleValueEvent(valueEventListener);
    }

    public void getTheatrsMap(final GetTheatersMapCallback getTheatersMapCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    List<String> templist = new ArrayList<>();
                    for (DataSnapshot _ds : ds.getChildren()){
                        templist.add(_ds.getKey());
                    }
                    theatersMap.put(ds.getKey(), templist);
                }
                getTheatersMapCallback.onCallback(theatersMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        theaterRef.addListenerForSingleValueEvent(valueEventListener);
    }

}
