package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.myapplication.model.userDetails;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";

    private TextInputEditText emailPengguna, passwordPengguna;
    private Button signupBtn;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            v.setPadding(
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).left,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).top,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).right,
                    insets.getInsets(WindowInsetsCompat.Type.systemBars()).bottom
            );
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        emailPengguna = findViewById(R.id.email);
        passwordPengguna = findViewById(R.id.password);
        signupBtn = findViewById(R.id.btnSignUp);

        signupBtn.setOnClickListener(view -> {
            String email = String.valueOf(emailPengguna.getText());
            String password = String.valueOf(passwordPengguna.getText());

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(RegisterActivity.this, "Enter Email", Toast.LENGTH_LONG).show();
                emailPengguna.requestFocus();
            } else if (TextUtils.isEmpty(password)) {
                Toast.makeText(RegisterActivity.this, "Enter Password", Toast.LENGTH_LONG).show();
                passwordPengguna.requestFocus();
            } else {
                ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this);
                progressDialog.setMessage("Mendaftarkan pengguna...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                registerUser(email, password, progressDialog);
            }
        });

    }



    private void registerUser(String email, String password, ProgressDialog progressDialog) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            String uid = firebaseUser.getUid();

                            userDetails user = new userDetails(uid, email);

                            DatabaseReference reference = FirebaseDatabase.getInstance()
                                    .getReference("Users");

                            reference.child(uid).setValue(user)
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(this, "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(this, HomeActivity .class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            Log.e(TAG, "Database error", task1.getException());
                                            Toast.makeText(this, "Gagal menyimpan data pengguna", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    } else {
                        handleAuthError(task.getException());
                    }
                });
    }

    private void handleAuthError(Exception e) {
        if (e instanceof FirebaseAuthUserCollisionException) {
            Toast.makeText(this, "Email sudah terdaftar", Toast.LENGTH_LONG).show();
        } else if (e instanceof FirebaseAuthWeakPasswordException) {
            passwordPengguna.setError("Password terlalu lemah");
            passwordPengguna.requestFocus();
        } else if (e instanceof FirebaseAuthInvalidCredentialsException) {
            emailPengguna.setError("Email tidak valid");
            emailPengguna.requestFocus();
        } else {
            Toast.makeText(this, "Pendaftaran gagal: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        Log.e(TAG, "Auth error", e);
    }
}