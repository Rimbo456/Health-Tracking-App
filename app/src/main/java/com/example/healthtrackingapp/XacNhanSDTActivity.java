package com.example.healthtrackingapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class XacNhanSDTActivity extends Activity {
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

        setupOtpAutoMove(otp_1, otp_2);
        setupOtpAutoMove(otp_2, otp_3);
        setupOtpAutoMove(otp_3, otp_4);
        setupOtpAutoMove(otp_4, otp_5);
        setupOtpAutoMove(otp_5, otp_6);


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
                    Toast.makeText(XacNhanSDTActivity.this, "Nhập mã OTP được gửi về số điện thoại "+sdt+" để xác minh", Toast.LENGTH_SHORT).show();
                else {
                    if(OTP.equals("111111")){
                        userInfo.update("phone", sdt);
                        finish();
                    }
                    else
                        Toast.makeText(XacNhanSDTActivity.this, "Sai mã xác minh", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // Hàm chuyển sang ô tiếp theo sau khi nhập số
    private void setupOtpAutoMove(EditText current, EditText next) {
        current.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() >= 1) {
                    next.requestFocus(); // Chuyển đến ô tiếp theo
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // Nếu người dùng nhấn "Backspace" trên bàn phím, quay lại ô trước
        current.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                current.setText(""); // Xóa số hiện tại
                current.clearFocus();
                return true;
            }
            return false;
        });
    }
}

