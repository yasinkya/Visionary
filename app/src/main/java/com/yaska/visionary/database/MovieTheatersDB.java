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


public class MovieTheatersDB extends DatabaseService {

    DatabaseReference databaseRef;
    Theater theater;
    Map<String, Theater> theaterMap;
    public Map<String, Map<String, Theater>> allTheatersMap = new HashMap<>();

    public MovieTheatersDB(){
        databaseRef = ref.child("Movie Theaters");
    }

    /* Veritabanında movietheaters'ın altıdaki verileri getir */
    public void getCityTheaters(final GetCityTheatersCallback getCityTheatersCallback){

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                /* Veri tabanında önce şehileri gezer ve bu şehilerin her birinde bulunan sinema
                * salonlarını allTheatersMap nesnesine aktarır son olarak callback yaparak işlemi  bitirir */
                for (DataSnapshot city: snapshot.getChildren()){

                    /* gezilen şehirler için yeni bir map yaratılı bu map in içerisine de o salona
                    * ait bilgileri ve güncel vizyondaki sinemaları aktarır */

                    theaterMap = new HashMap<>();
                    for (DataSnapshot theater: city.getChildren()){
                        MovieTheatersDB.this.theater = new Theater();
                        for (DataSnapshot attrs: theater.getChildren()){
                            switch (Objects.requireNonNull(attrs.getKey())){
                                case "3-d":
                                    MovieTheatersDB.this.theater.ThreeD = (boolean) attrs.getValue();
                                    break;
                                case "air-cond":
                                    MovieTheatersDB.this.theater.AirCond = (boolean) attrs.getValue();
                                    break;
                                case "cafe":
                                    MovieTheatersDB.this.theater.Cafe = (boolean) attrs.getValue();
                                    break;
                                case "dolby":
                                    MovieTheatersDB.this.theater.Dolby = (boolean) attrs.getValue();
                                    break;
                                case "parking":
                                    MovieTheatersDB.this.theater.Parking = (boolean) attrs.getValue();
                                    break;
                                case "phone-sale":
                                    MovieTheatersDB.this.theater.PhoneSale = (boolean) attrs.getValue();
                                    break;
                                case "Address":
                                    MovieTheatersDB.this.theater.Address = (String) attrs.getValue();
                                    break;
                                case "Name":
                                    MovieTheatersDB.this.theater.Name = (String) attrs.getValue();
                                    break;
                                case "Number":
                                    MovieTheatersDB.this.theater.Number = (String) attrs.getValue();
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
                                    MovieTheatersDB.this.theater.InTheaters = inTheaters;

                                    break;

                            }
                            MovieTheatersDB.this.theater.City = city.getKey();

                        }
                        theaterMap.put(theater.getKey(), MovieTheatersDB.this.theater);
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
