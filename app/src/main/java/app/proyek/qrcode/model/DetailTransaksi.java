package app.proyek.qrcode.model;

import java.util.List;

/**
 * Created by WINDOWS 10 on 11/06/2017.
 */

public class DetailTransaksi {

    private Content content;
    private List<Detail> detail;

    public Content getContent() {
        return content;
    }

    public List<Detail> getDetail() {
        return detail;
    }

    public static class Content {
        private String id_transaksi;
        private String id_user;
        private String tanggal_transaksi;
        private String no_antrian;
        private String id_kasir;
        private String status_transaksi;
        private String total_harga;

        public String getId_transaksi() {
            return id_transaksi;
        }

        public String getId_user() {
            return id_user;
        }

        public String getTanggal_transaksi() {
            return tanggal_transaksi;
        }

        public String getNo_antrian() {
            return no_antrian;
        }

        public String getId_kasir() {
            return id_kasir;
        }

        public String getStatus_transaksi() {
            return status_transaksi;
        }

        public String getTotal_harga() {
            return total_harga;
        }
    }

    public static class Detail {
        private String id_detail;
        private String id_transaksi;
        private String id_barang;
        private String harga_barang;
        private String jumlah_barang;
        private String nama_barang;
        private String distributor;
        private String masa_berlaku;
        private String tanggal_masuk_barang;
        private String berat;
        private String satuan;
        private String harga;
        private String stok;
        private String gambar;

        public String getId_detail() {
            return id_detail;
        }

        public String getId_transaksi() {
            return id_transaksi;
        }

        public String getId_barang() {
            return id_barang;
        }

        public String getHarga_barang() {
            return harga_barang;
        }

        public String getJumlah_barang() {
            return jumlah_barang;
        }

        public String getNama_barang() {
            return nama_barang;
        }

        public String getDistributor() {
            return distributor;
        }

        public String getMasa_berlaku() {
            return masa_berlaku;
        }

        public String getTanggal_masuk_barang() {
            return tanggal_masuk_barang;
        }

        public String getBerat() {
            return berat;
        }

        public String getSatuan() {
            return satuan;
        }

        public String getHarga() {
            return harga;
        }

        public String getStok() {
            return stok;
        }

        public String getGambar() {
            return gambar;
        }
    }
}
