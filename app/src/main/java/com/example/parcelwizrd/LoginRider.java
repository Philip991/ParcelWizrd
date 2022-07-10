package com.example.parcelwizrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Pattern;

public class LoginRider extends AppCompatActivity {

    EditText Email, Password;
    Button Login;
    TextView ForgotPass, RegisterBtn, LoginUser;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);

    ProgressDialog progressDialog;

    FirebaseAuth mAuth;
    FirebaseUser mUser;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_rider);

        getSupportActionBar().hide();

        Email=(EditText) findViewById(R.id.et_email);
        Password=(EditText) findViewById(R.id.et_password);
        Login=(Button) findViewById(R.id.btn_login);
        ForgotPass=(TextView) findViewById(R.id.forgot_pass);
        RegisterBtn=(TextView) findViewById(R.id.register_btn);
        LoginUser=(TextView) findViewById(R.id.login_user);

        progressDialog = new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();


        ForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRider.this, RiderRegistration.class));

            }
        });
        LoginUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginRider.this, com.example.parcelwizrd.LoginUser.class));

            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RiderLogin();
            }
        });
    }

    private void RiderLogin() {
        String email= Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(!pat.matcher(email).matches()){
            Email.setError("Enter a Valid Email");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()||password.length()<8){
            Password.setError("Enter a valid password ");
            Password.requestFocus();
            return;
        }
        else{
            progressDialog.setMessage("loading your account...");
            progressDialog.setTitle("Loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginRider.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        SendUserToLandingPage();
                        progressDialog.hide();
                    }else{
                        Toast.makeText(LoginRider.this, "Error Logging in", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }
            });

        }
    }

    private void SendUserToLandingPage() {
        Intent intent = new Intent(LoginRider.this, RiderLandingPage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}