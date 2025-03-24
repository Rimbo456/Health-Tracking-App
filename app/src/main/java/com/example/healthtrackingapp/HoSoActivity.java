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
                    // Xử lý trường hợp giá trị null
                    gender = document.getBoolean("gender") != null ? document.getBoolean("gender") : false;
                    height = document.getDouble("height") != null ? document.getDouble("height") : 0.0;
                    weight = document.getDouble("weight") != null ? document.getDouble("weight") : 0.0;

                    // Xử lý trường hợp birthYear null
                    if (document.contains("birthYear") && document.getLong("birthYear") != null) {
                        birthYear = document.getLong("birthYear");
                    } else {
                        birthYear = 0; // Giá trị mặc định
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setUserInfo(); // Cập nhật UI ngay lập tức
                        }
                    });
                } else {
                    Log.d("FirestoreData", "Không tìm thấy dữ liệu");
                }
            }
        });

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
            if (!inputText.isEmpty()) {
                try {
                    height = Double.parseDouble(inputText);
                    if (height > 0 && height < 300) {  // Kiểm tra giá trị hợp lệ
                        userInfo.update("height", height);
                        setUserInfo();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HoSoActivity.this, "Chiều cao không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(HoSoActivity.this, "Vui lòng nhập chiều cao hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HoSoActivity.this, "Vui lòng nhập chiều cao!", Toast.LENGTH_SHORT).show();
            }
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
            if (!inputText.isEmpty()) {
                try {
                    weight = Double.parseDouble(inputText);
                    if (weight > 0 && weight < 1000) {  // Kiểm tra giá trị hợp lệ
                        userInfo.update("weight", weight);
                        setUserInfo();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HoSoActivity.this, "Cân nặng không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(HoSoActivity.this, "Vui lòng nhập cân nặng hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HoSoActivity.this, "Vui lòng nhập cân nặng!", Toast.LENGTH_SHORT).show();
            }
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

        // Xử lý trường hợp birthYear là 0 (chưa được thiết lập)
        if (birthYear != 0) {
            edtNhapNam.setText(String.valueOf(birthYear));
        } else {
            edtNhapNam.setText("");
        }

        imgDong.setOnClickListener(view -> dialog.dismiss());
        imgXoaChu.setOnClickListener(view -> edtNhapNam.setText(null));
        btnLuuNam.setOnClickListener(view -> {
            String inputText = edtNhapNam.getText().toString().trim();
            if (!inputText.isEmpty()) {
                try {
                    birthYear = Long.parseLong(inputText);
                    if (birthYear > 1900 && birthYear <= 2025) {  // Kiểm tra giá trị hợp lệ
                        userInfo.update("birthYear", birthYear);
                        setUserInfo();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(HoSoActivity.this, "Năm sinh không hợp lệ!", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(HoSoActivity.this, "Vui lòng nhập năm sinh hợp lệ!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(HoSoActivity.this, "Vui lòng nhập năm sinh!", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }

    public void setUserInfo() {
        if (name != null && !name.isEmpty()) {
            txtTen.setText(name);
        } else {
            txtTen.setText("Chưa nhập");
        }

        if (phone != null && !phone.isEmpty()) {
            txtSDT.setText(phone);
        } else {
            txtSDT.setText("Chưa nhập");
        }

        if (gender != null) {
            txtGioiTinh.setText(gender ? "Nam" : "Nữ");
        } else {
            txtGioiTinh.setText("Chưa chọn");
        }

        if (height > 0) {
            txtChieuCao.setText(String.format("%.1f cm", height));
        } else {
            txtChieuCao.setText("Chưa nhập");
        }

        if (weight > 0) {
            txtCanNang.setText(String.format("%.1f kg", weight));
        } else {
            txtCanNang.setText("Chưa nhập");
        }

        if (birthYear > 0) {
            txtNamSinh.setText(String.valueOf(birthYear));
        } else {
            txtNamSinh.setText("Chưa nhập");
        }
    }
}