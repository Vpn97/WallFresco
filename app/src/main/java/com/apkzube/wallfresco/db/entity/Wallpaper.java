package com.apkzube.wallfresco.db.entity;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.apkzube.wallfresco.BR;
import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.util.DateTypeConverters;

import java.util.Date;

@Entity(tableName = "wallpaper_mst")
public class Wallpaper  extends BaseObservable  implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="wallpaper_id")
    private int wallpaperId;

    @ColumnInfo(name = "id")
    private String id;

    @ColumnInfo(name = "width")
    private String width;

    @ColumnInfo(name = "height")
    private String height;

    @ColumnInfo(name = "url")
    private String url;

    @ColumnInfo(name = "photographer")
    private String photographer;

    @ColumnInfo(name = "photographerUrl")
    private String photographerUrl;

    @ColumnInfo(name = "photographerId")
    private String photographerId;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "category")
    private String category;

    @ColumnInfo(name = "search_string")
    private String searchString;

    //src-------------------------------

    @ColumnInfo(name="original")
    private String original;

    @ColumnInfo(name="large2x")
    private String large2x;

    @ColumnInfo(name="large")
    private String large;

    @ColumnInfo(name="medium")
    private String medium;

    @ColumnInfo(name="small")
    private String small;

    @ColumnInfo(name="portrait")
    private String portrait;

    @ColumnInfo(name="landscape")
    private String landscape;

    @ColumnInfo(name="tiny")
    private String tiny;

    //src end ----------------------------

    @ColumnInfo(name = "is_downloaded")
    private boolean isDownloaded;

    @ColumnInfo(name = "created_date", defaultValue = "CURRENT_TIMESTAMP")
    @TypeConverters(DateTypeConverters.class)
    private Date createdDate;


    //----------------


    public Wallpaper(int wallpaperId, String id, String width, String height, String url, String photographer, String photographerUrl, String photographerId, boolean isFavorite, String category, String searchString, String original, String large2x, String large, String medium, String small, String portrait, String landscape, String tiny, boolean isDownloaded, Date createdDate) {
        this.wallpaperId = wallpaperId;
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.photographer = photographer;
        this.photographerUrl = photographerUrl;
        this.photographerId = photographerId;
        this.isFavorite = isFavorite;
        this.category = category;
        this.searchString = searchString;
        this.original = original;
        this.large2x = large2x;
        this.large = large;
        this.medium = medium;
        this.small = small;
        this.portrait = portrait;
        this.landscape = landscape;
        this.tiny = tiny;
        this.isDownloaded = isDownloaded;
        this.createdDate = createdDate;
    }

    @Ignore
    public Wallpaper() {
    }

    @Ignore
    protected Wallpaper(Parcel in) {
        wallpaperId = in.readInt();
        id = in.readString();
        width = in.readString();
        height = in.readString();
        url = in.readString();
        photographer = in.readString();
        photographerUrl = in.readString();
        photographerId = in.readString();
        isFavorite = in.readByte() != 0;
        category = in.readString();
        searchString = in.readString();
        original = in.readString();
        large2x = in.readString();
        large = in.readString();
        medium = in.readString();
        small = in.readString();
        portrait = in.readString();
        landscape = in.readString();
        tiny = in.readString();
        isDownloaded = in.readByte() != 0;
    }

    @Override
    @Ignore
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(wallpaperId);
        dest.writeString(id);
        dest.writeString(width);
        dest.writeString(height);
        dest.writeString(url);
        dest.writeString(photographer);
        dest.writeString(photographerUrl);
        dest.writeString(photographerId);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeString(category);
        dest.writeString(searchString);
        dest.writeString(original);
        dest.writeString(large2x);
        dest.writeString(large);
        dest.writeString(medium);
        dest.writeString(small);
        dest.writeString(portrait);
        dest.writeString(landscape);
        dest.writeString(tiny);
        dest.writeByte((byte) (isDownloaded ? 1 : 0));
    }

    @Override
    @Ignore
    public int describeContents() {
        return 0;
    }

    @Ignore
    public static final Creator<Wallpaper> CREATOR = new Creator<Wallpaper>() {
        @Override
        public Wallpaper createFromParcel(Parcel in) {
            return new Wallpaper(in);
        }

        @Override
        public Wallpaper[] newArray(int size) {
            return new Wallpaper[size];
        }
    };

    public int getWallpaperId() {
        return wallpaperId;
    }

    public void setWallpaperId(int wallpaperId) {
        this.wallpaperId = wallpaperId;
    }

    @Bindable
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
        notifyPropertyChanged(BR.width);
    }

    @Bindable
    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
        notifyPropertyChanged(BR.height);
    }

    @Bindable
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
        notifyPropertyChanged(BR.photographer);
    }

    @Bindable
    public String getPhotographerUrl() {
        return photographerUrl;
    }

    public void setPhotographerUrl(String photographerUrl) {
        this.photographerUrl = photographerUrl;
        notifyPropertyChanged(BR.photographerUrl);
    }

    @Bindable
    public String getPhotographerId() {
        return photographerId;
    }

    public void setPhotographerId(String photographerId) {
        this.photographerId = photographerId;
        notifyPropertyChanged(BR.photographerId);
    }

    @Bindable
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
        notifyPropertyChanged(BR.favorite);
    }

    @Bindable
    public String getOriginal() {
        return original;
    }


    public void setOriginal(String original) {
        this.original = original;
        notifyPropertyChanged(BR.original);
    }

    @Bindable
    public String getLarge2x() {
        return large2x;
    }

    public void setLarge2x(String large2x) {
        this.large2x = large2x;
        notifyPropertyChanged(BR.large2x);
    }

    @Bindable
    public String getLarge() {
        return large;
    }


    public void setLarge(String large) {
        this.large = large;
        notifyPropertyChanged(BR.large);
    }

    @Bindable
    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
        notifyPropertyChanged(BR.medium);
    }

    @Bindable
    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
        notifyPropertyChanged(BR.small);
    }

    @Bindable
    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
        notifyPropertyChanged(BR.portrait);
    }

    @Bindable
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
        notifyPropertyChanged(BR.category);
    }

    @Bindable
    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
        notifyPropertyChanged(BR.searchString);
    }

    @Bindable
    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
        notifyPropertyChanged(BR.landscape);
    }

    @Bindable
    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
        notifyPropertyChanged(BR.tiny);
    }

    @Bindable
    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
        notifyPropertyChanged(BR.downloaded);
    }

    @Bindable
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
        notifyPropertyChanged(BR.createdDate);
    }

/*
    @BindingAdapter(value={"isFavorite"}, requireAll=false)
    public void bindSrcCompat(ImageView imageView,boolean isFavorite){
        // Your setter code goes here, like setDrawable or similar
        if(isFavorite){
            imageView.setImageResource(R.drawable.ic_favorite_checked);
        }else{
            imageView.setImageResource(R.drawable.ic_favorite_unchecked);
        }
    }*/

}
