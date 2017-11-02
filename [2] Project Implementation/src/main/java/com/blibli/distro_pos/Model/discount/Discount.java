package com.blibli.distro_pos.Model.discount;

import java.sql.Date;

public class Discount {
    private String id_disc;
    private String id_emp;
    private String name;
    private String desc;
    private float percentage;
    private String start_date;
    private String end_date;
    private String status;

    public Discount() {
    }

    public Discount(String id_disc, String id_emp, String name, String desc,
                    float percentage, String start_date, String end_date, String status) {
        this.setId_disc(id_disc);
        this.setId_emp(id_emp);
        this.setName(name);
        this.setDesc(desc);
        this.setPercentage(percentage);
        this.setStart_date(start_date);
        this.setEnd_date(end_date);
        this.setStatus(status);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
