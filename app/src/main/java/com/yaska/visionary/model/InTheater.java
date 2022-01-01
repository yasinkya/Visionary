package com.yaska.visionary.model;

import java.io.Serializable;
import java.util.List;

public class InTheater  implements Serializable {

    public String Name;
    public List<Ticket> Tickets;

    public InTheater(String name, List<Ticket> tickets){
        this.Name = name;
        this.Tickets = tickets;
    }
}

