package com.example.ilyapplication;

public class UserData {
    public String nim, nama, email, fakultas, prodi, telephone;

    public UserData() {

    }

    public UserData(String nim, String nama, String email, String fakultas, String prodi, String telephone){
        this.nim = nim;
        this.nama = nama;
        this.email = email;
        this.fakultas = fakultas;
        this.prodi = prodi;
        this.telephone = telephone;

    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFakultas() {
        return fakultas;
    }

    public void setFakultas(String fakultas) {
        this.fakultas = fakultas;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
