package com.example.healthtrackingapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthtrackingapp.data.databases.FirestoreHelper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity2 extends AppCompatActivity {
    TableRow tbrHoSo;
    ImageView imgBanner;
    TextView tvUserName, tvEmail;

    FirestoreHelper firestoreHelper = new FirestoreHelper();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

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

        tbrHoSo = findViewById(R.id.tbrHoSo);
        tvUserName = findViewById(R.id.tvUserName);
        tvEmail = findViewById(R.id.tvEmail);

        if (user != null) {
            String userId = user.getUid();
            String email = user.getEmail();

            tvEmail.setText(email);

            firestoreHelper.getUserData(userId, documentSnapshot -> {
                if (documentSnapshot.exists() && documentSnapshot.contains("username")) {
                    String username = documentSnapshot.getString("username");
                    tvUserName.setText(username);
                } else {
                    tvUserName.setText(user.getDisplayName());
                }
            }, e -> Log.e("Firestore", "Lỗi lấy dữ liệu", e));
        }

        tbrHoSo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, HoSoActivity.class);
                startActivity(intent);
            }
        });

    }
}