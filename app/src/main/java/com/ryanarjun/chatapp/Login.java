package com.ryanarjun.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;



public class Login extends AppCompatActivity{
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fields on the page
        final EditText etEmail = findViewById(R.id.etEmail);
        final EditText etPassword = findViewById(R.id.etPassword);
        final Button bLogin = findViewById(R.id.btnLogin);
        final TextView tvRegisterLink = findViewById(R.id.tvRegisterLink);
        progressDialog = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        // If the register link is clicked on create an intent of the Register screen
        tvRegisterLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(Login.this, Register.class);
                Login.this.startActivity(registerIntent);
            }
        });

        // Click the Login
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(Login.this, MainMenu.class);
                final String email = etEmail.getText().toString();
                final String password = etPassword.getText().toString();

                 //Listener will send the information
                loginUser(email, password);
                Login.this.startActivity(mainIntent);
                finish();

            }
        });
    }

    protected void loginUser(String email, String password){
        if (TextUtils.isEmpty(email)){
            // email is empty
            Toast.makeText(this, "Please Enter an email", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please Enter a password", Toast.LENGTH_SHORT).show();
            return;

        }
        progressDialog.setMessage("Logining now...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "Welcome",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                });
    }
}