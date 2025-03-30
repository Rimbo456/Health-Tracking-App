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

    public tinhTrangSKAdapter(List<String> danhSachTinhTrang) {
        this.danhSachTinhTrang = danhSachTinhTrang;
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

        // Xử lý sự kiện bật/tắt
        holder.switchToggle.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // TODO: Thêm logic xử lý khi bật/tắt Switch
            if (isChecked) {
                System.out.println(tinhTrang + " ĐÃ BẬT");
            } else {
                System.out.println(tinhTrang + " ĐÃ TẮT");
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
}
