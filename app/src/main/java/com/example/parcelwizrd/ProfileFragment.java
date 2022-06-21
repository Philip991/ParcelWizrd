package com.example.parcelwizrd;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parcelwizrd.Model.UserModel;
import com.example.parcelwizrd.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment  {

    View view;



    ActivityMainBinding binding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    Button logout;
    //TextView GreetingTv;

    List<UserModel> mList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_profile, container, false);
        logout = (Button) view.findViewById(R.id.logout_btn);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginUser.class));
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference=FirebaseDatabase.getInstance().getReference("Users").child(RegisterUser.CUSTOMER_USERS);
        userID = user.getUid();

        //GreetingTv = (TextView) view.findViewById(R.id.greeting);

        final TextView UsernameTv = (TextView) view.findViewById(R.id.username);
        final TextView EmailAddressTv = (TextView) view.findViewById(R.id.emailAddress);
        final TextView FullNameTv = (TextView) view.findViewById(R.id.fullName);
        final TextView GreetingTv = (TextView) view.findViewById(R.id.greeting);

            reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    UserModel userModel = snapshot.getValue(UserModel.class);
                    if (userModel != null) {
                        String Username = userModel.Username;
                        String Email = userModel.Email;
                        String FirstName = userModel.FirstName;
                        String LastName = userModel.LastName;


                        UsernameTv.setText(Username);
                        EmailAddressTv.setText(Email);
                        FullNameTv.setText(FirstName +""+ LastName);
                        GreetingTv.setText("Hello " + FirstName);


                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(getContext(), "Error getting Profile details", Toast.LENGTH_SHORT).show();
                }
            });






        return view;

    }
    public ProfileFragment(){

    }
}