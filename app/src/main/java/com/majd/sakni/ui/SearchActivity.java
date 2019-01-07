package com.majd.sakni.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.majd.sakni.R;
import com.majd.sakni.adapters.HouseAdapter;
import com.majd.sakni.heplers.ItemClickSupport;
import com.majd.sakni.models.House;
import com.majd.sakni.models.User;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    String queryText=" ";
    ArrayList<House> searchHouses;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        if(getIntent().hasExtra("q")) {

            queryText = getIntent().getStringExtra("q");
            search(queryText);
        }
        recyclerView=findViewById(R.id.rv);
        recyclerView.setLayoutManager(layoutManager);
        searchHouses=new ArrayList<>();
        searchView=findViewById(R.id.sv);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                search(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });





        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent=new Intent(getApplicationContext(),HouseDetailsActivity.class);
                intent.putExtra("h",searchHouses.get(position));
                startActivity(intent);

            }
        });


    }

    void search(final String queryText){

        new Thread(new Runnable() {
            @Override
            public void run() {

                Query query = FirebaseDatabase.getInstance()
                        .getReference().child("houses")
                        .orderByChild("location")
                        .startAt(queryText)
                        .endAt(queryText + "\uf8ff");

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            searchHouses = new ArrayList<>();
                            if(!TextUtils.isEmpty(queryText)) {
                                for (DataSnapshot child : dataSnapshot.getChildren()) {

                                    searchHouses.add(child.getValue(House.class));
                                }
                            }
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            HouseAdapter houseAdapter=new HouseAdapter(searchHouses,getApplicationContext());

                            recyclerView.setAdapter(houseAdapter);
                            Log.e("*&*&*&*&*&*&",searchHouses.size()+"");



                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




            }
        }).start();
    }


}
