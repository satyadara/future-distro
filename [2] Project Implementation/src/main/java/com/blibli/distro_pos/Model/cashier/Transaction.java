package com.blibli.distro_pos.Model.cashier;

public class Transaction {
    String id_trans, id_disc, id_emp, customer_name, date, status;
    Double total_trans;

    public Transaction(String id_trans, String id_disc, String id_emp, String customer_name, Double total_trans, String date, String status) {
        this.id_trans = id_trans;
        this.id_disc = id_disc;
        this.id_emp = id_emp;
        this.customer_name = customer_name;
        this.date = date;
        this.status = status;
        this.total_trans = total_trans;
    }

    public Transaction() {
    }

    public String getId_trans() {

        return id_trans;
    }

    public void setId_trans(String id_trans) {
        this.id_trans = id_trans;
    }

    public String getId_disc() {
        return id_disc;
    }

    public void setId_disc(String id_disc) {
        this.id_disc = id_disc;
    }

    public String getId_emp() {
        return id_emp;
    }

    public void setId_emp(String id_emp) {
        this.id_emp = id_emp;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal_trans() {
        return total_trans;
    }

    public void setTotal_trans(Double total_trans) {
        this.total_trans = total_trans;
    }
}
