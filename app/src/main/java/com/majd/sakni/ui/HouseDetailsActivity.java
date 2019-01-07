package com.majd.sakni.ui;

import android.Manifest;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.majd.sakni.R;
import com.majd.sakni.models.House;
import com.majd.sakni.models.User;
import com.squareup.picasso.Picasso;

public class HouseDetailsActivity extends AppCompatActivity {

    TextView srTextView;
    TextView drTextView;
    TextView brTextView;
    TextView price;
    TextView locationAndAddress;
    TextView servicesTextView;
    TextView ownerTextView;
    String phone = "";
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_details);


        House house = getIntent().getParcelableExtra("h");

        ViewFlipper viewFlipper = findViewById(R.id.myflipper);
        for (int i = 0; i < house.getPictures().size(); i++) {

            Log.i("Set Filpper Called", house.getPictures().get(i));
            ImageView image = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            image.setLayoutParams(layoutParams);
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.get().load(house.getPictures().get(i)).into(image);
            viewFlipper.addView(image);


        }
        viewFlipper.startFlipping();


        //  This will create dynamic image view and add them to ViewFlipper

        srTextView = findViewById(R.id.textView2);
        drTextView = findViewById(R.id.textView20);
        brTextView = findViewById(R.id.textView21);
        price = findViewById(R.id.price_inc);
        locationAndAddress = findViewById(R.id.textView30);
        ownerTextView = findViewById(R.id.owner_name);

        srTextView.append("  " + house.getNumberOfSingleRooms());
        drTextView.append("   " + house.getBumberOfDoubleRooms());
        brTextView.append("  " + house.getNumberOfBathrooms());
        String serviceIncluded = (house.isServicesIncluded() ? "services included" : "services not included");
        price.setText(house.getTotalprice() + "$, " + serviceIncluded);
        locationAndAddress.setText(house.getLocation() + ", " + house.getAddress());
        servicesTextView = findViewById(R.id.servicesTextView);
        servicesTextView.setText("");
        if (house.isFurnished()) {
            servicesTextView.append(" - Furnished\n ");
        }
        if (house.isParking()) {
            servicesTextView.append("- Car parking\n ");
        }
        if (house.isBalcony()) {
            servicesTextView.append("- Balcony\n ");
        }
        if (house.isSalon()) {
            servicesTextView.append("- Salon\n ");
        }
        if (house.isElevator()) {
            servicesTextView.append("- Elevator\n ");
        }
        if (house.isWifi()) {
            servicesTextView.append("- Wifi\n ");
        }

        Query query = FirebaseDatabase.getInstance()
                .getReference().child("users").orderByKey().equalTo(house.getUserId());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                        User user = childDataSnapshot.getValue(User.class);
                        ownerTextView.setText(user.getName());
                        phone = user.getPhoneNo();


                        Log.d("******************", user.getName());
                        Log.d("*******************", user.getPhoneNo());

                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        Log.d("*************", house.getAddress());


    }

    public void call(View view) {

        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+phone));
        if (ContextCompat.checkSelfPermission(HouseDetailsActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HouseDetailsActivity.this, new String[]{Manifest.permission.CALL_PHONE},REQUEST_PHONE_CALL);
        }
        else
        {
            startActivity(callIntent);

        }

    }
}
