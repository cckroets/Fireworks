package com.ckroetsch.hanabi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class Card implements Parcelable {

    @JsonProperty("suit")
    Suit mSuit;

    @JsonProperty("number")
    int mValue;

    private Card() {

    }

    private Card(Parcel parcel) {
        mSuit = Suit.valueOf(parcel.readString());
        mValue = parcel.readInt();
    }

    public Suit getSuit() {
        return mSuit;
    }

    public int getValue() {
        return mValue;
    }

    public void setSuit(Suit mSuit) {
        this.mSuit = mSuit;
    }

    public void setValue(int mValue) {
        this.mValue = mValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mSuit.name());
        parcel.writeInt(mValue);
    }
}
