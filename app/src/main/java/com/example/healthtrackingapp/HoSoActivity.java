package com.example.healthtrackingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HoSoActivity extends Activity {
    Boolean gender;
    long birthYear;
    double height, weight;
    TextView txtTen, txtEmail, txtSDT, txtGioiTinh, txtChieuCao, txtCanNang, txtNamSinh;
    TableRow tbrTen, tbrSDT, tbrGioiTinh, tbrChieuCao, tbrCanNang, tbrNamSinh;
    ImageView imgQuayLai;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userId = user.getUid(), name, phone;
    DocumentReference userInfo = db.collection("users").document(userId);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_ho_so), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgQuayLai = findViewById(R.id.imgQuayLai);
        tbrTen = findViewById(R.id.tbrTen);
        tbrSDT = findViewById(R.id.tbrSDT);
        tbrGioiTinh = findViewById(R.id.tbrGioiTinh);
        tbrChieuCao = findViewById(R.id.tbrChieuCao);
        tbrCanNang = findViewById(R.id.tbrCanNang);
        tbrNamSinh = findViewById(R.id.tbrNamSinh);
        txtTen = findViewById(R.id.txtTen);
        txtEmail = findViewById(R.id.txtEmail);
        txtSDT = findViewById(R.id.txtSDT);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtChieuCao = findViewById(R.id.txtChieuCao);
        txtCanNang = findViewById(R.id.txtCanNang);
        txtNamSinh = findViewById(R.id.txtNamSinh);

        imgQuayLai.setOnClickListener(view -> finish());

        userInfo.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot document) {
                if (document.exists()) {
                    name = document.getString("name");
                    phone = document.getString("phone");
                    gender = document.getBoolean("gender");
                    height = document.getDouble("height");
                    weight = document.getDouble("weight");
                    birthYear = document.getLong("birthYear");
                } else {
                    Log.d("FirestoreData", "Không tìm thấy dữ liệu");
                }
            }
        });
        setUserInfo();

        tbrTen.setOnClickListener(view -> showEnterNameDialog());
        tbrSDT.setOnClickListener(view -> showEnterPhoneDialog());
        tbrGioiTinh.setOnClickListener(view -> {
            showSelectGenderDialog();
        });
        tbrChieuCao.setOnClickListener(view -> showEnterHeightDialog());
        tbrCanNang.setOnClickListener(view -> showEnterWeightDialog());
        tbrNamSinh.setOnClickListener(view -> showEnterBirthYearDialog());
    }

    public void showEnterNameDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_ten);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapTen = dialog.findViewById(R.id.edtNhapTen);
        Button btnLuuTen = dialog.findViewById(R.id.btnLuuTen);
        userInfo.get().addOnSuccessListener(document -> name = document.getString("name"));

        edtNhapTen.setText(name);
        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapTen.setText(""));
        btnLuuTen.setOnClickListener(v -> {
            String newName = edtNhapTen.getText().toString().trim();
            if (newName.isEmpty()) {
                Toast.makeText(HoSoActivity.this, "Tên không được để trống!", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                edtNhapTen.setText(newName);
                userInfo.update("name", newName);
                setUserInfo();
            }
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterPhoneDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_sdt);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapSDT = dialog.findViewById(R.id.edtNhapSDT);
        Button btnLuuSDT = dialog.findViewById(R.id.btnLuuSDT);
        userInfo.get().addOnSuccessListener(document -> phone = document.getString("phone"));

        edtNhapSDT.setText(phone);
        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapSDT.setText(null));
        String soDienThoai = edtNhapSDT.getText().toString().trim();
        btnLuuSDT.setOnClickListener(view -> {
            if (!soDienThoai.isEmpty()) {
                Intent intentSDT = new Intent(HoSoActivity.this, XacNhanSDTActivity.class);
                intentSDT.putExtra("sdt", soDienThoai);
                startActivity(intentSDT);
            } else {
                Toast.makeText(this, "Vui lòng nhập số điện thoại!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showSelectGenderDialog(){
        //false: female       true: male
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_chon_gioi_tinh);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        LinearLayout btnFemale = dialog.findViewById(R.id.btnFemale);
        LinearLayout btnMale = dialog.findViewById(R.id.btnMale);
        Button btnLuuGioiTinh = dialog.findViewById(R.id.btnLuuGioiTinh);

        if (gender){
            btnFemale.setBackgroundResource(R.drawable.rounded_corner);
            btnMale.setBackgroundResource(R.drawable.btn_selected_male);
        }
        else{
            btnFemale.setBackgroundResource(R.drawable.btn_selected_female);
            btnMale.setBackgroundResource(R.drawable.rounded_corner);
        }

        imgDong.setOnClickListener(view -> dialog.dismiss());
        btnFemale.setOnClickListener(view -> {
            gender = false;
            btnFemale.setBackgroundResource(R.drawable.btn_selected_female);
            btnMale.setBackgroundResource(R.drawable.rounded_corner);
        });
        btnMale.setOnClickListener(view -> {
            gender = true;
            btnFemale.setBackgroundResource(R.drawable.rounded_corner);
            btnMale.setBackgroundResource(R.drawable.btn_selected_male);
        });
        btnLuuGioiTinh.setOnClickListener(v -> {
            userInfo.update("gender", gender);
            setUserInfo();
            dialog.dismiss();
        });
        dialog.show();
    }

    public void showEnterHeightDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_chieu_cao);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapChieuCao = dialog.findViewById(R.id.edtNhapChieuCao);
        Button btnLuuChieuCao = dialog.findViewById(R.id.btnLuuChieuCao);

        edtNhapChieuCao.setText(String.valueOf(height));

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapChieuCao.setText(null));
        btnLuuChieuCao.setOnClickListener(view -> {
            String inputText = edtNhapChieuCao.getText().toString().trim();
            height = Double.parseDouble(inputText);
            userInfo.update("height", height);
            setUserInfo();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterWeightDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_can_nang);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapCanNang = dialog.findViewById(R.id.edtNhapCanNang);
        Button btnLuuCanNang = dialog.findViewById(R.id.btnLuuCanNang);

        edtNhapCanNang.setText(String.valueOf(weight));

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapCanNang.setText(null));
        btnLuuCanNang.setOnClickListener(view -> {
            String inputText = edtNhapCanNang.getText().toString().trim();
            weight = Double.parseDouble(inputText);
            userInfo.update("weight", weight);
            setUserInfo();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterBirthYearDialog(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_nam_sinh);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapNam = dialog.findViewById(R.id.edtNhapNam);
        Button btnLuuNam = dialog.findViewById(R.id.btnLuuNam);

        edtNhapNam.setText(String.valueOf(birthYear));

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapNam.setText(null));
        btnLuuNam.setOnClickListener(view -> {
            String inputText = edtNhapNam.getText().toString().trim();
            birthYear = Long.parseLong(inputText);
            userInfo.update("birthYear", birthYear);
            setUserInfo();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void setUserInfo (){
        txtTen.setText(name);
        txtSDT.setText(phone);
        if (gender)
            txtGioiTinh.setText("Nam");
        else
            txtGioiTinh.setText("Nữ");
        txtChieuCao.setText(getString(R.string.chieu_cao, height));
        txtCanNang.setText(getString(R.string.can_nang, weight));
        txtNamSinh.setText(String.valueOf(birthYear));
    }
}