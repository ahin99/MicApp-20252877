package com.example.gyeote;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText editEmail;
    private EditText editPassword;
    private RadioButton radioElder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        radioElder = findViewById(R.id.radioElder);
        Button buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> login());
    }

    private void login() {
        String email = editEmail.getText().toString().trim();
        String password = editPassword.getText().toString();

        // 빈 값으로 Firebase를 호출하면 앱이 종료되므로 먼저 막는다
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "이메일과 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        goToRoleScreen();
                    } else {
                        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // 선택한 역할에 맞는 화면으로 이동
    private void goToRoleScreen() {
        Intent intent;
        if (radioElder.isChecked()) {
            intent = new Intent(this, ElderHomeActivity.class);
        } else {
            intent = new Intent(this, FamilyHomeActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
