package com.blibli.distro_pos.Model;

public class User {

    private String namaLengkap;
    private String username;
    private String password;
    private String alamat;
    private String ktp;
    private String telp;
    private String jenisKelamin;
    private boolean enabled;
    private String role;

    public User(String namaLengkap, String username, String password, String alamat, String ktp, String telp, String jenisKelamin, boolean enabled, String role) {
        this.namaLengkap = namaLengkap;
        this.username = username;
        this.password = password;
        this.alamat = alamat;
        this.ktp = ktp;
        this.telp = telp;
        this.jenisKelamin = jenisKelamin;
        this.enabled = enabled;
        this.role = role;
    }

    public User() {

    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
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

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKtp() {
        return ktp;
    }

    public void setKtp(String ktp) {
        this.ktp = ktp;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
