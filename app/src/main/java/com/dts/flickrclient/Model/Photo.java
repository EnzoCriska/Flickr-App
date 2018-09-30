
package com.dts.flickrclient.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable{

    @SerializedName("height_s")
    private String mHeightS;
    @SerializedName("id")
    private String mId;
    @SerializedName("isfamily")
    private Long mIsfamily;
    @SerializedName("isfriend")
    private Long mIsfriend;
    @SerializedName("ispublic")
    private Long mIspublic;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url_s")
    private String mUrlS;
    @SerializedName("width_s")
    private String mWidthS;

    public Photo() {
    }

    public Photo(String mId, String mTitle, Long mIspublic, Long mIsfriend, Long mIsfamily, String mUrlS, String mHeightS, String mWidthS) {
        this.mHeightS = mHeightS;
        this.mId = mId;
        this.mIsfamily = mIsfamily;
        this.mIsfriend = mIsfriend;
        this.mIspublic = mIspublic;
        this.mTitle = mTitle;
        this.mUrlS = mUrlS;
        this.mWidthS = mWidthS;
    }

    protected Photo(Parcel in) {
        mHeightS = in.readString();
        mId = in.readString();
        if (in.readByte() == 0) {
            mIsfamily = null;
        } else {
            mIsfamily = in.readLong();
        }
        if (in.readByte() == 0) {
            mIsfriend = null;
        } else {
            mIsfriend = in.readLong();
        }
        if (in.readByte() == 0) {
            mIspublic = null;
        } else {
            mIspublic = in.readLong();
        }
        mTitle = in.readString();
        mUrlS = in.readString();
        mWidthS = in.readString();
    }

    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
        @Override
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        @Override
        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };

    public String getHeightS() {
        return mHeightS;
    }

    public String getId() {
        return mId;
    }

    public Long getIsfamily() {
        return mIsfamily;
    }

    public Long getIsfriend() {
        return mIsfriend;
    }

    public Long getIspublic() {
        return mIspublic;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrlS() {
        return mUrlS;
    }

    public String getWidthS() {
        return mWidthS;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mHeightS);
        parcel.writeString(mId);
        if (mIsfamily == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mIsfamily);
        }
        if (mIsfriend == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mIsfriend);
        }
        if (mIspublic == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mIspublic);
        }

        parcel.writeString(mTitle);
        parcel.writeString(mUrlS);
        parcel.writeString(mWidthS);
    }

    public boolean equals(Object obj){
        if(obj instanceof Photo){
            Photo photo2 = (Photo) obj;
            if (this.mId.equals(photo2.getId())){
                if (this.mTitle.equals(photo2.getTitle())) return true;
            }else return false;
        }else return false;

        return false;
    }



}
