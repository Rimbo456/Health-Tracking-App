package com.example.healthtrackingapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthtrackingapp.data.models.tinhTrangSKAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TinhTrangSucKhoeActivity extends AppCompatActivity {

    ImageView imgQuayLai;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth auth = FirebaseAuth.getInstance();
    FirebaseUser user = auth.getCurrentUser();
    String userId = user.getUid();
    CollectionReference healthConditionsCollection = db.collection("users").document(userId).collection("healthConditions");
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

        imgQuayLai = findViewById(R.id.imgQuayLai);

        RecyclerView recyclerView = findViewById(R.id.list_tinh_trang_SK);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        if (user != null) {
            healthConditionsCollection.get().addOnSuccessListener(queryDocumentSnapshots -> {
                boolean[] checkedItems = new boolean[danhSachTinhTrang.size()];
                for (DocumentSnapshot document : queryDocumentSnapshots) {
                    String diseaseName = document.getId();
                    boolean state = document.getBoolean("state");
                    int position = danhSachTinhTrang.indexOf(diseaseName);
                    if (position != -1) {
                        checkedItems[position] = state;
                    }
                }
                adapter.setCheckedItems(checkedItems);
            }).addOnFailureListener(e -> {
                Log.e("TinhTrangSucKhoe", "Error getting health conditions: " + e.getMessage());
            });

            adapter.setOnItemCheckedChangeListener(new tinhTrangSKAdapter.OnItemCheckedChangeListener() {
                @Override
                public void onItemCheckedChanged(int position, boolean isChecked) {
                    String tinhTrang = danhSachTinhTrang.get(position);
                    saveHealthCondition(tinhTrang, isChecked);
                }
            });
        } else {
            Log.e("TinhTrangSucKhoe", "User not logged in");
        }

        imgQuayLai.setOnClickListener(v -> onBackPressed());
    }

    private void saveHealthCondition(String conditionName, boolean isChecked) {
        Map<String, Object> healthCondition = new HashMap<>();
        healthCondition.put("disease_name", conditionName);
        healthCondition.put("state", isChecked);

        healthConditionsCollection.document(conditionName).set(healthCondition)
                .addOnSuccessListener(aVoid -> {
                    Log.d("TinhTrangSucKhoe", "Health condition saved successfully");
                })
                .addOnFailureListener(e -> {
                    Log.e("TinhTrangSucKhoe", "Error saving health condition: " + e.getMessage());
                });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("checkedItems", adapter.getCheckedItems());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            boolean[] checkedItems = savedInstanceState.getBooleanArray("checkedItems");
            if (checkedItems != null) {
                adapter.setCheckedItems(checkedItems);
            }
        }
    }
}