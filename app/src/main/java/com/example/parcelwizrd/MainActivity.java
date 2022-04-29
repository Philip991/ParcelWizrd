package com.example.parcelwizrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnItemSelectedListener{

    BottomNavigationView bottomNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView= findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.homeFragment);



    }
    HomeFragment homeFragment = new HomeFragment();
    OrdersFragment ordersFragment= new OrdersFragment();
    ProfileFragment profileFragment=new ProfileFragment();


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homeFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment);
                return true;

            case R.id.ordersFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.container, ordersFragment);
                return true;

            case R.id.profileFragment:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,profileFragment);
        }



        return false;

    }
}