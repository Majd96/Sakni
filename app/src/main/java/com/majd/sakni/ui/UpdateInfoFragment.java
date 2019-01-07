package com.majd.sakni.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.majd.sakni.R;
import com.majd.sakni.models.User;

public class UpdateInfoFragment extends Fragment {
    private ImageView imageButtonImage;
    private ImageView imageButtonName;
    private ImageView imageViewProfile;
    private EditText nameEditText;
    private TextView phoneTextView;
    private FirebaseUser firebaseUser;

    public UpdateInfoFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_update_info,container,false);
        imageButtonImage=view.findViewById(R.id.imageButton);
        imageButtonName=view.findViewById(R.id.imageButton2);
        imageViewProfile=view.findViewById(R.id.profile_image);
        nameEditText=view.findViewById(R.id.editText);
        phoneTextView=view.findViewById(R.id.textView4);
        nameEditText.setTag(R.drawable.ic_mode_edit);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Query query = FirebaseDatabase.getInstance()
                .getReference().child("users").orderByKey().equalTo(firebaseUser.getUid());

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {


                        User user = childDataSnapshot.getValue(User.class);
                        nameEditText.setText(user.getName());
                        phoneTextView.setText(user.getPhoneNo());
                        if(user.getName().equalsIgnoreCase("null")){
                            nameEditText.setText("");
                            nameEditText.setHint("Full name");
                        }

                        Log.d("******************",user.getName());
                        Log.d("*******************",user.getPhoneNo());

                    }

                }

            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        imageButtonName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (nameEditText.getTag().equals(R.drawable.ic_mode_edit)) {
                    nameEditText.setFocusableInTouchMode(true);
                    nameEditText.setFocusable(true);
                    nameEditText.setEnabled(true);

                    imageButtonName.setImageResource(R.drawable.ic_check);
                    nameEditText.setTag(R.drawable.ic_check);
                } else {

                    if (!nameEditText.getText().toString().isEmpty()) {
                        FirebaseDatabase.getInstance()
                                .getReference().child("users")
                                .child(firebaseUser.getUid())
                                .child("name").setValue(nameEditText.getText().toString());
                        imageButtonName.setImageResource(R.drawable.ic_mode_edit);
                        nameEditText.setTag(R.drawable.ic_mode_edit);
                        nameEditText.setFocusable(false);
                        nameEditText.setEnabled(false);

                        Toast.makeText(getContext(), "Name  is updated successfully", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(getContext(), "Name is empty", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });








        return view;
    }
}
