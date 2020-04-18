package com.apkzube.wallfresco.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "wallpaper")
public class Wallpaper {

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

    @ColumnInfo(name = "src")
    private Src src;

    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    @ColumnInfo(name = "is_downloaded")
    private boolean isDownloaded;

    @ColumnInfo(name = "created_date",defaultValue = "CURRENT_TIMESTAMP")
    private Date createdDate;

    public Wallpaper() {
    }


}
