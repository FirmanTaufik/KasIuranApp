package com.app.kasapp.Models;

public class RekapPengeluaran {
    String id_pengeluaran, jumlah_pengeluaran, keperluan, tanggal_pengeluaran;

    public RekapPengeluaran(String id_pengeluaran, String jumlah_pengeluaran, String keperluan, String tanggal_pengeluaran) {
        this.id_pengeluaran = id_pengeluaran;
        this.jumlah_pengeluaran = jumlah_pengeluaran;
        this.keperluan = keperluan;
        this.tanggal_pengeluaran = tanggal_pengeluaran;
    }

    public String getId_pengeluaran() {
        return id_pengeluaran;
    }

    public void setId_pengeluaran(String id_pengeluaran) {
        this.id_pengeluaran = id_pengeluaran;
    }

    public String getJumlah_pengeluaran() {
        return jumlah_pengeluaran;
    }

    public void setJumlah_pengeluaran(String jumlah_pengeluaran) {
        this.jumlah_pengeluaran = jumlah_pengeluaran;
    }

    public String getKeperluan() {
        return keperluan;
    }

    public void setKeperluan(String keperluan) {
        this.keperluan = keperluan;
    }

    public String getTanggal_pengeluaran() {
        return tanggal_pengeluaran;
    }

    public void setTanggal_pengeluaran(String tanggal_pengeluaran) {
        this.tanggal_pengeluaran = tanggal_pengeluaran;
    }
}
