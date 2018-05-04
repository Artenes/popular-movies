package com.artenesnogueira.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Image implements Parcelable {

    private final String path;
    private final int width;
    private final int height;

    public Image(String path, int width, int height) {
        this.path = path;
        this.width = width;
        this.height = height;
    }

    public String getPath() {
        return path;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    Image(Parcel in) {
        path = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<Image> CREATOR = new Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(path);
        dest.writeInt(width);
        dest.writeInt(height);
    }

}
