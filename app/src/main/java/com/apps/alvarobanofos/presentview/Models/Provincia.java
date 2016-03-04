package com.apps.alvarobanofos.presentview.Models;

/**
 * Created by alvarobanofos on 25/2/16.
 */
public class Provincia {
    private int Id;
    private String Name;

    public Provincia(int id, String name) {
        Id = id;
        Name = name;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
