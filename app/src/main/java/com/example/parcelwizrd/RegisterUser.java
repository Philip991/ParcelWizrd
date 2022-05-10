package com.example.parcelwizrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
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

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {

    public static final String CUSTOMER_USERS = "CustomersUser";

    EditText Username, Email, FirstName,LastName,PhoneNumber, Address, City, State, Country, Password, confirmPass;
    Button Register;
    TextView Login;
    TextView riderRegister;

    private FirebaseAuth mAuth;
    FirebaseUser mUser;
    String userID= "";
    private String username ="";




    DatabaseReference reference;



    ProgressDialog progressDialog;

    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();

        Username = (EditText) findViewById(R.id.et_username);
        Email=(EditText)findViewById(R.id.et_email);
        FirstName=(EditText)findViewById(R.id.et_first_name);
        LastName=(EditText) findViewById(R.id.et_last_name);
        PhoneNumber=(EditText) findViewById(R.id.et_phone_number);
        Address=(EditText) findViewById(R.id.et_address);
        City=(EditText) findViewById(R.id.et_city);
        State= (EditText) findViewById(R.id.et_state);
        Country=(EditText) findViewById(R.id.et_country);
        Password = (EditText) findViewById(R.id.et_password);
        confirmPass=(EditText)findViewById(R.id.et_confirm_pass);
        Register = (Button) findViewById(R.id.register_btn);
        Login=(TextView) findViewById(R.id.login_btn);
        riderRegister=(TextView)findViewById(R.id.rider_registration);

        progressDialog = new ProgressDialog(this );

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this,LoginUser.class));
                finish();
            }
        });

        riderRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterUser.this,RiderRegistration.class));
                finish();
            }
        });
    }
    public void registerUser(){
        String username =Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirm_password=confirmPass.getText().toString().trim();
        String firstName= FirstName.getText().toString();
        String lastName=LastName.getText().toString();
        String phoneNumber=PhoneNumber.getText().toString().trim();
        String address=Address.getText().toString();
        String city=City.getText().toString();
        String state= State.getText().toString();
        String country=Country.getText().toString();

        if (username.isEmpty()){
            Username.setError("Username is required");
            Username.requestFocus();
            return;
        }
        if (!pat.matcher(email).matches()){
            Email.setError("Please enter a valid Email");
            Email.requestFocus();
            return;
        }
        if (password.isEmpty()){
            Password.setError("Password is empty");
            Password.requestFocus();
            return;
        }
        if(password.length()<8){
            Password.setError("Password must be at least 8 digits");
            Password.requestFocus();
            return;
        }
        if (!confirm_password.equals(password)){
            confirmPass.setError("Passwords don't match");
            confirmPass.requestFocus();
            return;
        }
        if(firstName.isEmpty()){
            FirstName.setError("Enter your First Name please");
            FirstName.requestFocus();
            return;
        }
        if (lastName.isEmpty()){
            LastName.setError("Enter your Last Name please");
            LastName.requestFocus();
            return;
        }
        if(phoneNumber.isEmpty()){
            PhoneNumber.setError("Enter your phone number please");
            PhoneNumber.requestFocus();
            return;
        }
        if (address.isEmpty()){
            Address.setError("Enter your preferred delivery address please");
            Address.requestFocus();
            return;
        }
        if (city.isEmpty()){
            City.setError("Enter your City please");
            City.requestFocus();
            return;
        }
        if (state.isEmpty()){
            State.setError("Enter your state please");
            State.requestFocus();
            return;
        }
        if (country.isEmpty()){
            Country.setError("Enter your country please");
            State.requestFocus();
            return;
        }

        else{
            progressDialog.setMessage("Creating your account...");
            progressDialog.setTitle("Loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


           mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        userID=firebaseUser.getUid();


                        reference = FirebaseDatabase.getInstance().getReference("User").child(RegisterUser.CUSTOMER_USERS).child(username);
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("id",userID);
                        hashMap.put("Username", username);
                        hashMap.put("Email", email);
                        hashMap.put("First Name",firstName);
                        hashMap.put("Last Name",lastName);
                        hashMap.put("Phone Number",phoneNumber);
                        hashMap.put("Address",address);
                        hashMap.put("City",city);
                        hashMap.put("State",state);
                        hashMap.put("Country",country);



                        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterUser.this,"User Registered Successfully", Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                    sendUserToLogin();

                                }
                                else {
                                    Toast.makeText(RegisterUser.this,"Failed to register, try again!", Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                }
                            }
                        });


                    }
                    else {
                        Toast.makeText(RegisterUser.this,"Error, Try again",Toast.LENGTH_LONG);
                        progressDialog.hide();

                    }



                }
            });
        }

    }


    public void sendUserToLogin(){
        Intent intent =new Intent(RegisterUser.this,LoginUser.class);
        intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}