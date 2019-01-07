package com.majd.sakni.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

public class MyHousesFragment extends Fragment {
    private static FirebaseUser firebaseUser;
    private FloatingActionButton addButton;
    public static ArrayList<House> myHouses;
    public static RecyclerView recyclerView;
    private boolean isTwoPane;


    public MyHousesFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_houses,container,false);
        isTwoPane=getResources().getBoolean(R.bool.isTablet);

        myHouses=new ArrayList<>();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        addButton=view.findViewById(R.id.floatingActionButton);

        addButton=view.findViewById(R.id.floatingActionButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firebaseUser == null) {
                    Intent intent = new Intent(getContext(), SignupActivity.class);
                    startActivity(intent);
                } else {
                    Query query = FirebaseDatabase.getInstance()
                            .getReference().child("users").orderByKey().equalTo(firebaseUser.getUid());

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                                    User user = childDataSnapshot.getValue(User.class);
                                    if (user.getName().equalsIgnoreCase("null")) {
                                        Intent intent=new Intent(getContext(),MainActivity.class);
                                        intent.putExtra("updateState",false);
                                        startActivity(intent);
                                    }
                                }

                            }

                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }

            }
        });
        recyclerView=view.findViewById(R.id.search_rv);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(firebaseUser!=null) {
                    startActivity(new Intent(getContext(), AddHouseActivity.class));
                }else {
                    Toast.makeText(getContext(),"Please sign in before",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(getContext(),SignupActivity.class));
                }
            }
        });

        if(firebaseUser!=null) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Query query = FirebaseDatabase.getInstance()
                            .getReference().child("houses");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                                    House house = childDataSnapshot.getValue(House.class);
                                    if (house.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
                                        Log.e("************", house.getAddress());
                                        myHouses.add(house);

                                    }

                                }

                            }
                            Log.e("##############", myHouses.size() + "");

                            
                            if(isTwoPane){
                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));



                            }else {
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                            }
                            HouseAdapter houseAdapter = new HouseAdapter(myHouses, getContext());

                            recyclerView.setAdapter(houseAdapter);


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }).start();

        }else{

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Query query = FirebaseDatabase.getInstance()
                            .getReference().child("houses");

                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                                    House house = childDataSnapshot.getValue(House.class);
//                                    if (house.getUserId().equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {
//                                        Log.e("************", house.getAddress());
                                        myHouses.add(house);

//                                    }

                                }

                            }
                            Log.e("##############", myHouses.size() + "");
                            if(isTwoPane){
                                recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));



                            }else {
                                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


                            }                            HouseAdapter houseAdapter = new HouseAdapter(myHouses, getContext());

                            recyclerView.setAdapter(houseAdapter);


                        }


                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });


                }
            }).start();



        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent=new Intent(getContext(),HouseDetailsActivity.class);
                intent.putExtra("h",myHouses.get(position));
                startActivity(intent);

            }
        });








        return view;
    }




}

