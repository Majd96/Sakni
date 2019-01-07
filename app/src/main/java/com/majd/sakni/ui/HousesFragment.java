package com.majd.sakni.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.majd.sakni.R;
import com.majd.sakni.adapters.HouseAdapter;
import com.majd.sakni.models.House;

import java.util.ArrayList;

public class HousesFragment extends Fragment implements SearchView.OnQueryTextListener {
    RecyclerView recyclerView;
    HouseAdapter adapter;

    ArrayList<House> searchHouses;


    public HousesFragment () {

    }




    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(final String newText) {

        Log.d("#####", "text change");
        Query query = FirebaseDatabase.getInstance()
                .getReference().child("houses")
                .orderByChild("location")
                .startAt(newText)
                .endAt(newText + "\uf8ff");

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    searchHouses = new ArrayList<>();
                    if (!TextUtils.isEmpty(newText)) {
                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            searchHouses.add(child.getValue(House.class));
                        }
                    }
                    adapter = new HouseAdapter(searchHouses, getContext());
                    recyclerView.setAdapter(adapter);


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return false;
    }


}
