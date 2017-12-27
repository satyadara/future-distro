package com.blibli.distro_pos.Model.item;

public class Item {
    private String id_item;
    private String username;
    private String name_item;
    private float price;
    private String image;
    private String merk;
    private int stock;
    private String color;
    private String size;
    private String type;
    private String status;

    public Item() {
    }

    public Item(String id_item, String username, String name_item, float price, String merk, int stock, String image, String color, String size, String type, String status) {
        this.id_item = id_item;
        this.username = username;
        this.name_item = name_item;
        this.price = price;
        this.image = image;
        this.merk = merk;
        this.stock = stock;
        this.color = color;
        this.size = size;
        this.type = type;
        this.status = status;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}