package com.apkzube.wallfresco.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.adapter.WallpaperAdapter;
import com.apkzube.wallfresco.databinding.FragmentFavoriteBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.util.DataStorage;

import java.util.ArrayList;

public class FavoriteFragment extends Fragment {

    private FavoriteViewModel model;
    private FragmentFavoriteBinding mBinding;
    private RecyclerView rvFavorite;
    private LottieAnimationView favoriteAnimation;
    private ArrayList<Wallpaper> favoriteWallpapersList;
    private WallpaperAdapter adapter;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_favorite,container,false);
        model = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        mBinding.setModel(model);
        allocation();
        setEvent();

        return mBinding.getRoot();
    }

    private void allocation() {

    }

    private void setEvent() {

    }
}