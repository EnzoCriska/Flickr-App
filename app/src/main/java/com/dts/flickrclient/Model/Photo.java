
package com.dts.flickrclient.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable{


    @SerializedName("farm")
    private Long mFarm;
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
    @SerializedName("owner")
    private String mOwner;
    @SerializedName("photos")
    private Photos mPhotos;
    @SerializedName("secret")
    private String mSecret;
    @SerializedName("server")
    private String mServer;
    @SerializedName("stat")
    private String mStat;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("url_s")
    private String mUrlS;
    @SerializedName("width_s")
    private String mWidthS;

    public Photo() {
    }

    public Photo(String mId, String mOwner, String mSecret, String mServer, Long mFarm, String mTitle,
                 Long mIspublic, Long mIsfriend, Long mIsfamily, String mUrlS, String mHeightS, String mWidthS) {
        this.mFarm = mFarm;
        this.mHeightS = mHeightS;
        this.mId = mId;
        this.mIsfamily = mIsfamily;
        this.mIsfriend = mIsfriend;
        this.mIspublic = mIspublic;
        this.mOwner = mOwner;
        this.mPhotos = mPhotos;
        this.mSecret = mSecret;
        this.mServer = mServer;
        this.mStat = mStat;
        this.mTitle = mTitle;
        this.mUrlS = mUrlS;
        this.mWidthS = mWidthS;
    }


//    protected Photo(Parcel in) {
//        this.mFarm = in.readLong();
//        this.mHeightS = in.readString();
//        this.mId = in.readString();
//        this.mIsfamily = in.readLong();
//        this.mIsfriend = in.readLong();
//        this.mIspublic = in.readLong();
//        this.mOwner = in.readString();
//        this.mSecret = in.readString();
//        this.mServer = in.readString();
//        this.mStat = in.readString();
//        this.mTitle = in.readString();
//        this.mUrlS = in.readString();
//        this.mWidthS = in.readString();
//    }
//
//    public static final Creator<Photo> CREATOR = new Creator<Photo>() {
//        @Override
//        public Photo createFromParcel(Parcel in) {
//            return new Photo(in);
//        }
//
//        @Override
//        public Photo[] newArray(int size) {
//            return new Photo[size];
//        }
//    };


    protected Photo(Parcel in) {
        if (in.readByte() == 0) {
            mFarm = null;
        } else {
            mFarm = in.readLong();
        }
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
        mOwner = in.readString();
        mSecret = in.readString();
        mServer = in.readString();
        mStat = in.readString();
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

    public Long getFarm() {
        return mFarm;
    }

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

    public String getOwner() {
        return mOwner;
    }

    public Photos getPhotos() {
        return mPhotos;
    }

    public String getSecret() {
        return mSecret;
    }

    public String getServer() {
        return mServer;
    }

    public String getStat() {
        return mStat;
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
        if (mFarm == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(mFarm);
        }
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
        parcel.writeString(mOwner);
        parcel.writeString(mSecret);
        parcel.writeString(mServer);
        parcel.writeString(mStat);
        parcel.writeString(mTitle);
        parcel.writeString(mUrlS);
        parcel.writeString(mWidthS);
    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel parcel, int i) {
//        parcel.writeString(mId);
//        parcel.writeString(mOwner);
//        parcel.writeString(mSecret);
//        parcel.writeString(mServer);
//        parcel.writeLong(mFarm);
//        parcel.writeString(mTitle);
//        parcel.writeLong(mIspublic);
//        parcel.writeLong(mIsfriend);
//        parcel.writeLong(mIsfamily);
//        parcel.writeString(mUrlS);
//        parcel.writeString(mHeightS);
//        parcel.writeString(mWidthS);
//    }

    public static class Builder {

        private Long mFarm;
        private String mHeightS;
        private String mId;
        private Long mIsfamily;
        private Long mIsfriend;
        private Long mIspublic;
        private String mOwner;
        private Photos mPhotos;
        private String mSecret;
        private String mServer;
        private String mStat;
        private String mTitle;
        private String mUrlS;
        private String mWidthS;

        public Photo.Builder withFarm(Long farm) {
            mFarm = farm;
            return this;
        }

        public Photo.Builder withHeightS(String heightS) {
            mHeightS = heightS;
            return this;
        }

        public Photo.Builder withId(String id) {
            mId = id;
            return this;
        }

        public Photo.Builder withIsfamily(Long isfamily) {
            mIsfamily = isfamily;
            return this;
        }

        public Photo.Builder withIsfriend(Long isfriend) {
            mIsfriend = isfriend;
            return this;
        }

        public Photo.Builder withIspublic(Long ispublic) {
            mIspublic = ispublic;
            return this;
        }

        public Photo.Builder withOwner(String owner) {
            mOwner = owner;
            return this;
        }

        public Photo.Builder withPhotos(Photos photos) {
            mPhotos = photos;
            return this;
        }

        public Photo.Builder withSecret(String secret) {
            mSecret = secret;
            return this;
        }

        public Photo.Builder withServer(String server) {
            mServer = server;
            return this;
        }

        public Photo.Builder withStat(String stat) {
            mStat = stat;
            return this;
        }

        public Photo.Builder withTitle(String title) {
            mTitle = title;
            return this;
        }

        public Photo.Builder withUrlS(String urlS) {
            mUrlS = urlS;
            return this;
        }

        public Photo.Builder withWidthS(String widthS) {
            mWidthS = widthS;
            return this;
        }

        public Photo build() {
            Photo photo = new Photo();
            photo.mFarm = mFarm;
            photo.mHeightS = mHeightS;
            photo.mId = mId;
            photo.mIsfamily = mIsfamily;
            photo.mIsfriend = mIsfriend;
            photo.mIspublic = mIspublic;
            photo.mOwner = mOwner;
            photo.mPhotos = mPhotos;
            photo.mSecret = mSecret;
            photo.mServer = mServer;
            photo.mStat = mStat;
            photo.mTitle = mTitle;
            photo.mUrlS = mUrlS;
            photo.mWidthS = mWidthS;
            return photo;
        }

    }

}
