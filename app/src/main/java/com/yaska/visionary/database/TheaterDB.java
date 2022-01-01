package com.yaska.visionary.database;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.yaska.visionary.model.InTheater;
import com.yaska.visionary.model.Theater;
import com.yaska.visionary.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class TheaterDB extends DatabaseService {

    DatabaseReference theaterRef;

    public Map<String, List<String>> theatersMap = new HashMap<>();
    public Map<String, List<Theater>> cityTheaters = new HashMap<>();
    Theater cityTheater;



    public TheaterDB(){
        theaterRef = ref.child("Movie Theaters");
    }

    public void getCityTheaters(final GetCityTheatersCallback getCityTheatersCallback){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot city: snapshot.getChildren()){
                    List<Theater> theaterList = new ArrayList<>();
                    for (DataSnapshot theater: city.getChildren()){
                        cityTheater = new Theater();
                        for (DataSnapshot attrs: theater.getChildren()){
                            switch (Objects.requireNonNull(attrs.getKey())){
                                case "3-d":
                                    cityTheater.ThreeD = (boolean) attrs.getValue();
                                    break;
                                case "air-cond":
                                    cityTheater.AirCond = (boolean) attrs.getValue();
                                    break;
                                case "cafe":
                                    cityTheater.Cafe = (boolean) attrs.getValue();
                                    break;
                                case "dolby":
                                    cityTheater.Dolby = (boolean) attrs.getValue();
                                    break;
                                case "parking":
                                    cityTheater.Parking = (boolean) attrs.getValue();
                                    break;
                                case "phone-sale":
                                    cityTheater.PhoneSale = (boolean) attrs.getValue();
                                    break;
                                case "Address":
                                    cityTheater.Address = (String) attrs.getValue();
                                    break;
                                case "Name":
                                    cityTheater.Name = (String) attrs.getValue();
                                    break;
                                case "Number":
                                    cityTheater.Number = (String) attrs.getValue();
                                    break;
                                case "InTheaters":
                                    List<InTheater> inTheaters = new ArrayList<>();
                                    for (DataSnapshot movies:attrs.getChildren()){
                                        List<Ticket> tickets = new ArrayList<>();
                                        for (DataSnapshot ticket: movies.getChildren()){
                                            tickets.add(new Ticket((String) ticket.child("Tıme").getValue(),
                                                    (String) ticket.child("Ticket Url").getValue()));
                                        }
                                        inTheaters.add(new InTheater(movies.getKey(), tickets));
                                    }
                                    cityTheater.InTheaters = inTheaters;

                                    break;

                            }
                            cityTheater.City = city.getKey();

                        }
                        theaterList.add(cityTheater);
                    }
                    cityTheaters.put(city.getKey(), theaterList);

                }
                getCityTheatersCallback.onCallback(cityTheaters);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        theaterRef.addValueEventListener(valueEventListener);

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
