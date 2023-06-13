package com.app.kasapp.Models;

public class RekapPenarikan {

    String id_penarikan, id_anggota, nama_anggota, jumlah_penarikan, tanggal_penarikan;

    public RekapPenarikan(String id_penarikan, String id_anggota, String nama_anggota, String jumlah_penarikan, String tanggal_penarikan) {
        this.id_penarikan = id_penarikan;
        this.id_anggota = id_anggota;
        this.nama_anggota = nama_anggota;
        this.jumlah_penarikan = jumlah_penarikan;
        this.tanggal_penarikan = tanggal_penarikan;
    }

    public String getId_penarikan() {
        return id_penarikan;
    }

    public void setId_penarikan(String id_penarikan) {
        this.id_penarikan = id_penarikan;
    }

    public String getId_anggota() {
        return id_anggota;
    }

    public void setId_anggota(String id_anggota) {
        this.id_anggota = id_anggota;
    }

    public String getNama_anggota() {
        return nama_anggota;
    }

    public void setNama_anggota(String nama_anggota) {
        this.nama_anggota = nama_anggota;
    }

    public String getJumlah_penarikan() {
        return jumlah_penarikan;
    }

    public void setJumlah_penarikan(String jumlah_penarikan) {
        this.jumlah_penarikan = jumlah_penarikan;
    }

    public String getTanggal_penarikan() {
        return tanggal_penarikan;
    }

    public void setTanggal_penarikan(String tanggal_penarikan) {
        this.tanggal_penarikan = tanggal_penarikan;
    }
}
