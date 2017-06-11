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

    private static class Content {
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

    private static class Detail {
        private String id_detail;
        private String id_transaksi;
        private String id_barang;
        private String harga_barang;
        private String jumlah_barang;

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
    }
}
