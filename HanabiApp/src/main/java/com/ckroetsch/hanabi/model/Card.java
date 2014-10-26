package com.ckroetsch.hanabi.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author curtiskroetsch
 */
public final class Card implements Parcelable {

    Suit suit;

    Integer number;

    private Card() {

    }

    private Card(Parcel parcel) {
        suit = Suit.valueOf(parcel.readString());
        number = parcel.readInt();
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return number;
    }

    public void setSuit(Suit mSuit) {
        this.suit = mSuit;
    }

    public void setValue(int mValue) {
        this.number = mValue;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(suit.name());
        parcel.writeInt(number);
    }
}
