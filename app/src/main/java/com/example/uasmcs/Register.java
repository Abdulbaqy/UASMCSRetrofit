package com.example.uasmcs;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    private EditText userNameEditText, emailEditText, passwordEditText;
    private Button registerButton, goToLogin;
    private FirebaseAuth mAuth;

    public Register() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        userNameEditText = findViewById(R.id.userNameEditText);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        registerButton = findViewById(R.id.registerButton);
        goToLogin = findViewById(R.id.goToLoginButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if (isValidEmail(email)) {
                    registerUser(username, email, password);
                } else {
                    Toast.makeText(Register.this, "Alamat email tidak valid.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    private void registerUser(String username, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Registrasi sukses, tambahkan data pengguna ke Firebase Realtime Database
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            if (currentUser != null) {
                                String userId = currentUser.getUid();

                                // Misalnya, simpan nama pengguna ke Firebase Realtime Database
                                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(userId);
                                userRef.child("username").setValue(username);
                                userRef.child("email").setValue(email);

                                // Sekarang data pengguna sudah disimpan di Firebase Realtime Database

                                // Lanjutkan ke aktivitas berikutnya (GameList)
                                Intent intent = new Intent(Register.this, GameList.class);
                                startActivity(intent);
                                finish();
                            }
                        } else {
                            if (task.getException() != null) {
                                String errorMessage = task.getException().getMessage();
                                Toast.makeText(Register.this, "Registrasi gagal: " + errorMessage, Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(Register.this, "Registrasi gagal. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private boolean isValidEmail(String email) {
        // Validasi alamat email dengan ekspresi reguler
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (!email.matches(emailPattern)) {
            // Alamat email tidak sesuai dengan format yang benar
            return false;
        }
        return true;
    }
}



