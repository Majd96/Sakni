package com.majd.sakni.ui;


import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import com.majd.sakni.R;

import com.majd.sakni.adapters.ViewPagerAdapter;
import com.majd.sakni.models.House;

import java.util.ArrayList;




public class MainActivity extends AppCompatActivity{
    private FirebaseUser firebaseUser;
    private Toolbar toolbar;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private boolean hasTheUserUpateHisInfo=true;
    public SearchView editsearch;



    ArrayList<House> searchHouses;




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        editsearch = findViewById(R.id.sv);
        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
//                if (newText.length() > 0) {
//                } else {
//                    // Do something when there's no input
//                }
                return false;
            }
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
                intent.putExtra("q",query);
                startActivity(intent);

                return false; }
        });


        Intent intent=getIntent();
        if(intent.hasExtra("updateState")){
            hasTheUserUpateHisInfo=intent.getBooleanExtra("updateState",false);


        }






        viewPager =  findViewById(R.id.viewpager1);
        setupViewPager(viewPager,hasTheUserUpateHisInfo);

        tabLayout =  findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);







    }

    private void setupViewPager(ViewPager viewPager,boolean hasTheUserUpateHisInfo) {
        //defines the number of tabs by setting up the appropriate fragments and tab name
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        if (firebaseUser != null) {
            if (hasTheUserUpateHisInfo) {


                adapter.addFragment(new MyHousesFragment(), "My Houses");
                adapter.addFragment(new UpdateInfoFragment(), "Profile");


            } else {
                adapter.addFragment(new UpdateInfoFragment(), "Please set your real name");
            }

        }else{

            adapter.addFragment(new MyHousesFragment(), "");

        }


        viewPager.setAdapter(adapter);


    }


}