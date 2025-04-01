package com.example.healthtrackingapp.data.models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrackingapp.R;

import java.util.List;

public class tinhTrangSKAdapter extends RecyclerView.Adapter<tinhTrangSKAdapter.ViewHolder> {
    private List<String> danhSachTinhTrang;
    private boolean[] checkedItems;
    private OnItemCheckedChangeListener listener;

    public tinhTrangSKAdapter(List<String> danhSachTinhTrang) {
        this.danhSachTinhTrang = danhSachTinhTrang;
        this.checkedItems = new boolean[danhSachTinhTrang.size()];
    }

    public void setCheckedItems(boolean[] checkedItems) {
        this.checkedItems = checkedItems;
        notifyDataSetChanged();
    }

    public boolean[] getCheckedItems() {
        return checkedItems;
    }

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tinh_trang_suc_khoe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String tinhTrang = danhSachTinhTrang.get(position);
        holder.tvItemName.setText(tinhTrang);
        holder.switchToggle.setChecked(checkedItems[position]);

        holder.switchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            checkedItems[position] = isChecked;
            if (listener != null) {
                listener.onItemCheckedChanged(position, isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return danhSachTinhTrang.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemName;
        SwitchCompat switchToggle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvItemName = itemView.findViewById(R.id.tvItemTinhTrangSK);
            switchToggle = itemView.findViewById(R.id.switchToggle);
        }
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChanged(int position, boolean isChecked);
    }
}