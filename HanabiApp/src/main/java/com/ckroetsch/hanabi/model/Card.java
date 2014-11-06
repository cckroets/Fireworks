package com.ckroetsch.hanabi.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author curtiskroetsch
 */
public final class Card implements Parcelable {

    Suit suit;

    Integer number;

    List<Suit> knownSuit;

    Boolean knowNumber;

    private Card() {

    }

    private Card(Parcel parcel) {
        suit = Suit.valueOf(parcel.readString());
        number = parcel.readInt();
        final int size = parcel.readInt();
        knownSuit = new ArrayList<Suit>(size);
        for (int i = 0; i < size; i++) {
            knownSuit.add(Suit.valueOf(parcel.readString()));
        }
        knowNumber = parcel.readByte() != 0;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    public boolean getKnowNumber() { return knowNumber; }

    public List<Suit> getKnownSuit() { return knownSuit; }

    public void setSuit(Suit mSuit) {
        this.suit = mSuit;
    }

    public void setNumber(int mValue) {
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
        parcel.writeInt(knownSuit == null ? 0 : knownSuit.size());
        if (knownSuit != null) {
            for (int s = 0; s < knownSuit.size(); s++) {
                parcel.writeString(knownSuit.get(s).name());
            }
        }
        parcel.writeByte((byte) (knowNumber ? 1 : 0));
    }

    public static final Creator<Card> CREATOR = new Creator<Card>() {
        @Override
        public Card createFromParcel(Parcel parcel) {
            return new Card(parcel);
        }

        @Override
        public Card[] newArray(int i) {
            return new Card[i];
        }
    };

    @Override
    public String toString() {
        return String.format("Card %d, %s", number, suit);
    }
}
