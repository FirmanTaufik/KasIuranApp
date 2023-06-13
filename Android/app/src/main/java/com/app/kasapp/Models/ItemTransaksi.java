package com.app.kasapp.Models;

public class ItemTransaksi {
    public String jumlah_kas, jumla_tabungan, jumlah_transaksi,tanggal_transaksi;

    public ItemTransaksi(String jumlah_kas, String jumla_tabungan, String jumlah_transaksi, String tanggal_transaksi) {
        this.jumlah_kas = jumlah_kas;
        this.jumla_tabungan = jumla_tabungan;
        this.jumlah_transaksi = jumlah_transaksi;
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public String getJumlah_kas() {
        return jumlah_kas;
    }

    public void setJumlah_kas(String jumlah_kas) {
        this.jumlah_kas = jumlah_kas;
    }

    public String getJumla_tabungan() {
        return jumla_tabungan;
    }

    public void setJumla_tabungan(String jumla_tabungan) {
        this.jumla_tabungan = jumla_tabungan;
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
