package com.app.kasapp.Models;

public class ItemPenarikan {
    String id_penarikan, jumlah_penarikan, tanggal_penarikan;

    public ItemPenarikan(String id_penarikan, String jumlah_penarikan, String tanggal_penarikan) {
        this.id_penarikan = id_penarikan;
        this.jumlah_penarikan = jumlah_penarikan;
        this.tanggal_penarikan = tanggal_penarikan;
    }

    public String getId_penarikan() {
        return id_penarikan;
    }

    public void setId_penarikan(String id_penarikan) {
        this.id_penarikan = id_penarikan;
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
