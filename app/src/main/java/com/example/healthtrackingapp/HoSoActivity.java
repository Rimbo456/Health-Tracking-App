package com.example.healthtrackingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.atomic.AtomicBoolean;

public class HoSoActivity extends Activity {
    boolean gioiTinh = false;
    String ten = "nhut";
    TextView txtTen, txtSDT, txtGioiTinh, txtChieuCao, txtNamSinh;
    TableRow tbrTen, tbrSDT, tbrGioiTinh, tbrChieuCao, tbrNamSinh;
    ImageView imgQuayLai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_so);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgQuayLai = findViewById(R.id.imgQuayLai);
        tbrTen = findViewById(R.id.tbrTen);
        tbrSDT = findViewById(R.id.tbrSDT);
        tbrGioiTinh = findViewById(R.id.tbrGioiTinh);
        tbrChieuCao = findViewById(R.id.tbrChieuCao);
        tbrNamSinh = findViewById(R.id.tbrNamSinh);
        txtTen = findViewById(R.id.txtTen);
        txtSDT = findViewById(R.id.txtSDT);
        txtGioiTinh = findViewById(R.id.txtGioiTinh);
        txtChieuCao = findViewById(R.id.txtChieuCao);
        txtNamSinh = findViewById(R.id.txtNamSinh);

        imgQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HoSoActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        tbrTen.setOnClickListener(view -> showEnterNameDialog(ten));

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

    public interface GenderSelectListener {
        void onGenderSelected(boolean isMale);
    }
//    public interface NameSelectListener {
//        void onGenderSelected(String name);
//    }
}