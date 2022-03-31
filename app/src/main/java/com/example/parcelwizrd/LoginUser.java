package com.example.parcelwizrd;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginUser extends AppCompatActivity {

    EditText Username, Password;
    Button Login;
    TextView ForgotPass, Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user);

        getSupportActionBar().hide();

        Username = (EditText) findViewById(R.id.et_username);
        Password = (EditText) findViewById(R.id.et_password);
        Login = (Button) findViewById(R.id.btn_login);
        ForgotPass=(TextView) findViewById(R.id.forgot_pass);
        Register=(TextView) findViewById(R.id.btn_register);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginUser.this,RegisterUser.class));
            }
        });
    }


}