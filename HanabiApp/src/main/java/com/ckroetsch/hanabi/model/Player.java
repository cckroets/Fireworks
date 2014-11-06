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

    String name;
    List<Card> hand;

    private Player() {
    }

    private Player(Parcel parcel) {
        name = parcel.readString();
        hand = new ArrayList<Card>();
        parcel.readList(hand, null);
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public void setHand(List<Card> mHand) {
        this.hand = mHand;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeList(hand);
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel parcel) {
            return new Player(parcel);
        }

        @Override
        public Player[] newArray(int i) {
            return new Player[i];
        }
    };
}
