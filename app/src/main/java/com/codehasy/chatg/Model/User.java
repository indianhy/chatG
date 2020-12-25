package com.codehasy.chatg.Model;

public class User {
    private String id;
    private String username;
    private String imageURL;
    private String password;

    public User(String id, String username,String password, String imageURL) {
        this.id = id;
        this.username = username;
        this.imageURL = imageURL;
        this.password=password;
    }

    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
