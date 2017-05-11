package com.irvanyale.app.qrcode.model;

/**
 * Created by WINDOWS 10 on 02/04/2017.
 */

public class Item {
    private String id_barang;
    private String nama_barang;
    private String harga;
    private String berat;
    private String tanggal_masuk_barang;
    private String masa_berlaku;
    private String distributor;
    private String stok;
    private int qty;
    private String img;

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getHarga() {
        return harga;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public String getBerat() {
        return berat;
    }

    public void setBerat(String berat) {
        this.berat = berat;
    }

    public String getTanggal_masuk_barang() {
        return tanggal_masuk_barang;
    }

    public void setTanggal_masuk_barang(String tanggal_masuk_barang) {
        this.tanggal_masuk_barang = tanggal_masuk_barang;
    }

    public String getMasa_berlaku() {
        return masa_berlaku;
    }

    public void setMasa_berlaku(String masa_berlaku) {
        this.masa_berlaku = masa_berlaku;
    }

    public String getDistributor() {
        return distributor;
    }

    public void setDistributor(String distributor) {
        this.distributor = distributor;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
