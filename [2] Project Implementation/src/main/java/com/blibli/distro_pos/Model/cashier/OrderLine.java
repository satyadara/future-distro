package com.blibli.distro_pos.Model.cashier;

public class OrderLine {
    private String id_orderline;
    private String id_trans;
    private String id_item;
    private double item_price;
    private double subtotal;
    private int quantity;
    private String item_name;

    public OrderLine() {
    }

    public OrderLine(String id_orderline, String id_trans, String id_item, double item_price, double subtotal, int quantity) {
        this.setId_orderline(id_orderline);
        this.setId_trans(id_trans);
        this.setId_item(id_item);
        this.setItem_price(item_price);
        this.setSubtotal(subtotal);
        this.setQuantity(quantity);
    }

    public OrderLine(String id_orderline, String id_trans, String id_item, double item_price, double subtotal, int quantity, String item_name) {
        this.id_orderline = id_orderline;
        this.id_trans = id_trans;
        this.id_item = id_item;
        this.item_price = item_price;
        this.subtotal = subtotal;
        this.quantity = quantity;
        this.item_name = item_name;
    }

    public String getId_orderline() {
        return id_orderline;
    }

    public void setId_orderline(String id_orderline) {
        this.id_orderline = id_orderline;
    }

    public String getId_trans() {
        return id_trans;
    }

    public void setId_trans(String id_trans) {
        this.id_trans = id_trans;
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
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
}
