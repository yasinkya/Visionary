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

    DatabaseReference databaseRef;
    Theater theater;
    Map<String, Theater> theaterMap;
    public Map<String, Map<String, Theater>> allTheatersMap = new HashMap<>();




    public TheaterDB(){
        databaseRef = ref.child("Movie Theaters");
    }

    public void getCityTheaters(final GetCityTheatersCallback getCityTheatersCallback){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot city: snapshot.getChildren()){
                    theaterMap = new HashMap<>();
                    for (DataSnapshot theater: city.getChildren()){
                        TheaterDB.this.theater = new Theater();
                        for (DataSnapshot attrs: theater.getChildren()){
                            switch (Objects.requireNonNull(attrs.getKey())){
                                case "3-d":
                                    TheaterDB.this.theater.ThreeD = (boolean) attrs.getValue();
                                    break;
                                case "air-cond":
                                    TheaterDB.this.theater.AirCond = (boolean) attrs.getValue();
                                    break;
                                case "cafe":
                                    TheaterDB.this.theater.Cafe = (boolean) attrs.getValue();
                                    break;
                                case "dolby":
                                    TheaterDB.this.theater.Dolby = (boolean) attrs.getValue();
                                    break;
                                case "parking":
                                    TheaterDB.this.theater.Parking = (boolean) attrs.getValue();
                                    break;
                                case "phone-sale":
                                    TheaterDB.this.theater.PhoneSale = (boolean) attrs.getValue();
                                    break;
                                case "Address":
                                    TheaterDB.this.theater.Address = (String) attrs.getValue();
                                    break;
                                case "Name":
                                    TheaterDB.this.theater.Name = (String) attrs.getValue();
                                    break;
                                case "Number":
                                    TheaterDB.this.theater.Number = (String) attrs.getValue();
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
                                    TheaterDB.this.theater.InTheaters = inTheaters;

                                    break;

                            }
                            TheaterDB.this.theater.City = city.getKey();

                        }
                        theaterMap.put(theater.getKey(), TheaterDB.this.theater);
                    }
                    allTheatersMap.put(city.getKey(), theaterMap);
                }
                getCityTheatersCallback.onCallback(allTheatersMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        databaseRef.addValueEventListener(valueEventListener);

    }

}
