package com.example.parcelwizrd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcelwizrd.Model.UserModel;
import com.example.parcelwizrd.Model.UserOrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment {

    public static final String ORDERS = "Orders" ;

    View view;

    TextView Greeting;
    EditText PickUp, DropOff, PickUpFirstName, PickUpLastName, PickUpNumber, PickUPEmail, DeliveryFirstName, DeliveryLastName, DeliveryNumber, DeliveryEmail;
    CheckBox Bike_CB, Car_CB, Van_CB, Lorry_CB;
    Button Proceed;
    private String username= "";

    FirebaseUser user;
    DatabaseReference reference;
    String userID="";
    private FirebaseAuth mAuth;


    List<UserOrderModel> mList = new ArrayList<>();

    ProgressDialog progressDialog;


    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";


    Pattern pat = Pattern.compile(emailRegex);


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        Proceed =(Button) view.findViewById(R.id.proceed);
        Bike_CB = (CheckBox) view.findViewById(R.id.cb_bike);
        Car_CB= (CheckBox) view.findViewById(R.id.cb_car);
        Van_CB=(CheckBox) view.findViewById(R.id.cb_van);
        Lorry_CB=(CheckBox) view.findViewById(R.id.cb_lorry);
        PickUp =(EditText) view.findViewById(R.id.et_pickup);
        DropOff =(EditText) view.findViewById(R.id.et_dropOff);
        PickUpFirstName =(EditText) view.findViewById(R.id.pickup_first_name);
        PickUpLastName =(EditText) view.findViewById(R.id.pickup_last_name);
        PickUpNumber =(EditText) view.findViewById(R.id.pickup_number);
        PickUPEmail =(EditText) view.findViewById(R.id.pickup_email);
        DeliveryFirstName =(EditText) view.findViewById(R.id.delivery_first_name);
        DeliveryLastName =(EditText) view.findViewById(R.id.delivery_last_name);
        DeliveryNumber =(EditText) view.findViewById(R.id.delivery_number);
        DeliveryEmail =(EditText) view.findViewById(R.id.delivery_email);




        Proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceed();
            }
        });

        Bike_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Car_CB.setChecked(false);
                    Van_CB.setChecked(false);
                    Lorry_CB.setChecked(false);

                }
            }
        });
        Car_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Bike_CB.setChecked(false);
                    Van_CB.setChecked(false);
                    Lorry_CB.setChecked(false);
                }
            }
        });
        Van_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Bike_CB.setChecked(false);
                    Car_CB.setChecked(false);
                    Lorry_CB.setChecked(false);
                }
            }
        });

        Lorry_CB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    Bike_CB.setChecked(false);
                    Car_CB.setChecked(false);
                    Van_CB.setChecked(false);
                }
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(RegisterUser.CUSTOMER_USERS);
        userID = user.getUid();

        final TextView GreetingTv = (TextView) view.findViewById(R.id.greeting);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserModel userModel = snapshot.getValue(UserModel.class);
                if (userModel != null) {

                    String FirstName = userModel.FirstName;



                    GreetingTv.setText("Welcome, " + FirstName);


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error getting Profile details", Toast.LENGTH_SHORT).show();
            }
        });



        return view;
    }

    private void proceed() {
        String pickUp = PickUp.getText().toString();
        String dropOff = DropOff.getText().toString();
        String pickUpFirstName = PickUpFirstName.getText().toString();
        String pickUpLastName = PickUpLastName.getText().toString();
        String pickUpNumber = PickUpNumber.getText().toString();
        String pickUpEmail = PickUPEmail.getText().toString();
        String deliveryFirstName = DeliveryFirstName.getText().toString();
        String deliveryLastName = DeliveryLastName.getText().toString();
        String deliveryNumber = DeliveryNumber.getText().toString();
        String deliveryEmail = DeliveryEmail.getText().toString();


        if (pickUp.isEmpty()) {
            PickUp.setError("Enter PickUp Location");
            PickUp.requestFocus();
            return;
        }
        if (dropOff.isEmpty()) {
            DropOff.setError("Enter DropOff Location");
            DropOff.requestFocus();
            return;
        }
        if (pickUpFirstName.isEmpty()) {
            PickUpFirstName.setError("First Name Required");
            PickUpFirstName.requestFocus();
            return;
        }
        if (pickUpLastName.isEmpty()) {
            PickUpLastName.setError("Last Name Required");
            PickUpLastName.requestFocus();
            return;
        }
        if (pickUpNumber.isEmpty()) {
            PickUpNumber.setError("Phone Number Required");
            PickUpNumber.requestFocus();
            return;
        }
        if (!pat.matcher(pickUpEmail).matches()) {
            PickUPEmail.setError("Enter a Valid Email");
            PickUPEmail.requestFocus();
            return;
        }
        if (deliveryFirstName.isEmpty()) {
            DeliveryFirstName.setError("First Name Required");
            DeliveryFirstName.requestFocus();
            return;
        }
        if (deliveryLastName.isEmpty()) {
            DeliveryLastName.setError("Last Name Required");
            DeliveryLastName.requestFocus();
            return;
        }
        if (deliveryNumber.isEmpty()) {
            DeliveryNumber.setError("Phone Number Required");
            DeliveryNumber.requestFocus();
            return;
        }
        if (!pat.matcher(deliveryEmail).matches()) {
            DeliveryEmail.setError("Enter a Valid Email");
            DeliveryEmail.requestFocus();
            return;
        } else {

            progressDialog.setMessage("Processing your Order...");
            progressDialog.setTitle("Loading..");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            FirebaseUser firebaseUser = mAuth.getCurrentUser();
            userID = firebaseUser.getUid();
            reference=FirebaseDatabase.getInstance().getReference("Users").child(RegisterUser.ORDER).child(userID);
            UserOrderModel userOrderModel = new UserOrderModel(pickUp, dropOff,pickUpFirstName,pickUpLastName,pickUpNumber,pickUpEmail,deliveryFirstName,deliveryLastName,deliveryNumber,deliveryEmail);


            FirebaseDatabase.getInstance().getReference("Users").child(RegisterUser.CUSTOMER_USERS).child(HomeFragment.ORDERS).child(userID).push().setValue(userOrderModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                    sendUserToOrders();


                } else {
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
                    progressDialog.hide();
                }
            }
        });

    }

    }

    private void sendUserToOrders() {
        Intent intent = new Intent(getActivity(), OrdersFragment.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


    public HomeFragment(){
        
    }
}