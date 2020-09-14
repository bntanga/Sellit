package com.example.sellitpt5;

import java.util.ArrayList;

public class UserDataSchema {
    String username;
    String email;
    String phone_number;
    ArrayList<String> saved_posts;

    public UserDataSchema(String username, String email, String phone_number, ArrayList<String> saved_posts) {
        this.username = username;
        this.email = email;
        this.phone_number = phone_number;
        this.saved_posts = saved_posts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public ArrayList<String> getSaved_posts() {
        return saved_posts;
    }

    public void setSaved_posts(ArrayList<String> saved_posts) {
        this.saved_posts = saved_posts;
    }
}
