package com.example.androidaddandfetchfirestore.university;

public class university {
    private String name;
    private String type;

    private String website;

    public university(String name, String type, String website) {
        this.name = name;
        this.type = type;
        this.website = website;
    }

    //Getter and Setter for name, type and website
    public university() {}

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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}