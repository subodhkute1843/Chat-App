package com.example.mychatapp;

class Users {

    String uid;
    String name;
    String imageUri;
    String email;
    String status;

    //when we are dealing with firebase u also need one empty constructor
    public Users(){

    }

    public Users(String uid, String name, String imageUri, String email , String status) {
        this.uid = uid;
        this.name = name;
        this.imageUri = imageUri;
        this.email = email;
        this.status = status;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUri;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUri = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
