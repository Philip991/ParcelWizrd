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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UserPortfolio extends AppCompatActivity {

    EditText FirstName,LastName,PhoneNumber, Address, City, State, Country;

    Button finishRegistration;

    FirebaseUser mUser;
    FirebaseAuth mAuth;

    String userID="";

    ProgressDialog progressDialog;

    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_portfolio);

        getSupportActionBar().hide();

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();

        FirstName=(EditText)findViewById(R.id.et_first_name);
        LastName=(EditText) findViewById(R.id.et_last_name);
        PhoneNumber=(EditText) findViewById(R.id.et_phone_number);
        Address=(EditText) findViewById(R.id.et_address);
        City=(EditText) findViewById(R.id.et_city);
        State= (EditText) findViewById(R.id.et_state);
        Country=(EditText) findViewById(R.id.et_country);



        progressDialog=new ProgressDialog(this);

        finishRegistration=(Button) findViewById(R.id.finish_register_btn);

        finishRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishReg();

            }
        });


    }

    public void finishReg(){
        String firstName= FirstName.getText().toString();
        String lastName=LastName.getText().toString();
        String phoneNumber=PhoneNumber.getText().toString().trim();
        String address=Address.getText().toString();
        String city=City.getText().toString();
        String state= State.getText().toString();
        String country=Country.getText().toString();



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
            progressDialog.setMessage("Finishing your account...");
            progressDialog.setTitle("Loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            mAuth.updateCurrentUser(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        userID = firebaseUser.getUid();



                        reference=FirebaseDatabase.getInstance().getReference("Users").child("CustomersUser").child(userID);
                        HashMap<String,String>hashMap= new HashMap<>();
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
                                if(task.isSuccessful()){
                                    Toast.makeText(UserPortfolio.this, "Registration Complete", Toast.LENGTH_LONG).show();
                                    progressDialog.hide();
                                    sendUserToMainActivity();

                                }
                                else {
                                    Toast.makeText(UserPortfolio.this, "Registration Failed, Try again!", Toast.LENGTH_SHORT).show();
                                    progressDialog.hide();
                                }
                            }
                        });


                    }


                }
            });



    }




}

    public void sendUserToMainActivity(){
        Intent intent =new Intent(UserPortfolio.this,MainActivity.class);
        //intent.putExtra("username", username);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


}