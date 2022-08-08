package com.example.androidpractical.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.androidpractical.R;

public class MainActivity extends AppCompatActivity {
    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String PASSWORD_KEY = "password_key";
    SharedPreferences sharedpreferences;
    private String email, password;
    private Button loginBtn;
    private EditText emailEdt;
    private EditText passwordEdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        emailEdt = findViewById(R.id.idEdtEmail);
        passwordEdt = findViewById(R.id.idEdtPassword);
        loginBtn = findViewById(R.id.idBtnLogin);
        sharedPrefer();
        onClickLogIn();
    }

    private void onClickLogIn() {
        loginBtn.setOnClickListener(v -> {
            if (TextUtils.isEmpty(emailEdt.getText().toString()) && TextUtils.isEmpty
                    (passwordEdt.getText().toString())) {
                Toast.makeText(MainActivity.this, "Please Enter Email and Password"
                        , Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.putString(EMAIL_KEY, emailEdt.getText().toString());
                editor.putString(PASSWORD_KEY, passwordEdt.getText().toString());
                editor.apply();
                Intent i = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(i);
                finish();
            }
        });
    }

    private void sharedPrefer() {
        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        email = sharedpreferences.getString(EMAIL_KEY, null);
        password = sharedpreferences.getString(PASSWORD_KEY, null);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (email != null && password != null) {
            Intent i = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(i);
        }
    }
}