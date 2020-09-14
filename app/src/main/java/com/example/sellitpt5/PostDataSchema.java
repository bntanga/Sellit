package com.example.sellitpt5;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;

public class PostDataSchema  {

    private static final String TAG = "PostDataSchema";
    public String product_name;
    public String product_price;
    public String image_uri;
    public String phone_number;
    public String description;
    public String location;
    public String user_name;
    public String date;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String user_id;
    public ArrayList<String> tags;

    public PostDataSchema(String product_name, String product_price, String date,
                          String image_uri, String description, String location, String phone_number, ArrayList<String> tags, String user_id, String user_name
    ) {
        this.product_name = product_name;
        this.product_price = product_price;
        this.image_uri = image_uri;
        this.description = description;
        this.location = location;
        this.phone_number = phone_number;
        this.tags = tags;
        this.user_id = user_id;
        this.user_name = user_name;
        this.date = date;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUser_name() {
        return user_name;
    }



    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }




    public String getImage_uri() {
        return image_uri;
    }

    public PostDataSchema() {
    }


    public String getProduct_name() {
        return product_name;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    @Override
    public String toString() {
        return "PostDataSchema{" +
                "product_name='" + product_name + '\'' +
                ", product_price='" + product_price + '\'' +
                ", image_uri='" + image_uri + '\'' +
                '}';
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public void setImage_uri(String image_uri) {
        this.image_uri = image_uri;
    }



}

