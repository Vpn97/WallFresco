package com.apkzube.wallfresco.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.apkzube.wallfresco.db.entity.Wallpaper;

public class SetWallpaperViewModel extends AndroidViewModel {
    private Application application;
    private MutableLiveData<Wallpaper> wallpaperLiveData=new MutableLiveData<>();

    public SetWallpaperViewModel(@NonNull Application application) {
        super(application);
        this.application=application;
    }


    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public MutableLiveData<Wallpaper> getWallpaperLiveData() {
        return wallpaperLiveData;
    }

    public void setWallpaperLiveData(MutableLiveData<Wallpaper> wallpaperLiveData) {
        this.wallpaperLiveData = wallpaperLiveData;
    }
}
