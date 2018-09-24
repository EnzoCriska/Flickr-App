
package com.dts.flickrclient.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


@SuppressWarnings("unused")
public class Photos {

    @SerializedName("page")
    private Long mPage;
    @SerializedName("pages")
    private Long mPages;
    @SerializedName("perpage")
    private Long mPerpage;
    @SerializedName("photo")
    private List<Photo> mPhoto;
    @SerializedName("total")
    private Long mTotal;

    public Long getPage() {
        return mPage;
    }

    public Long getPages() {
        return mPages;
    }

    public Long getPerpage() {
        return mPerpage;
    }

    public List<Photo> getPhoto() {
        return mPhoto;
    }

    public Long getTotal() {
        return mTotal;
    }

    public static class Builder {

        private Long mPage;
        private Long mPages;
        private Long mPerpage;
        private List<Photo> mPhoto;
        private Long mTotal;

        public Photos.Builder withPage(Long page) {
            mPage = page;
            return this;
        }

        public Photos.Builder withPages(Long pages) {
            mPages = pages;
            return this;
        }

        public Photos.Builder withPerpage(Long perpage) {
            mPerpage = perpage;
            return this;
        }

        public Photos.Builder withPhoto(List<Photo> photo) {
            mPhoto = photo;
            return this;
        }

        public Photos.Builder withTotal(Long total) {
            mTotal = total;
            return this;
        }

        public Photos build() {
            Photos photos = new Photos();
            photos.mPage = mPage;
            photos.mPages = mPages;
            photos.mPerpage = mPerpage;
            photos.mPhoto = mPhoto;
            photos.mTotal = mTotal;
            return photos;
        }

    }

}
