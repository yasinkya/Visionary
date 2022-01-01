package com.yaska.visionary.model;

import java.io.Serializable;

public class Ticket  implements Serializable {
    public String Time;
    public String Url;

    public Ticket(String time, String url){
        this.Time = time;
        this.Url = url;
    }

}
