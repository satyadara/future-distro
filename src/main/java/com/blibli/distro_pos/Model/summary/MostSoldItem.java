package com.blibli.distro_pos.Model.summary;

public class MostSoldItem {
    private String id_item;
    private String name_item;
    private int quantity;
    private double total;

    public MostSoldItem()   {}

    public MostSoldItem(String id_item, String name_item, int quantity, double total) {
        this.setId_item(id_item);
        this.setName_item(name_item);
        this.setQuantity(quantity);
        this.setTotal(total);
    }

    public String getId_item() {
        return id_item;
    }

    public void setId_item(String id_item) {
        this.id_item = id_item;
    }

    public String getName_item() {
        return name_item;
    }

    public void setName_item(String name_item) {
        this.name_item = name_item;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "MostSoldItem{" +
                "id_item='" + id_item + '\'' +
                ", name_item='" + name_item + '\'' +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
