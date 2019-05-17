package com.example.schoolapp.Body;


//Create an Unique object that stores the relevant information from the Firebase Database
public class User {

    private String id;
    private String name;
    private String imageURL;
    private String currentStatus;
    private String search;
    private String timetable;

    public User(String id, String name, String imageURL, String currentStatus, String search, String timetable){
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.currentStatus = currentStatus;
        this.search = search;
        this.timetable = timetable;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    public User(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
