package com.example.healthtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TableRow;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity2 extends AppCompatActivity {
    TableRow tbrHoSo, tbrHoTro, tbrVeChungToi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tbrHoTro = findViewById(R.id.tbrHoTro);
        tbrVeChungToi = findViewById(R.id.tbrVeChungToi);

        tbrHoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, HoSoActivity.class);
                startActivity(intent);
            }
        });

        tbrHoTro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, HoTroVaThongTinActivity.class);
                startActivity(intent);
            }
        });

        tbrVeChungToi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, GioiThieuActivity.class);
                startActivity(intent);
            }
        });

    }
}