package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    Button loginBtn;
    TextInputEditText emailPengguna, passwordPengguna;
    TextView tvSignup;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FirebaseAuth.getInstance().signOut();

        loginBtn = findViewById(R.id.btnLogin);
        emailPengguna = findViewById(R.id.email);
        passwordPengguna = findViewById(R.id.password);
        tvSignup = findViewById(R.id.SignUp);

        mAuth = FirebaseAuth.getInstance();

        loginBtn.setOnClickListener(view -> {
            String email = emailPengguna.getText().toString().trim();
            String password = passwordPengguna.getText().toString().trim();
            loginUser(email, password);
        });

        tvSignup.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        // Cek apakah user sudah login
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Sudah login, langsung ke Home
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void loginUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser fuser = mAuth.getCurrentUser();
                        if (fuser != null) {
                            Toast.makeText(LoginActivity.this, "Login berhasil", Toast.LENGTH_SHORT).show();

                            // Pindah ke HomeActivity
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("USER_EMAIL", fuser.getEmail());
                            startActivity(intent);
                            finish();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Login gagal: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Login Error: " + task.getException().getMessage());
                    }
                });
    }
}
