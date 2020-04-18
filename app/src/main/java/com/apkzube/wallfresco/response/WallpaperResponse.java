package com.apkzube.wallfresco.response;

/*
*
* {
            "id": 556667,
            "width": 2430,
            "height": 3004,
            "url": "https://www.pexels.com/photo/affection-afterglow-backlit-blur-556667/",
            "photographer": "luizclas",
            "photographer_url": "https://www.pexels.com/@luizclas-170497",
            "photographer_id": 170497,
            "src": {
                "original": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg",
                "large2x": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940",
                "large": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&h=650&w=940",
                "medium": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&h=350",
                "small": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&h=130",
                "portrait": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=1200&w=800",
                "landscape": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&fit=crop&h=627&w=1200",
                "tiny": "https://images.pexels.com/photos/556667/pexels-photo-556667.jpeg?auto=compress&cs=tinysrgb&dpr=1&fit=crop&h=200&w=280"
            }
        }
*
* */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WallpaperResponse implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("width")
    @Expose
    private String width;

    @SerializedName("height")
    @Expose
    private String height;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("photographer")
    @Expose
    private String photographer;

    @SerializedName("photographer_url")
    @Expose
    private String photographerUrl;

    @SerializedName("photographer_id")
    @Expose
    private String photographerId;

    @SerializedName("src")
    @Expose
    private  SrcResponse src;

    public WallpaperResponse() {
    }

    public WallpaperResponse(String id, String width, String height, String url, String photographer, String photographerUrl, String photographerId, SrcResponse src) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.photographer = photographer;
        this.photographerUrl = photographerUrl;
        this.photographerId = photographerId;
        this.src = src;
    }

    protected WallpaperResponse(Parcel in) {
        id = in.readString();
        width = in.readString();
        height = in.readString();
        url = in.readString();
        photographer = in.readString();
        photographerUrl = in.readString();
        photographerId = in.readString();
        src = in.readParcelable(SrcResponse.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(url);
        dest.writeString(photographer);
        dest.writeString(photographerUrl);
        dest.writeString(photographerId);
        dest.writeParcelable(src, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<WallpaperResponse> CREATOR = new Creator<WallpaperResponse>() {
        @Override
        public WallpaperResponse createFromParcel(Parcel in) {
            return new WallpaperResponse(in);
        }

        @Override
        public WallpaperResponse[] newArray(int size) {
            return new WallpaperResponse[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public SrcResponse getSrc() {
        return src;
    }

    public void setSrc(SrcResponse src) {
        this.src = src;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getPhotographerUrl() {
        return photographerUrl;
    }

    public void setPhotographerUrl(String photographerUrl) {
        this.photographerUrl = photographerUrl;
    }

    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
    }
}
