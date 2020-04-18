package com.apkzube.wallfresco.ui.wallpaper;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.List;

public class WallpaperViewModel extends AndroidViewModel{

    private Application application;
    private MutableLiveData<List<Wallpaper>> wallpaperLiveData=new MutableLiveData<>();

    public WallpaperViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }




    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Wallpaper>> getWallpaperLiveData() {
        return wallpaperLiveData;
    }

    public void setWallpaperLiveData(MutableLiveData<List<Wallpaper>> wallpaperLiveData) {
        this.wallpaperLiveData = wallpaperLiveData;
    }
}