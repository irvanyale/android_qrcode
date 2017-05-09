package com.irvanyale.app.qrcode.model;

import java.util.List;

/**
 * Created by WINDOWS 10 on 17/04/2017.
 */

public class ItemChart {
    private String id;
    private String status;
    private String tanggal;
    private List<Item> item;

    public String getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public List<Item> getItem() {
        return item;
    }

    private class Item {
        String id_barang;
        String nama_barang;
        String distributor;
        String masa_berlaku;
        String tanggal_masuk_barang;
        String berat;
        String harga;
        String stok;

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

        public String getDistributor() {
            return distributor;
        }

        public void setDistributor(String distributor) {
            this.distributor = distributor;
        }

        public String getMasa_berlaku() {
            return masa_berlaku;
        }

        public void setMasa_berlaku(String masa_berlaku) {
            this.masa_berlaku = masa_berlaku;
        }

        public String getTanggal_masuk_barang() {
            return tanggal_masuk_barang;
        }

        public void setTanggal_masuk_barang(String tanggal_masuk_barang) {
            this.tanggal_masuk_barang = tanggal_masuk_barang;
        }

        public String getBerat() {
            return berat;
        }

        public void setBerat(String berat) {
            this.berat = berat;
        }

        public String getHarga() {
            return harga;
        }

        public void setHarga(String harga) {
            this.harga = harga;
        }

        public String getStok() {
            return stok;
        }

        public void setStok(String stok) {
            this.stok = stok;
        }
    }
}
