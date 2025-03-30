package com.example.healthtrackingapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrackingapp.data.models.tinhTrangSKAdapter;

import java.util.Arrays;
import java.util.List;

public class TinhTrangSucKhoeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tinh_trang_suc_khoe);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_tinh_trang_sk), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recyclerView = findViewById(R.id.list_tinh_trang_SK);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<String> danhSachTinhTrang = Arrays.asList(
                "Tiểu đường",
                "Tăng huyết áp",
                "Mỡ máu cao",
                "Viêm ruột mạn tính (IBD)",
                "Hội chứng ruột kích thích (IBS)",
                "Đau thắt ngực",
                "Suy tim",
                "Bệnh Crohn's",
                "Viêm loét đại tràng",
                "Bệnh Alzheimer",
                "Hen suyễn",
                "Bệnh thận mạn tính",
                "Bệnh phổi tắc nghẽn mạn tính",
                "Bệnh chàm (Viêm da cơ địa)",
                "Bệnh gút",
                "Bệnh trĩ",
                "Chứng ngủ rũ",
                "Béo phì",
                "Viêm khớp dạng thấp"
        );

        tinhTrangSKAdapter adapter = new tinhTrangSKAdapter(danhSachTinhTrang);
        recyclerView.setAdapter(adapter);
    }
}