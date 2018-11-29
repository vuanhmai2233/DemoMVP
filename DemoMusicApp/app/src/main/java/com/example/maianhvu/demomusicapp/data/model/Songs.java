package com.example.maianhvu.demomusicapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Songs implements Parcelable {
    private String mNameSong;
    private String mNameArtist;
    private String mImage;
    private String mDuration;
    private String mUri;
    private String mId;

    public Songs() {
    }

    public Songs(String nameSong, String nameArtist, String image, String duration, String uri,
            String id) {
        mNameSong = nameSong;
        mNameArtist = nameArtist;
        mImage = image;
        mDuration = duration;
        mUri = uri;
        mId = id;
    }

    protected Songs(Parcel in) {
        mNameSong = in.readString();
        mNameArtist = in.readString();
        mImage = in.readString();
        mDuration = in.readString();
        mUri = in.readString();
        mId = in.readString();
    }

    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };

    public String getNameSong() {
        return mNameSong;
    }

    public void setNameSong(String nameSong) {
        mNameSong = nameSong;
    }

    public String getNameArtist() {
        return mNameArtist;
    }

    public void setNameArtist(String nameArtist) {
        mNameArtist = nameArtist;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mNameSong);
        dest.writeString(mNameArtist);
        dest.writeString(mImage);
        dest.writeString(mDuration);
        dest.writeString(mUri);
        dest.writeString(mId);
    }
}