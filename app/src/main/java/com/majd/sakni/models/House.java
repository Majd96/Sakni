package com.majd.sakni.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class House implements Parcelable{

    private double totalprice;
    private String numberOfSingleRooms;
    private String bumberOfDoubleRooms;
    private boolean parking;
    private boolean furnished;
    private boolean salon;
    private boolean balcony;
    private String numberOfBathrooms;

    private boolean elevator;
    private boolean wifi;
    private boolean isServicesIncluded;
    private String location;
    private String address;
    private String datePublished;
    private ArrayList<String> pictures;
    private String userId;

    public House() {
    }

    public House(double totalprice, String numberOfSingleRooms, String bumberOfDoubleRooms, boolean parking, boolean furnished, boolean salon, boolean balcony, String numberOfBathrooms, boolean elevator, boolean wifi, boolean isServicesIncluded, String location, String address, String datePublished, ArrayList<String> pictures, String userId) {
        this.totalprice = totalprice;
        this.numberOfSingleRooms = numberOfSingleRooms;
        this.bumberOfDoubleRooms = bumberOfDoubleRooms;
        this.parking = parking;
        this.furnished = furnished;
        this.salon = salon;
        this.balcony = balcony;
        this.numberOfBathrooms = numberOfBathrooms;

        this.elevator = elevator;
        this.wifi = wifi;
        this.isServicesIncluded = isServicesIncluded;
        this.location = location;
        this.address = address;
        this.datePublished = datePublished;
        this.pictures = pictures;
        this.userId = userId;
    }

    protected House(Parcel in) {
        totalprice = in.readDouble();
        numberOfSingleRooms = in.readString();
        bumberOfDoubleRooms = in.readString();
        parking = in.readByte() != 0;
        furnished = in.readByte() != 0;
        salon = in.readByte() != 0;
        balcony = in.readByte() != 0;
        numberOfBathrooms = in.readString();
        elevator = in.readByte() != 0;
        wifi = in.readByte() != 0;
        isServicesIncluded = in.readByte() != 0;
        location = in.readString();
        address = in.readString();
        datePublished = in.readString();
        pictures = in.createStringArrayList();
        userId = in.readString();
    }

    public static final Creator<House> CREATOR = new Creator<House>() {
        @Override
        public House createFromParcel(Parcel in) {
            return new House(in);
        }

        @Override
        public House[] newArray(int size) {
            return new House[size];
        }
    };

    public double getTotalprice() {
        return totalprice;
    }

    public String getNumberOfSingleRooms() {
        return numberOfSingleRooms;
    }

    public String getBumberOfDoubleRooms() {
        return bumberOfDoubleRooms;
    }

    public boolean isParking() {
        return parking;
    }

    public boolean isFurnished() {
        return furnished;
    }

    public boolean isSalon() {
        return salon;
    }

    public boolean isBalcony() {
        return balcony;
    }

    public String getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public boolean isElevator() {
        return elevator;
    }

    public boolean isWifi() {
        return wifi;
    }

    public boolean isServicesIncluded() {
        return isServicesIncluded;
    }

    public String getLocation() {
        return location;
    }

    public String getAddress() {
        return address;
    }

    public String getDatePublished() {
        return datePublished;
    }

    public ArrayList<String> getPictures() {
        return pictures;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(totalprice);
        parcel.writeString(numberOfSingleRooms);
        parcel.writeString(bumberOfDoubleRooms);
        parcel.writeByte((byte) (parking ? 1 : 0));
        parcel.writeByte((byte) (furnished ? 1 : 0));
        parcel.writeByte((byte) (salon ? 1 : 0));
        parcel.writeByte((byte) (balcony ? 1 : 0));
        parcel.writeString(numberOfBathrooms);
        parcel.writeByte((byte) (elevator ? 1 : 0));
        parcel.writeByte((byte) (wifi ? 1 : 0));
        parcel.writeByte((byte) (isServicesIncluded ? 1 : 0));
        parcel.writeString(location);
        parcel.writeString(address);
        parcel.writeString(datePublished);
        parcel.writeStringList(pictures);
        parcel.writeString(userId);
    }
}
