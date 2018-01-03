package com.blibli.distro_pos.Model.summary;

public class LoyalCustomer {
    private String name_cust;
    private double total;

    public LoyalCustomer() {
    }

    public LoyalCustomer(String name_cust, double total) {
        this.name_cust = name_cust;
        this.total = total;
    }

    public String getName_cust() {
        return name_cust;
    }

    public void setName_cust(String name_cust) {
        this.name_cust = name_cust;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
