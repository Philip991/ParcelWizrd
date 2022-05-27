package com.example.parcelwizrd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.example.parcelwizrd.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

//import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;
    ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        getSupportActionBar().hide();



        arrayList= new ArrayList<String>();
      //  itemReader();
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, arrayList);
      //  itemWriter();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
             switch (item.getItemId()){
                 case R.id.homeFragment:
                     replaceFragment(new HomeFragment());
                     break;
                 case R.id.ordersFragment:
                     replaceFragment(new OrdersFragment());
                     break;
                 case R.id.profileFragment:
                     replaceFragment(new ProfileFragment());
                     break;
             }
            return true;

        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_fragment,fragment);
        fragmentTransaction.commit();
    }
/*
    public void itemReader(){
        File filesDir = getFilesDir();
        File  deliveryFile = new File(filesDir, "parcelWizrd.txt");
        try {
            arrayList = new ArrayList<String>(FileUtils.readLines(deliveryFile));
        } catch (IOException e){
            arrayList= new ArrayList<String>();
        }

    }
    public void itemWriter(){
        File filesDir = getFilesDir();
        File deliveryFile = new File(filesDir, "parcelWizrd.txt");
        try {
            FileUtils.writeLines(deliveryFile, arrayList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/


}