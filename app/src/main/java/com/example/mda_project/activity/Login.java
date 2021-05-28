package com.example.mda_project.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.example.mda_project.R;
import com.google.android.material.textfield.TextInputEditText;
import com.vishnusivadas.advanced_httpurlconnection.PutData;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    TextInputEditText textInputEditTextUsername, textInputEditTextPassword;
    Button buttonLogin;
    TextView textViewSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textInputEditTextUsername = findViewById(R.id.inputUsername);
        textInputEditTextPassword = findViewById(R.id.inputPassword);
        buttonLogin = findViewById(R.id.btnLogin);
        textViewSignup = findViewById(R.id.loginText);

        textViewSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });

        buttonLogin.setOnClickListener(v -> {

            String username = String.valueOf(textInputEditTextUsername.getText());
            String password = String.valueOf(textInputEditTextPassword.getText());

            if (!username.equals("") && !password.equals("")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[2];
                    field[0] = "username";
                    field[1] = "password";
                    //Creating array for data
                    String[] data = new String[2];
                    data[0] = username;
                    data[1] = password;
                    PutData putData = new PutData("http://192.168.0.4/login_signup/login.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Login Success")){
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
            }else{
                Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}