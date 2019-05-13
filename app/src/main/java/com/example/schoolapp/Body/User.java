package com.example.schoolapp.Body;

public class User {

    private String id;
    private String name;
    private String imageURL;
    private String currentStatus;

    public User(String id, String name, String imageURL, String currentStatus){
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.currentStatus = currentStatus;
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
}
