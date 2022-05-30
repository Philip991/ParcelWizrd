package com.example.parcelwizrd;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;


public class HomeFragment extends Fragment {

    View view;

    TextView Greeting;
    EditText PickUp, DropOff, PickUpFirstName, PickUpLastName, PickUpNumber, PickUPEmail, DeliveryFirstName, DeliveryLastName, DeliveryNumber, DeliveryEmail;
    CheckBox Bike_CB, Car_CB, Van_CB, Lorry_CB;
    Button Proceed;

    List<UserOrderModel> mlist = new ArrayList<>();


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

        Greeting = view.findViewById(R.id.greeting);
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




        return view;
    }

    private void proceed() {
        String pickUp= PickUp.getText().toString();
        String dropOff=DropOff.getText().toString();
        String pickUpFirstName=PickUpFirstName.getText().toString();
        String pickUpLastName=PickUpLastName.getText().toString();
        String pickUpNumber=PickUpNumber.getText().toString();
        String pickUpEmail=PickUPEmail.getText().toString();
        String deliveryFirstName=DeliveryFirstName.getText().toString();
        String deliveryLastName=DeliveryLastName.getText().toString();
        String deliveryNumber=DeliveryNumber.getText().toString();
        String deliveryEmail=DeliveryEmail.getText().toString();


        if (pickUp.isEmpty()){
            PickUp.setError("Enter PickUp Location");
            PickUp.requestFocus();
            return;
        }
        if (dropOff.isEmpty()){
            DropOff.setError("Enter DropOff Location");
            DropOff.requestFocus();
            return;
        }
        if (pickUpFirstName.isEmpty()){
            PickUpFirstName.setError("First Name Required");
            PickUpFirstName.requestFocus();
            return;
        }
        if (pickUpLastName.isEmpty()){
            PickUpLastName.setError("Last Name Required");
            PickUpLastName.requestFocus();
            return;
        }
        if (pickUpNumber.isEmpty()){
            PickUpNumber.setError("Phone Number Required");
            PickUpNumber.requestFocus();
            return;
        }
        if (!pat.matcher(pickUpEmail).matches()){
            PickUPEmail.setError("Enter a Valid Email");
            PickUPEmail.requestFocus();
            return;
        }
        if (deliveryFirstName.isEmpty()){
            DeliveryFirstName.setError("First Name Required");
            DeliveryFirstName.requestFocus();
            return;
        }
        if (deliveryLastName.isEmpty()){
            DeliveryLastName.setError("Last Name Required");
            DeliveryLastName.requestFocus();
            return;
        }
        if (deliveryNumber.isEmpty()){
            DeliveryNumber.setError("Phone Number Required");
            DeliveryNumber.requestFocus();
            return;
        }
        if (!pat.matcher(deliveryEmail).matches()){
            DeliveryEmail.setError("Enter a Valid Email");
            DeliveryEmail.requestFocus();
            return;
        }
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userID= firebaseUser.getUid();
        for (UserOrderModel Order: mlist){
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(RegisterUser.ORDER).child(userID);
            HashMap<String, String>hashMap= new HashMap<>();
            hashMap.put("PickUp Location", Order.getPickUp());
            hashMap.put("DropOff Location",Order.getDropOff());
            hashMap.put("PickUp First Name", Order.getPickUpFirstName());
            hashMap.put("PickUp Last Name",Order.getPickUpLastName());
            hashMap.put("PickUp Phone Number",Order.getPickUpNumber());
            hashMap.put("PickUp Email", Order.getPickUpEmail());
            hashMap.put("Delivery First Name", Order.getDeliveryFirstName());
            hashMap.put("Delivery Last Name", Order.getDeliveryLastName());
            hashMap.put("Delivery Phone Number", Order.getDeliveryNumber());
            hashMap.put("Delivery Email", Order.getDeliveryEmail());

            reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), OrderPayment.class));
                    }
                    else {
                        Toast.makeText(getContext(), "Something went wrong",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }


    }


    public HomeFragment(){
        
    }
}