package app.proyek.qrcode.activity.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.proyek.qrcode.R;
import app.proyek.qrcode.model.Item;
import app.proyek.qrcode.util.Util;

import java.util.List;

/**
 * Created by WINDOWS 10 on 10/05/2017.
 */

public class ItemCartAdapter extends RecyclerView.Adapter<ItemCartAdapter.ViewHolder>{

    private Context context;
    private List<Item> listItem;

    public ItemCartAdapter(Context context, List<Item> listItem) {
        this.context = context;
        this.listItem = listItem;
    }

    private Context getContext() {
        return context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private TextView item_nama;
        private TextView item_harga;
        private TextView item_qty;

        public ViewHolder(View itemView) {
            super(itemView);
            item_nama = (TextView) itemView.findViewById(R.id.item_nama);
            item_harga = (TextView) itemView.findViewById(R.id.item_harga);
            item_qty = (TextView) itemView.findViewById(R.id.item_qty);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_cart, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = listItem.get(position);
        holder.item_nama.setText(item.getNama_barang());
        holder.item_harga.setText("Rp " + Util.convertToCurrency(item.getHarga()));
        holder.item_qty.setText(item.getQty()+"");
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }
}
