package com.example.healthtrackingapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HoTroVaThongTinActivity extends AppCompatActivity {

    ImageView imgQuayLai;
    Button btnHoTro, btnBaoMat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ho_tro_va_thong_tin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_ho_tro_va_thong_tin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgQuayLai = findViewById(R.id.imgQuayLai);
        btnHoTro = findViewById(R.id.btnHoTro);
        btnBaoMat = findViewById(R.id.btnBaoMat);

        imgQuayLai.setOnClickListener(view -> finish());

        btnHoTro.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:nguyenrimbo2004@gmail.com"));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Hỗ trợ ứng dụng");
            startActivity(Intent.createChooser(emailIntent, "Chọn ứng dụng email"));
        });

        btnBaoMat.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://yourapp.com/privacy"));
            Toast.makeText(HoTroVaThongTinActivity.this, "Chưa có", Toast.LENGTH_SHORT);
            startActivity(browserIntent);
        });
    }
}