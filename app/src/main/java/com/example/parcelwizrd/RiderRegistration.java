package com.example.parcelwizrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RiderRegistration extends AppCompatActivity {

    public static final String CUSTOMER_USERS = "RidersUsers";

    EditText Email,Password,confirmPass;

    Button Register;

    ProgressDialog progressDialog;

    FirebaseUser mUser;
    FirebaseAuth mAuth;
    String userID="";
    private String username="";

    DatabaseReference reference;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_registration);

        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        Email = (EditText)findViewById(R.id.et_email);
        Password = (EditText) findViewById(R.id.et_password);
        confirmPass=(EditText) findViewById(R.id.et_confirm_pass);

        Register =(Button) findViewById(R.id.register_btn);

        progressDialog= new ProgressDialog(this);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                finish();

            }
        });

    }

    public void registerUser(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirm_pass=confirmPass.getText().toString().trim();

        if(email.isEmpty()){
            Email.setError("Email is required");
            Email.requestFocus();
            return;
        }
        if(!pat.matcher(email).matches()){
            Email.setError("Please enter a valid email");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            Password.setError("Password is empty");
            Password.requestFocus();
            return;
        }
        if(password.length()<8){
            Password.setError("Password must be at least 8 digits");
            Password.requestFocus();
            return;
        }
        if (!confirm_pass.equals(password)){
            confirmPass.setError("Passwords don't match");
            confirmPass.requestFocus();
            return;
        }

        else{
            progressDialog.setMessage("Creating your account...");
            progressDialog.setTitle("Loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        userID= firebaseUser.getUid();


                        reference = FirebaseDatabase.getInstance().getReference("Users").child(RiderRegistration.CUSTOMER_USERS).child(userID);
                        HashMap<String, String>hashMap= new HashMap<>();
                        hashMap.put("id", userID);
                        hashMap.put("Email",email);


                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RiderRegistration.this,"Rider Registered Successfully",Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                    sendUserToMainActivity();

                                }
                                else{
                                    Toast.makeText(RiderRegistration.this,"Failed to register, try again",Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                }
                            }
                        });
                    }
                    else{
                        Toast.makeText(RiderRegistration.this, "Error, Try again", Toast.LENGTH_LONG).show();
                        progressDialog.hide();
                    }
                }
            });


        }
    }
    public void sendUserToMainActivity(){
        Intent intent =new Intent(RiderRegistration.this,MainActivity.class);
        //intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}