package com.example.healthtrackingapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class XacNhanSDTActivity extends AppCompatActivity {
    TextView txtSDTXacThuc;
    EditText otp_1, otp_2, otp_3, otp_4, otp_5, otp_6;
    Button btnXacNhanOTP;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_xac_nhan_sdt);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_xac_nhan_sdt), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        txtSDTXacThuc = findViewById(R.id.txtSDTXacThuc);
        otp_1 = findViewById(R.id.otp_1);
        otp_2 = findViewById(R.id.otp_2);
        otp_3 = findViewById(R.id.otp_3);
        otp_4 = findViewById(R.id.otp_4);
        otp_5 = findViewById(R.id.otp_5);
        otp_6 = findViewById(R.id.otp_6);
        btnXacNhanOTP = findViewById(R.id.btnXacNhanOTP);

        Intent intent = getIntent();
        String sdt = intent.getStringExtra("SDT");

        txtSDTXacThuc.setText(sdt);

        btnXacNhanOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp1 = otp_1.getText().toString().trim();
                String otp2 = otp_2.getText().toString().trim();
                String otp3 = otp_3.getText().toString().trim();
                String otp4 = otp_4.getText().toString().trim();
                String otp5 = otp_5.getText().toString().trim();
                String otp6 = otp_6.getText().toString().trim();

                if (otp1.isEmpty()||otp2.isEmpty()||otp3.isEmpty()||otp4.isEmpty()||otp5.isEmpty()||otp6.isEmpty())
                    Toast.makeText(XacNhanSDTActivity.this, "Nhập mã OPT được gửi về số điện thoại để xác minh", Toast.LENGTH_SHORT).show();
                else {
                    String maOTP=otp1+otp2+otp3+otp4+otp5+otp6;
                    if(maOTP.equals("111111")){
                        Intent pushintent = new Intent();
                        pushintent.putExtra("SDT", sdt);
                        finish();
                    }
                    else
                        Toast.makeText(XacNhanSDTActivity.this, "Sai mã xác minh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}