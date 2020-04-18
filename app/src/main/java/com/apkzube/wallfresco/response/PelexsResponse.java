package com.apkzube.wallfresco.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PelexsResponse implements Parcelable {

    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("per_page")
    @Expose
    private int wallpaperPerPage;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("next_page")
    @Expose
    private String nextPageUrl;

    @SerializedName("photos")
    @Expose
    private ArrayList<WallpaperResponse> wallpapers;

    public PelexsResponse() {
    }

    public PelexsResponse(int page, int wallpaperPerPage, int totalResults, String url, String nextPageUrl, ArrayList<WallpaperResponse> wallpapers) {
        this.page = page;
        this.wallpaperPerPage = wallpaperPerPage;
        this.totalResults = totalResults;
        this.url = url;
        this.nextPageUrl = nextPageUrl;
        this.wallpapers = wallpapers;
    }


    protected PelexsResponse(Parcel in) {
        page = in.readInt();
        wallpaperPerPage = in.readInt();
        totalResults = in.readInt();
        url = in.readString();
        nextPageUrl = in.readString();
        wallpapers = in.createTypedArrayList(WallpaperResponse.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(page);
        dest.writeInt(wallpaperPerPage);
        dest.writeInt(totalResults);
        dest.writeString(url);
        dest.writeString(nextPageUrl);
        dest.writeTypedList(wallpapers);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PelexsResponse> CREATOR = new Creator<PelexsResponse>() {
        @Override
        public PelexsResponse createFromParcel(Parcel in) {
            return new PelexsResponse(in);
        }

        @Override
        public PelexsResponse[] newArray(int size) {
            return new PelexsResponse[size];
        }
    };

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getWallpaperPerPage() {
        return wallpaperPerPage;
    }

    public void setWallpaperPerPage(int wallpaperPerPage) {
        this.wallpaperPerPage = wallpaperPerPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public ArrayList<WallpaperResponse> getWallpapers() {
        return wallpapers;
    }

    public void setWallpapers(ArrayList<WallpaperResponse> wallpapers) {
        this.wallpapers = wallpapers;
    }
}
