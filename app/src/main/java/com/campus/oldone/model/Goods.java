package com.campus.oldone.model;

import java.util.ArrayList;
import java.util.List;

public class Goods {
    private int imageId;
    private String title;
    private String content;
    private Double price;
    private String location;
    private String type;
    private String phone;
    private String email;
    private int sold;
    private int ownerId;
    private List<String> images;

    public Goods(int imageId, String title, String content, Double price, String location,String phone,String email) {
        this.imageId = imageId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.sold = 0;
        images = new ArrayList<>();
        images.add("FFFE");
        images.add("WWWWW");
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSold() {
        return sold;
    }

    public void setSold(int sold) {
        this.sold = sold;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
