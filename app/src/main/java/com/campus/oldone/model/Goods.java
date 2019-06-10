package com.campus.oldone.model;

public class Goods {
    private int imageId;
    private String title;
    private String content;
    private Double price;
    private Boolean isSale;

    public Goods(int imageId, String title, String content, Double price) {
        this.imageId = imageId;
        this.title = title;
        this.content = content;
        this.price = price;
        this.isSale = false;
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
}
