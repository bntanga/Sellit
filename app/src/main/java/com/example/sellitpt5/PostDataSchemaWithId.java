package com.example.sellitpt5;

import java.util.ArrayList;

public class PostDataSchemaWithId extends PostDataSchema {
    public PostDataSchemaWithId() {
    }

    public String product_id;
    public PostDataSchemaWithId(String product_name, String product_price, String image_uri,
                                String description, String location, String phone_number, ArrayList<String> tags,
                                String user_id, String user_name, String date) {
        super(product_name, product_price, date, image_uri, description, location, phone_number, tags, user_id, user_name);
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    @Override
    public String toString() {
        return "PostDataSchemaWithId{" +
                "product_id='" + product_id + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_price='" + product_price + '\'' +
                ", image_uri='" + image_uri + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", tags=" + tags +
                '}';
    }
}
