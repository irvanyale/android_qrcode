package app.proyek.qrcode.model;

/**
 * Created by WINDOWS 10 on 11/06/2017.
 */

public class Transaksi {

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
