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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class XacNhanSDTActivity extends AppCompatActivity {
    TextView txtSDTXacThuc;
    EditText otp_1, otp_2, otp_3, otp_4, otp_5, otp_6;
    Button btnXacNhanOTP;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userId = user.getUid(), name, phone;
    DocumentReference userInfo = db.collection("users").document(userId);

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
        String sdt = intent.getStringExtra("sdt");

        txtSDTXacThuc.setText(sdt);

        btnXacNhanOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String OTP = otp_1.getText().toString().trim()
                            +otp_2.getText().toString().trim()
                            +otp_3.getText().toString().trim()
                            +otp_4.getText().toString().trim()
                            +otp_5.getText().toString().trim()
                            +otp_6.getText().toString().trim();

                if (OTP.isEmpty())
                    otp_1.setError("Nhập mã OTP được gửi về số điện thoại "+sdt+" để xác minh");
                else {
                    if(OTP.equals("111111")){
                        userInfo.update("phone", sdt);
                        finish();
                    }
                    else
                        otp_1.setError("Sai mã xác minh");
                }
            }
        });
    }
}