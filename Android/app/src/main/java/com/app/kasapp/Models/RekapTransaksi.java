package com.app.kasapp.Models;

public class RekapTransaksi {

    String id_transaksi, id_anggota, nama_anggota, jumlah_kas, jumlah_tabungan, jumlah_transaksi, tanggal_transaksi;

    public RekapTransaksi(String id_transaksi, String id_anggota, String nama_anggota, String jumlah_kas,
                          String jumlah_tabungan, String jumlah_transaksi, String tanggal_transaksi) {
        this.id_transaksi = id_transaksi;
        this.id_anggota = id_anggota;
        this.nama_anggota = nama_anggota;
        this.jumlah_kas = jumlah_kas;
        this.jumlah_tabungan = jumlah_tabungan;
        this.jumlah_transaksi = jumlah_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getId_transaksi() {
        return id_transaksi;
    }

    public void setId_transaksi(String id_transaksi) {
        this.id_transaksi = id_transaksi;
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

    public String getJumlah_kas() {
        return jumlah_kas;
    }

    public void setJumlah_kas(String jumlah_kas) {
        this.jumlah_kas = jumlah_kas;
    }

    public String getJumlah_tabungan() {
        return jumlah_tabungan;
    }

    public void setJumlah_tabungan(String jumlah_tabungan) {
        this.jumlah_tabungan = jumlah_tabungan;
    }

    public String getJumlah_transaksi() {
        return jumlah_transaksi;
    }

    public void setJumlah_transaksi(String jumlah_transaksi) {
        this.jumlah_transaksi = jumlah_transaksi;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }
}
