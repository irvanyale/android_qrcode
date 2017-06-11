package app.proyek.qrcode.model;

import java.util.List;

/**
 * Created by WINDOWS 10 on 17/04/2017.
 */

public class ItemChart {
    private String id_user;
    private String tanggal_transaksi;
    private String total_harga;
    private List<Item> items;

    public String getId_user() {
        return id_user;
    }

    public String getTanggal_transaksi() {
        return tanggal_transaksi;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setTanggal_transaksi(String tanggal_transaksi) {
        this.tanggal_transaksi = tanggal_transaksi;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public static class Item {
        private String id_barang;
        private String harga_barang;
        private String jumlah_barang;

        public String getId_barang() {
            return id_barang;
        }

        public void setId_barang(String id_barang) {
            this.id_barang = id_barang;
        }

        public String getHarga_barang() {
            return harga_barang;
        }

        public void setHarga_barang(String harga_barang) {
            this.harga_barang = harga_barang;
        }

        public String getJumlah_barang() {
            return jumlah_barang;
        }

        public void setJumlah_barang(String jumlah_barang) {
            this.jumlah_barang = jumlah_barang;
        }
    }
}
