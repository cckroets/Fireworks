package com.ckroetsch.hanabi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author curtiskroetsch
 */
public final class Player implements Parcelable {

    @JsonProperty("name")
    String mName;

    @JsonProperty("hand")
    List<Card> mHand;

    private Player() {

    }

    private Player(Parcel parcel) {
        mName = parcel.readString();
        mHand = new ArrayList<Card>();
        parcel.readList(mHand, null);
    }

    public String getName() {
        return mName;
    }

    public List<Card> getHand() {
        return mHand;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public void setHand(List<Card> mHand) {
        this.mHand = mHand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeList(mHand);
    }
}
