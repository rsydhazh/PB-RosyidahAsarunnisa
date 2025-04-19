package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private TextView profileName, profileEmail;
    private Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.nama);
        profileEmail = findViewById(R.id.email);
        btnLogout = findViewById(R.id.btnLogout);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        if (user != null) {
            profileEmail.setText(user.getEmail());
            profileName.setText(user.getDisplayName() != null ? user.getDisplayName() : "Email Pengguna: ");
        }

        btnLogout.setOnClickListener(v -> {
            auth.signOut();
            startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            finish();
        });
    }
}
