package com.yaska.visionary.model;

import java.io.Serializable;
import java.util.List;

public class Theater implements Serializable {

    public String City;
    public String Name;
    public String Address;
    public String Number;
    public boolean ThreeD;
    public boolean AirCond;
    public boolean Cafe;
    public boolean Parking;
    public boolean Dolby;
    public boolean PhoneSale;
    public List<InTheater> InTheaters;

}
