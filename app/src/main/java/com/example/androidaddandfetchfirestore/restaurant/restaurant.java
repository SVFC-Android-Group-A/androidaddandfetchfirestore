package com.example.androidaddandfetchfirestore.restaurant;

public class restaurant {
    private String name;
    private String type;
    private String location;

    public restaurant(String name, String type) {
        this.name = name;
        this.type = type;
    }

    //these are the getter and setter for the name, type and location. All are functioning

    public restaurant() {}

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}