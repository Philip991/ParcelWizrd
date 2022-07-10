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

import com.example.parcelwizrd.Model.RiderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RiderRegistration extends AppCompatActivity {

    public static final String CUSTOMER_USERS = "RidersUsers";

    EditText Username, Email, FirstName,LastName,PhoneNumber, Address, City, State, Country,VehicleMake, VehicleColor, VehicleRegNo,Password,confirmPass;

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

        Username = (EditText) findViewById(R.id.et_username);
        Email = (EditText)findViewById(R.id.et_email);
        FirstName=(EditText)findViewById(R.id.et_first_name);
        LastName=(EditText) findViewById(R.id.et_last_name);
        PhoneNumber=(EditText) findViewById(R.id.et_phone_number);
        Address=(EditText) findViewById(R.id.et_address);
        City=(EditText) findViewById(R.id.et_city);
        State= (EditText) findViewById(R.id.et_state);
        Country=(EditText) findViewById(R.id.et_country);
        VehicleMake=(EditText)findViewById(R.id.et_vehicle_make);
        VehicleColor=(EditText)findViewById(R.id.et_vehicle_color);
        VehicleRegNo=(EditText)findViewById(R.id.et_vehicle_number);
        Password = (EditText) findViewById(R.id.et_password);
        confirmPass=(EditText) findViewById(R.id.et_confirm_pass);


        Register =(Button) findViewById(R.id.register_btn);

        progressDialog= new ProgressDialog(this);


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });

    }

    public void registerUser(){
        String username =Username.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String confirm_pass=confirmPass.getText().toString().trim();
        String firstName= FirstName.getText().toString();
        String lastName=LastName.getText().toString();
        String phoneNumber=PhoneNumber.getText().toString().trim();
        String address=Address.getText().toString();
        String city=City.getText().toString();
        String state= State.getText().toString();
        String country=Country.getText().toString();
        String vehicleMake=VehicleMake.getText().toString();
        String vehicleColor= VehicleColor.getText().toString().trim();
        String vehicleNum=VehicleRegNo.getText().toString().trim();

        if (username.isEmpty()){
            Username.setError("Username is required");
            Username.requestFocus();
            return;
        }
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
        if (vehicleMake.isEmpty()){
            VehicleMake.setError("Enter your Vehicle make please");
            VehicleMake.requestFocus();
            return;
        }
        if (vehicleColor.isEmpty()){
            VehicleColor.setError("Enter your Vehicle Color please");
            VehicleColor.requestFocus();
            return;
        }
        if (vehicleNum.isEmpty()){
            VehicleRegNo.setError("Enter your Vehicle Registration Number please");
            VehicleRegNo.requestFocus();
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
                        RiderModel riderModel = new RiderModel(userID,username, email, firstName, lastName, phoneNumber, address,city,state,country,vehicleMake,vehicleColor, vehicleNum);


                        FirebaseDatabase.getInstance().getReference("Users").child(RiderRegistration.CUSTOMER_USERS).child(userID).setValue(riderModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(RiderRegistration.this,"Rider Registered Successfully",Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                    sendUserToLogin();

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
    public void sendUserToLogin(){
        Intent intent =new Intent(RiderRegistration.this, LoginRider.class);
        //intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}