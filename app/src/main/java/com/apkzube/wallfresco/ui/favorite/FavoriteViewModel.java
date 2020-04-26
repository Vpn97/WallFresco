package com.apkzube.wallfresco.ui.favorite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.db.repo.WallRepository;

import java.util.List;

public class FavoriteViewModel extends AndroidViewModel {

    private Application application;
    private LiveData<List<Wallpaper>> favoriteLiveData=new MutableLiveData<List<Wallpaper>>();
    private   WallRepository repository;

    public FavoriteViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository=new WallRepository(this.application);
    }

    public LiveData<List<Wallpaper>> getFavoriteLiveWallpapers(){
        favoriteLiveData=repository.getFavoriteWallpapers();
        return favoriteLiveData;
    }


    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    public LiveData<List<Wallpaper>> getFavoriteLiveData() {
        return favoriteLiveData;
    }

    public void setFavoriteLiveData(LiveData<List<Wallpaper>> favoriteLiveData) {
        this.favoriteLiveData = favoriteLiveData;
    }
}