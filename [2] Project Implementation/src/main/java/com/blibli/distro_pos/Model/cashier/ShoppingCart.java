package com.blibli.distro_pos.Model.cashier;

public class ShoppingCart {
    private String id_item, username, item_name;
    private int quantity;
    private double subtotal;

    public ShoppingCart() {
    }

    public ShoppingCart(String id_item, String username, int quantity, String item_name, double subtotal) {
        this.id_item = id_item;
        this.username = username;
        this.quantity = quantity;
        this.item_name = item_name;
        this.subtotal = subtotal;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "id_item='" + id_item + '\'' +
                ", username='" + username + '\'' +
                ", item_name='" + item_name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
