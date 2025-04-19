package com.example.myapplication;

import android.app.AlertDialog;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    Button btnmemo, btnlihatResep1, btnlihatResep2, btnlihatResep3, btnlihatResep4, btnlihatResep5;
    TextView textMemo;
    EditText editMemo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnlihatResep1 = findViewById(R.id.btnlihatResep1);
        btnlihatResep2 = findViewById(R.id.btnlihatResep2);
        btnlihatResep3 = findViewById(R.id.btnlihatResep3);
        btnlihatResep4 = findViewById(R.id.btnlihatResep4);
        btnlihatResep5 = findViewById(R.id.btnlihatResep5);
        btnmemo = findViewById(R.id.btnMemo);
        editMemo = findViewById(R.id.editMemo);

        btnlihatResep1.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
            intent.putExtra("IMAGE_ID", R.drawable.gadogado); // ganti sesuai gambar
            startActivity(intent);
        });
        btnlihatResep2.setOnClickListener(v ->{
            Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
            intent.putExtra("IMAGE_ID", R.drawable.gudeggg);
            startActivity(intent);
        });
        btnlihatResep3.setOnClickListener(v ->{
            Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
            intent.putExtra("IMAGE_ID", R.drawable.mieaceh);
            startActivity(intent);
        });
        btnlihatResep4.setOnClickListener(v ->{
            Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
            intent.putExtra("IMAGE_ID", R.drawable.pempekpalembang);
            startActivity(intent);
        });
        btnlihatResep5.setOnClickListener(v ->{
            Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
            intent.putExtra("IMAGE_ID", R.drawable.sateayam);
            startActivity(intent);
        });

        btnmemo.setOnClickListener(v -> {
            String memo = editMemo.getText().toString().trim();

            if (memo.isEmpty()) {
                Toast.makeText(this, "Memo belum diisi!", Toast.LENGTH_SHORT).show();
            } else {
                // Simpan ke database kalau mau, atau tampilkan toast aja
                Toast.makeText(this, "Memo Tersimpan: " + memo, Toast.LENGTH_LONG).show();
                // Clear input setelah simpan
                editMemo.setText("");
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setSelectedItemId(R.id.home); // ID item di menu/navbar.xml

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.home) {
                return true;
            } else if (itemId == R.id.profile) {
                startActivity(new Intent(HomeActivity.this, ProfileActivity.class));
                return true;
            } else if (itemId == R.id.settings) {
                startActivity(new Intent(HomeActivity.this, SettingsActivity.class));
                return true;
            }

            return false;
        });

    }

    private void bukaDetail(int imageResId) {
        Intent intent = new Intent(HomeActivity.this, RecipeActivity.class);
        intent.putExtra("IMAGE_ID", imageResId);
        startActivity(intent);
    }
}
