package com.apkzube.wallfresco.util;

import java.util.Random;

public class CommonRestURL {

    public static final String BASE_URL="https://api.pexels.com/";

    //pixel  urls

    public  static  final String WALLPAPER__URL = "search";
    public  static  final String TRENDING__URL = "curated";
    public  static  final String PHOTO = "photos";

    public static int SERVER_CALL_COUNT = 0;

    public static final int PER_PAGE_TRENDING = 40;
    public static final int PER_PAGE_WALLPAPER = 50;
    public static final int PER_PAGE_CHIP_WALLPAPER = 40;


    public static String[] API_KEYS = new String[]{
            "563492ad6f91700001000001d78ec715992c4698b6ab8aeccf983927",
            "563492ad6f9170000100000187c1b0b0bc6446b69db6876f9a3b9a9c",
            "563492ad6f91700001000001680d1bf09a5d482396e0e99e975a020f",
            "563492ad6f91700001000001cc7af2602c714badb4e1a181ab1c80d9",
            "563492ad6f91700001000001110e20df03714d9e80cb1a3e7fb13f50",
            "563492ad6f91700001000001a335c28e638e46059d5c3e27a9a21b88",
            "563492ad6f917000010000013e9dbcd3809841dc929210ef407c9c54",
            "563492ad6f91700001000001c05c435b54cf4cea9e0e1d8080e4edb5",
            "563492ad6f91700001000001be202c2c95dd4e0794ea2cb87d09ff98",
            "563492ad6f91700001000001155e4b7ee29845fd92d52abc6f10c72f"};

    public static String getApiKEY() {
        Random random = new Random();
        return API_KEYS[random.nextInt(API_KEYS.length)];
    }

}
