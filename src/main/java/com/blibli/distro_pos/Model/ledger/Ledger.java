package com.blibli.distro_pos.Model.ledger;

public class Ledger {
    String date, title;
    Double in, out;

    public Ledger() {
    }

    public Ledger(String date, String title, Double in, Double out) {
        this.date = date;
        this.title = title;
        this.in = in;
        this.out = out;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getIn() {
        return in;
    }

    public void setIn(Double in) {
        this.in = in;
    }

    public Double getOut() {
        return out;
    }

    public void setOut(Double out) {
        this.out = out;
    }
}
