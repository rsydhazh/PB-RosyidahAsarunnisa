package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.google.firebase.auth.FirebaseAuth;

public class SettingsActivity extends AppCompatActivity {

    private TextView editProfileText, logoutText;
    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;

    private static final String PREFS_NAME = "settings_prefs";
    private static final String DARK_MODE_KEY = "dark_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initViews();
        setupDarkMode();
        setupEditProfile();
        setupLogout();
    }

    private void initViews() {
        editProfileText = findViewById(R.id.editProfile);
        logoutText = findViewById(R.id.logout);
        darkModeSwitch = findViewById(R.id.switchDarkMode);
    }

    private void setupDarkMode() {
        sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        boolean isDarkMode = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        darkModeSwitch.setChecked(isDarkMode);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(DARK_MODE_KEY, isChecked);
            editor.apply();

            AppCompatDelegate.setDefaultNightMode(
                    isChecked ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
            );
        });
    }

    private void setupEditProfile() {
        // Sementara fitur edit profile belum aktif
        // Bisa nonaktif atau kasih info ke pengguna

        editProfileText.setOnClickListener(v -> {
            Toast.makeText(this, "Besok ya kak fiturnya aktif :)", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupLogout() {
        logoutText.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
