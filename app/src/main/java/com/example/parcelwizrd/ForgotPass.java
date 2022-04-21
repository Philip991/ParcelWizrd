package com.example.parcelwizrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parcelwizrd.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPass extends AppCompatActivity {

    EditText Email;
    Button resetPassword;

    FirebaseAuth mAuth;

    ProgressDialog progressDialog;


    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        getSupportActionBar().hide();

        Email=(EditText) findViewById(R.id.et_email);
        resetPassword=(Button) findViewById(R.id.reset_pass);

        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPass();
            }
        });
    }
    public void resetPass(){
        String email = Email.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is Required");
            Email.requestFocus();
            return;
        }
        if (!pat.matcher(email).matches()){
            Email.setError("Please Enter a valid Email");
            Email.requestFocus();
            return;
        }
        else {
            progressDialog.setMessage("Resetting your password...");
            progressDialog.setTitle("Loading");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(ForgotPass.this, "Check your Email to reset your password ", Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                        sendUserToLogin();
                    }else {
                        Toast.makeText(ForgotPass.this, "Error, Try again", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                }
            });



        }
    }
    public void sendUserToLogin(){
        startActivity(new Intent(ForgotPass.this, LoginUser.class));
        finish();
    }

}