package app.proyek.qrcode.activity.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import app.proyek.qrcode.R;
import app.proyek.qrcode.model.Transaksi;
import app.proyek.qrcode.util.Util;

/**
 * Created by WINDOWS 10 on 11/06/2017.
 */

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder>{

    private Context context;
    private List<Transaksi> listTransaksi;
    private setOnClickListener listener;

    public TransaksiAdapter(Context context, List<Transaksi> listTransaksi) {
        this.context = context;
        this.listTransaksi = listTransaksi;
    }

    public Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout rlly_list;
        private TextView txvw_tgl;
        private TextView txvw_status;
        private TextView txvw_total;

        public ViewHolder(View itemView) {
            super(itemView);
            rlly_list = (RelativeLayout) itemView.findViewById(R.id.rlly_list);
            txvw_tgl = (TextView) itemView.findViewById(R.id.txvw_tgl);
            txvw_status = (TextView) itemView.findViewById(R.id.txvw_status);
            txvw_total = (TextView) itemView.findViewById(R.id.txvw_total);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Transaksi transaksi = listTransaksi.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(transaksi.getTanggal_transaksi());
        } catch (Exception e) {
            Log.e("TAG", Log.getStackTraceString(e));
        }
        sdf = new SimpleDateFormat("dd MMM yyyy");

        holder.txvw_tgl.setText(sdf.format(date));
        holder.txvw_status.setText(transaksi.getStatus_transaksi());
        holder.txvw_status.setTextColor(transaksi.getStatus_transaksi().equals("belum di bayar") ?
                ContextCompat.getColor(getContext(), android.R.color.holo_red_dark) :
                ContextCompat.getColor(getContext(), R.color.colorMain));
        holder.txvw_total.setText("Rp " + Util.convertToCurrency(transaksi.getTotal_harga()));

        holder.rlly_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null){
                    listener.onClickListener(transaksi.getId_transaksi());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTransaksi.size();
    }

    public void setList(List<Transaksi> listTransaksi){
        this.listTransaksi = listTransaksi;
        notifyDataSetChanged();
    }

    public void setOnClickListener(setOnClickListener listener){
        this.listener = listener;
    }

    public interface setOnClickListener {
        void onClickListener(String id);
    }
}
