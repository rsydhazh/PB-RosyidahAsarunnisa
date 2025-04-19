package com.example.myapplication;

import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeActivity extends AppCompatActivity {

    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        detailImage = findViewById(R.id.imgResep);

        // Ambil ID gambar dari Intent
        int imageResId = getIntent().getIntExtra("IMAGE_ID", 0);

        // Set gambar ke ImageView
        detailImage.setImageResource(imageResId);
    }
}
