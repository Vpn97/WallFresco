package com.apkzube.wallfresco.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "wallpaper_mst")
public class WallpaperEntity {

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

    @ColumnInfo(name = "photographer_url")
    private String photographer_url;

    @ColumnInfo(name = "photographer_id")
    private String photographer_id;

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


    public WallpaperEntity(String id, String width, String height, String url, String photographer, String photographer_url, String photographer_id, boolean isFavorite, String category, String searchString, String original, String large2x, String large, String medium, String small, String portrait, String landscape, String tiny, boolean isDownloaded, Date createdDate) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.url = url;
        this.photographer = photographer;
        this.photographer_url = photographer_url;
        this.photographer_id = photographer_id;
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
    public WallpaperEntity() {
    }

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

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public String getPhotographer_url() {
        return photographer_url;
    }

    public void setPhotographer_url(String photographer_url) {
        this.photographer_url = photographer_url;
    }

    public String getPhotographer_id() {
        return photographer_id;
    }

    public void setPhotographer_id(String photographer_id) {
        this.photographer_id = photographer_id;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getOriginal() {
        return original;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String getLarge2x() {
        return large2x;
    }

    public void setLarge2x(String large2x) {
        this.large2x = large2x;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSearchString() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString = searchString;
    }

    public String getLandscape() {
        return landscape;
    }

    public void setLandscape(String landscape) {
        this.landscape = landscape;
    }

    public String getTiny() {
        return tiny;
    }

    public void setTiny(String tiny) {
        this.tiny = tiny;
    }

    public boolean isDownloaded() {
        return isDownloaded;
    }

    public void setDownloaded(boolean downloaded) {
        isDownloaded = downloaded;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
