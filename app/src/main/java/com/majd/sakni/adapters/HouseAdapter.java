package com.majd.sakni.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.majd.sakni.R;
import com.majd.sakni.models.House;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HouseAdapter extends RecyclerView.Adapter<HouseAdapter.HouseViewHolder> {

    private ArrayList<House> houses;
    Context mContext;

    public HouseAdapter(ArrayList<House> houses, Context mContext) {
        this.houses = houses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public HouseAdapter.HouseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.house_item, viewGroup, false);
        HouseViewHolder holder = new HouseViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HouseAdapter.HouseViewHolder houseViewHolder, int i) {

        try {
            houseViewHolder.bind(i);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {

        if (houses == null) return 0;
        else return houses.size();
    }

    public class HouseViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView price;
        TextView furnished;
        TextView location;
        ImageView houseImage;

        public HouseViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.dateTextView);
            price = itemView.findViewById(R.id.priceTextView);
            furnished = itemView.findViewById(R.id.furnishedtextView);
            location = itemView.findViewById(R.id.textView3);
            houseImage = itemView.findViewById(R.id.imageView);
        }

        public void bind(int position) throws Exception {
            date.setText(houses.get(position).getDatePublished());
            price.setText(houses.get(position).getTotalprice() + " $");
            furnished.setText((houses.get(position).isFurnished()) ? "Furnished" : "Unfurnished");
            location.setText(houses.get(position).getLocation());

            Uri uri = Uri.parse(houses.get(position).getPictures().get(0)).buildUpon().build();
            Picasso.get().load(uri).into(houseImage);


        }
    }
}
