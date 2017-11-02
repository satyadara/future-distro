package com.blibli.distro_pos.Model;

public class User {

//    private int id;
//    private String namaLengkap;
    private String username;
    private String password;
//    private String alamat;
//    private int noKtp;
//    private int noTelp;
//    private  char jenisKelamin;
    private String alamat;
    private int noKtp;
    private int noTelp;
    private char jenisKelamin;
    private boolean enabled;

    public User(String username, String password, boolean enabled) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
