package com.apkzube.wallfresco.util;

import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.response.PelexsResponse;
import com.apkzube.wallfresco.response.WallpaperResponse;

import java.util.ArrayList;

public class ConverterUtil {


    public static Wallpaper convertResponseToEntity(WallpaperResponse response){
        Wallpaper wallpaper=new Wallpaper();
        wallpaper.setId(response.getId());
        wallpaper.setWidth(response.getWidth());
        wallpaper.setHeight(response.getHeight());
        wallpaper.setUrl(response.getUrl());
        wallpaper.setPhotographer(response.getPhotographer());
        wallpaper.setPhotographerUrl(response.getPhotographerUrl());
        wallpaper.setPhotographerId(response.getPhotographerId());
        wallpaper.setOriginal(response.getSrc().getOriginal());
        wallpaper.setLarge2x(response.getSrc().getLarge2x());
        wallpaper.setLarge(response.getSrc().getLarge());
        wallpaper.setMedium(response.getSrc().getMedium());
        wallpaper.setSmall(response.getSrc().getSmall());
        wallpaper.setPortrait(response.getSrc().getPortrait());
        wallpaper.setLandscape(response.getSrc().getLandscape());
        wallpaper.setTiny(response.getSrc().getTiny());
        return wallpaper;
    }

    public ArrayList<Wallpaper> convertResponseToEntityList(PelexsResponse responses){
        ArrayList<Wallpaper> wallpapers=new ArrayList<>();
        for (WallpaperResponse wallpaperResponse: responses.getWallpapers()) {
            if(wallpaperResponse !=null){
                wallpapers.add(convertResponseToEntity(wallpaperResponse));
            }
        }
        return wallpapers;
     }

}
