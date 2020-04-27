package com.apkzube.wallfresco.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.airbnb.lottie.LottieAnimationView;
import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.adapter.WallpaperAdapter;
import com.apkzube.wallfresco.databinding.FragmentFavoriteBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.ArrayList;
import java.util.List;

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
        rvFavorite=mBinding.rvFavorite;
        favoriteAnimation=mBinding.favoriteAmination;
        favoriteWallpapersList=new ArrayList<>();

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.column_count),
                LinearLayoutManager.VERTICAL);
        rvFavorite.setLayoutManager(manager);

        adapter=new WallpaperAdapter(mBinding.getRoot().getContext(),favoriteWallpapersList);
        rvFavorite.setAdapter(adapter);

    }

    private void setEvent() {
        model.getFavoriteLiveWallpapers().observe(getViewLifecycleOwner(), this::onAdapterChanged);
    }

    private void onAdapterChanged(List<Wallpaper> wallpapers) {
        favoriteAnimation.setVisibility(View.GONE);
        favoriteWallpapersList.clear();
        favoriteWallpapersList.addAll(wallpapers);
        adapter.notifyDataSetChanged();
        updateUI(favoriteWallpapersList);
    }


    public void updateUI(ArrayList<Wallpaper> wallpapers) {
        if (wallpapers.isEmpty()) {
            favoriteAnimation.setVisibility(View.VISIBLE);
            favoriteAnimation.setScaleType(ImageView.ScaleType.FIT_CENTER);
            favoriteAnimation.setAnimation(R.raw.empty_box);
            favoriteAnimation.playAnimation();
        }else{
            favoriteAnimation.setVisibility(View.GONE);
        }
    }
}