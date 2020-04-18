package com.apkzube.wallfresco.model;


import androidx.room.Entity;

@Entity(tableName = "src")
class Src {

    private int srcId;
    private  String wallpaperId;
    private String original;
    private String large2x;
    private String large;
    private String medium;
    private String small;
    private String portrait;
    private String landscape;
    private String tiny;
}
