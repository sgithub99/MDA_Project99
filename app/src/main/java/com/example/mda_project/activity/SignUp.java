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

public class SignUp extends AppCompatActivity {

    TextInputEditText textInputEditTextFullname, textInputEditTextUsername, textInputEditTextPassword, textInputEditTextEmail;
    Button buttonSignup;
    TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        textInputEditTextUsername = findViewById(R.id.inputUsername);
        textInputEditTextFullname = findViewById(R.id.inputFullName);
        textInputEditTextPassword = findViewById(R.id.inputPassword);
        textInputEditTextEmail = findViewById(R.id.inputEmail);
        buttonSignup = findViewById(R.id.btnSignup);
        textViewLogin = findViewById(R.id.loginText);

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        buttonSignup.setOnClickListener(v -> {
            String fullname = String.valueOf(textInputEditTextFullname.getText());
            String username = String.valueOf(textInputEditTextUsername.getText());
            String password = String.valueOf(textInputEditTextPassword.getText());
            String email = String.valueOf(textInputEditTextEmail.getText());

            if (!fullname.equals("") && !username.equals("") && !password.equals("") && !email.equals("")) {
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() -> {
                    String[] field = new String[4];
                    field[0] = "fullname";
                    field[1] = "username";
                    field[2] = "password";
                    field[3] = "email";
                    //Creating array for data
                    String[] data = new String[4];
                    data[0] = fullname;
                    data[1] = username;
                    data[2] = password;
                    data[3] = email;
                    PutData putData = new PutData("http://192.168.0.4/login_signup/signup.php", "POST", field, data);
                    if (putData.startPut()) {
                        if (putData.onComplete()) {
                            String result = putData.getResult();
                            if(result.equals("Sign Up Success")){
                                Intent intent = new Intent(getApplicationContext(), Login.class);
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