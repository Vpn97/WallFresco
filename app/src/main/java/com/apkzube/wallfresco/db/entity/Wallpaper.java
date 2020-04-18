package com.apkzube.wallfresco.db.entity;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.apkzube.wallfresco.BR;
import com.bumptech.glide.Glide;

import java.util.Date;

@Entity(tableName = "wallpaper_mst")
public class Wallpaper  extends BaseObservable {

    @PrimaryKey
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

    @ColumnInfo(name="medium")
    private String large;

    @ColumnInfo(name="")
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
    private Date createdDate;


    //----------------


    public Wallpaper(String id, String width, String height, String url, String photographer, String photographerUrl, String photographerId, boolean isFavorite, String category, String searchString, String original, String large2x, String large, String medium, String small, String portrait, String landscape, String tiny, boolean isDownloaded, Date createdDate) {
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

}
