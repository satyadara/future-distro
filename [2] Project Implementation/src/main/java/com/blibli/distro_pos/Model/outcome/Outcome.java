package com.blibli.distro_pos.Model.outcome;

public class Outcome {
    private String id_outcome;
    private String id_emp;
    private String title;
    private double amount;
    private int quantity;
    private String date;
    private String desc;

    public Outcome() {
    }

    public Outcome(String id_outcome, String id_emp, String title, double amount, int quantity, String date, String desc) {
        this.setId_outcome(id_outcome);
        this.setId_emp(id_emp);
        this.setTitle(title);
        this.setAmount(amount);
        this.setQuantity(quantity);
        this.setDate(date);
        this.setDesc(desc);
    }

    public String getId_outcome() {
        return id_outcome;
    }

    public void setId_outcome(String id_outcome) {
        this.id_outcome = id_outcome;
    }

    public String getId_emp() {
        return id_emp;
    }

    public void setId_emp(String id_emp) {
        this.id_emp = id_emp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String id_emp) {
        this.title = title;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
