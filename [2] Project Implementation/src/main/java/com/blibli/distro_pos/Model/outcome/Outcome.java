package com.blibli.distro_pos.Model.outcome;

public class Outcome {
    private String id_outcome;
    private String username;
    private String title;
    private double amount;
    private int quantity;
    private String date;
    private String desc;
    private String status;

    public Outcome() {
    }

    public Outcome(String id_outcome, String username, String title, double amount,
                   int quantity, String date, String desc, String status) {
        this.setId_outcome(id_outcome);
        this.setUsername(username);
        this.setTitle(title);
        this.setAmount(amount);
        this.setQuantity(quantity);
        this.setDate(date);
        this.setDesc(desc);
        this.setStatus(status);
    }

    public String getId_outcome() {
        return id_outcome;
    }

    public void setId_outcome(String id_outcome) {
        this.id_outcome = id_outcome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
