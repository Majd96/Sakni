package com.majd.sakni.ui;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.majd.sakni.R;
import com.majd.sakni.models.House;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class AddHouseActivity extends AppCompatActivity {
    private Spinner singleRoomsSpinner;
    private Spinner doubleRoomsSpinner;
    private Spinner bathRoomsSpinner;
    private EditText priceEditText;
    private Spinner locationSpinner;
    private CheckBox parkingCheckBox;
    private CheckBox furnishedCheckBox;
    private CheckBox salonCheckBox;
    private CheckBox balconyCheckBox;
    private CheckBox elvatorCheckBox;
    private CheckBox wifiCheckBox;
    private CheckBox servicesIncludedeCheckBox;
    private EditText addressEditText;
    private ImageView uploadImageView;
    ArrayList<String> pic= new ArrayList<>();
    private static final int SELECT_PHOTO = 100;

    StorageReference housesPhotoRef;
    private FirebaseStorage firebaseStorage;
    String cities[]={"Ramallah","Nablus","Jenin","Tubas","Hebron","tulkarm","Berzit","Jerico"};





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_house);
        singleRoomsSpinner=findViewById(R.id.spinner);
        doubleRoomsSpinner=findViewById(R.id.spinner2);
        bathRoomsSpinner=findViewById(R.id.spinner3);
        priceEditText=findViewById(R.id.price);
        locationSpinner=findViewById(R.id.spinner5);
        parkingCheckBox=findViewById(R.id.checkBox);
        furnishedCheckBox=findViewById(R.id.checkBox2);
        salonCheckBox=findViewById(R.id.checkBox3);
        balconyCheckBox=findViewById(R.id.checkBox4);
        elvatorCheckBox=findViewById(R.id.checkBox5);
        wifiCheckBox=findViewById(R.id.checkBox6);
        servicesIncludedeCheckBox=findViewById(R.id.checkBox7);
        addressEditText=findViewById(R.id.editText2);
        uploadImageView=findViewById(R.id.imageView5);


        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        firebaseStorage=FirebaseStorage.getInstance();

        housesPhotoRef=firebaseStorage.getReference().child("houses_photos");



        String rooms[]={"1","2","3","4","5","6","7","8","9","10"};
        String cities[]={"Ramallah","Nablus","Jenin","Tubas","Hebron","tulkarm","Berzit","Jerico"};
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, rooms);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter arrayAdapter1=new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, cities);

        arrayAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        singleRoomsSpinner.setAdapter(arrayAdapter);
        doubleRoomsSpinner.setAdapter(arrayAdapter);
        bathRoomsSpinner.setAdapter(arrayAdapter);

        locationSpinner.setAdapter(arrayAdapter1);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_add,menu);

        for(int i = 0; i < menu.size(); i++){
            Drawable drawable = menu.getItem(i).getIcon();
            if(drawable != null) {
                drawable.mutate();
                drawable.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.menu_save){

            String noSingleRooms=singleRoomsSpinner.getSelectedItem().toString();
            String noDoubleRooms=singleRoomsSpinner.getSelectedItem().toString();
            String noBathRooms=singleRoomsSpinner.getSelectedItem().toString();
            double price=Double.parseDouble(priceEditText.getText().toString());
            String location=locationSpinner.getSelectedItem().toString();
            boolean carParking=parkingCheckBox.isChecked();
            boolean furnished=furnishedCheckBox.isChecked();
            boolean salon=salonCheckBox.isChecked();
            boolean balcony=balconyCheckBox.isChecked();
            boolean elevator=elvatorCheckBox.isChecked();
            boolean wifi=wifiCheckBox.isChecked();
            boolean servicesIncluded=servicesIncludedeCheckBox.isChecked();
            String address=addressEditText.getText().toString();
            Date d = new Date();
            CharSequence date  = DateFormat.format("MMMM d, yyyy ", d.getTime());

            Log.e(pic.size()+"","()()()()()()()");
            House house=new House(price,noSingleRooms,noDoubleRooms,carParking,furnished,salon,balcony,noBathRooms,elevator,wifi,servicesIncluded,location,address,date.toString(),pic, FirebaseAuth.getInstance().getCurrentUser().getUid());
            DatabaseReference roofRef=FirebaseDatabase.getInstance().getReference();
            roofRef.child("houses").push().setValue(house);
            startActivity(new Intent(this,MainActivity.class));


        }
        return true;
    }

    public void uploadImage(View view) {

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PHOTO);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getContentResolver().query(
                            selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    //data.getData() used to get the uri of the selected image
                    final Uri selectedImageUri=data.getData();
                    //create a reference for each photo
                    final StorageReference photoRef=housesPhotoRef.child(selectedImageUri.getLastPathSegment());
                    //the putFile( ) method return a UploadTask object
//                    photoRef.putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                        @Override
//                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                            housesPhotoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    Uri downloadUrl = uri;
//                                    pic.add(downloadUrl.toString());
//                                    Toast.makeText(getApplicationContext(),"Image uploaded sucessfully!",Toast.LENGTH_LONG).show();
//
//
//
//                                }
//
//                            //add the Uri to the database
//
//
//                            //use picasso to download the image to the imageview
//
//
//
//                    });



//                }
//        });

                    final UploadTask uploadTask = photoRef.putFile(selectedImageUri);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                                    photoRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                        @Override
                                                        public void onSuccess(Uri uri) {
                                                            Log.e("*********",uri.toString());
                                                            pic.add(uri.toString());
                                                            Log.e("*******",pic.size()+"");
                                                            Toast.makeText(getApplicationContext(), "Upload Done", Toast.LENGTH_LONG).show();

                                                        }
                                                    }); // here is Url for photo


                                                }

                        //
//                                    Toast.makeText(getApplicationContext(),"Image uploaded sucessfully!",Toast.LENGTH_LONG).show();


                    });
    }

        }
    }
}
