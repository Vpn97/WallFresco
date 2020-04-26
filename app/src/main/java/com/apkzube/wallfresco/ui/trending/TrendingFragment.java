package com.apkzube.wallfresco.ui.trending;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.apkzube.wallfresco.databinding.FragmentTrendingBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;

import java.util.ArrayList;

public class TrendingFragment extends Fragment {

    private FragmentTrendingBinding mBinding;
    private TrendingViewModel model;
    private WallpaperAdapter adapter;
    private ArrayList<Wallpaper> wallpapers;
    private LottieAnimationView  animationTrending;
    private RecyclerView rvTrending;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        model = ViewModelProviders.of(this).get(TrendingViewModel.class);
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_trending,container,false);
        mBinding.setModel(model);
        allocation();
        setEvent();
        return mBinding.getRoot();
    }

    private void allocation() {
        rvTrending=mBinding.rvTrending;
        animationTrending=mBinding.animationTrending;
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.column_count),
                LinearLayoutManager.VERTICAL);
        rvTrending.setLayoutManager(manager);

        wallpapers=new ArrayList<>();
        adapter=new WallpaperAdapter(mBinding.getRoot().getContext(),wallpapers);
        rvTrending.setAdapter(adapter);
    }

    private void setEvent() {
        model.getTrendingWallpaperWeb();
        model.getTrendingWallpaper().observe(getViewLifecycleOwner(), wallpapersList -> {
            if(wallpapersList.size()>0) {
                animationTrending.setVisibility(View.GONE);
            }
            wallpapers.clear();
            wallpapers.addAll(wallpapersList);
            adapter.notifyDataSetChanged();
        });
    }
}