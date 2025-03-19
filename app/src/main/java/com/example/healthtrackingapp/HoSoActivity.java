package com.example.healthtrackingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
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

import java.util.concurrent.atomic.AtomicBoolean;

public class HoSoActivity extends Activity {
    boolean gioiTinh = true;
    String ten = "nhut", sdt = "0395511743", email = "feniknhut151104@gmail.com";
    int chieuCao = 165, canNang = 50, namSinh = 2004;
    TextView txtTen, txtEmail, txtSDT, txtGioiTinh, txtChieuCao, txtCanNang, txtNamSinh;
    TableRow tbrTen, tbrSDT, tbrGioiTinh, tbrChieuCao, tbrCanNang, tbrNamSinh;
    ImageView imgQuayLai;

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

        txtTen.setText(ten);
        txtEmail.setText(email);
        Intent intentSDT = getIntent();
        String SDT = intentSDT.getStringExtra("SDT");
        if (SDT != null && !SDT.isEmpty()) sdt = SDT;
        txtSDT.setText(sdt);
        if (gioiTinh)
            txtGioiTinh.setText("Nam");
        else
            txtGioiTinh.setText("Nữ");
        txtChieuCao.setText(getString(R.string.chieu_cao, chieuCao));
        txtCanNang.setText(getString(R.string.can_nang, canNang));
        txtNamSinh.setText(String.valueOf(namSinh));

        imgQuayLai.setOnClickListener(view -> finish());

        tbrTen.setOnClickListener(view -> showEnterNameDialog(ten));
        tbrSDT.setOnClickListener(view -> showEnterPhoneDialog(sdt));
        tbrGioiTinh.setOnClickListener(view -> {
            showSelectGenderDialog(gioiTinh, new GenderSelectListener() {
                @Override
                public void onGenderSelected(boolean isMale) {
                    gioiTinh = isMale;
                    if (gioiTinh) {
                        txtGioiTinh.setText("Nam");
                    } else {
                        txtGioiTinh.setText("Nữ");
                    }
                }
            });
        });
        tbrChieuCao.setOnClickListener(view -> showEnterHeightDialog(chieuCao));
        tbrCanNang.setOnClickListener(view -> showEnterWeightDialog(canNang));
        tbrNamSinh.setOnClickListener(view -> showEnterBirthYearDialog(namSinh));
    }

    public void showEnterNameDialog(String ten){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_ten);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapTen = dialog.findViewById(R.id.edtNhapTen);
        Button btnLuuTen = dialog.findViewById(R.id.btnLuuTen);
        
        edtNhapTen.setText(ten);

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapTen.setText(null));
        btnLuuTen.setOnClickListener(view -> {
            txtTen.setText(edtNhapTen.getText());
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterPhoneDialog(String sdt){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_sdt);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapSDT = dialog.findViewById(R.id.edtNhapSDT);
        Button btnLuuSDT = dialog.findViewById(R.id.btnLuuSDT);

        edtNhapSDT.setText(sdt);

//        btnLuuSDT.setOnClickListener(view -> {
//            String soDienThoai = edtNhapSDT.getText().toString().trim();
//
//            if (soDienThoai.isEmpty()) {
//                Toast.makeText(HoSoActivity.this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            else {
//                Intent intent = new Intent(HoSoActivity.this, XacNhanSDTActivity.class);
//                intent.putExtra("SDT", soDienThoai);
//                startActivity(intent);
//                dialog.dismiss();
//            }
//        });
        String soDienThoai = edtNhapSDT.getText().toString().trim();
        btnLuuSDT.setOnClickListener(view -> {
            txtSDT.setText(soDienThoai);
            dialog.dismiss();
        });

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapSDT.setText(null));

        dialog.show();
    }

    public void showSelectGenderDialog(boolean gioiTinh, GenderSelectListener listener){
        //false: female       true: male
        AtomicBoolean gt= new AtomicBoolean(gioiTinh);

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_chon_gioi_tinh);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        LinearLayout btnFemale = dialog.findViewById(R.id.btnFemale);
        LinearLayout btnMale = dialog.findViewById(R.id.btnMale);
        Button btnLuuGioiTinh = dialog.findViewById(R.id.btnLuuGioiTinh);

        if (gt.get()){
            btnFemale.setBackgroundResource(R.drawable.rounded_corner);
            btnMale.setBackgroundResource(R.drawable.btn_selected_male);
        }
        else{
            btnFemale.setBackgroundResource(R.drawable.btn_selected_female);
            btnMale.setBackgroundResource(R.drawable.rounded_corner);
        }

        imgDong.setOnClickListener(view -> dialog.dismiss());
        btnFemale.setOnClickListener(view -> {
            gt.set(false);
            btnFemale.setBackgroundResource(R.drawable.btn_selected_female);
            btnMale.setBackgroundResource(R.drawable.rounded_corner);
        });
        btnMale.setOnClickListener(view -> {
            gt.set(true);
            btnFemale.setBackgroundResource(R.drawable.rounded_corner);
            btnMale.setBackgroundResource(R.drawable.btn_selected_male);
        });
        btnLuuGioiTinh.setOnClickListener(v -> {
            listener.onGenderSelected(gt.get());
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterHeightDialog(int chieuCao){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_chieu_cao);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapChieuCao = dialog.findViewById(R.id.edtNhapChieuCao);
        Button btnLuuChieuCao = dialog.findViewById(R.id.btnLuuChieuCao);

        edtNhapChieuCao.setText(String.valueOf(chieuCao));

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapChieuCao.setText(null));
        btnLuuChieuCao.setOnClickListener(view -> {
            txtChieuCao.setText(getString(R.string.chieu_cao, chieuCao));
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterWeightDialog(int canNang){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_can_nang);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapCanNang = dialog.findViewById(R.id.edtNhapCanNang);
        Button btnLuuCanNang = dialog.findViewById(R.id.btnLuuCanNang);

        edtNhapCanNang.setText(String.valueOf(canNang));

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapCanNang.setText(null));
        btnLuuCanNang.setOnClickListener(view -> {
            txtCanNang.setText(getString(R.string.can_nang, canNang));
            dialog.dismiss();
        });

        dialog.show();
    }

    public void showEnterBirthYearDialog(int namSinh){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.cus_dialog_nhap_nam_sinh);

        ImageView imgDong = dialog.findViewById(R.id.imgDong);
        ImageView imgXoaChu = dialog.findViewById(R.id.imgXoaChu);
        EditText edtNhapNam = dialog.findViewById(R.id.edtNhapNam);
        Button btnLuuNam = dialog.findViewById(R.id.btnLuuNam);

        edtNhapNam.setText(String.valueOf(namSinh));

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapNam.setText(null));
        btnLuuNam.setOnClickListener(view -> {
            txtNamSinh.setText(edtNhapNam.getText());
            dialog.dismiss();
        });

        dialog.show();
    }

    public interface GenderSelectListener {
        void onGenderSelected(boolean isMale);
    }
}