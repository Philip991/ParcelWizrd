package com.example.parcelwizrd;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.parcelwizrd.Model.UserOrderModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class OrdersFragment extends Fragment {

    RecyclerView ShowOrders;
    DatabaseReference reference;
    UserShowOrderAdapter myAdapter;
    ArrayList<UserOrderModel> mList;
    View view;
    String userID="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_orders, container, false);

        ShowOrders = view.findViewById(R.id.show_orders);
        reference= FirebaseDatabase.getInstance().getReference("Users").child(RegisterUser.ORDER).child(userID);
        ShowOrders.setHasFixedSize(true);
        ShowOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        mList= new ArrayList<>();
        myAdapter = new UserShowOrderAdapter(getActivity(),mList);
        ShowOrders.setAdapter(myAdapter);



        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    UserOrderModel uModel= dataSnapshot.getValue(UserOrderModel.class);
                    mList.add(uModel);
                }
                myAdapter.notifyDataSetChanged();
                //Adapter = new UserShowOrderAdapter(getContext(),mList);
               // ShowOrders.setAdapter(Adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        return view;
    }




    public OrdersFragment(){

    }
}