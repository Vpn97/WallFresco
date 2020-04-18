package com.apkzube.wallfresco.ui.wallpaper;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.adapter.WallpaperAdapter;
import com.apkzube.wallfresco.databinding.FragmentWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.apkzube.wallfresco.util.Constant;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class WallpaperFragment extends Fragment {

    private WallpaperViewModel model;
    private RecyclerView rvWallpaper;
    private FragmentWallpaperBinding mBinding;
    private ChipGroup chipsCategory;
    private ArrayList<String> category;
    private WallpaperAdapter adapter;
    private ArrayList<Wallpaper> wallpapers;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       // rootView = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_wallpaper,container,false);

        allocation();
        setEvent();
        return mBinding.getRoot();
    }

    private void allocation() {
        model = ViewModelProviders.of(this).get(WallpaperViewModel.class);
        mBinding.setModel(model);
        mBinding.wallpaperFragmentLoading.playAnimation();
        rvWallpaper=mBinding.rvWallpaper;
        chipsCategory=mBinding.chipsCategory;

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.column_count),
                LinearLayoutManager.VERTICAL);
        rvWallpaper.setLayoutManager(manager);

        model.setWallpapers();
        wallpapers=new ArrayList<>();
        adapter=new WallpaperAdapter(mBinding.getRoot().getContext(),wallpapers);
        rvWallpaper.setAdapter(adapter);

        category = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.wallpaper_category)));
        setCategoryChips(category);

    }

    private void setEvent() {
        model.getWallpaperLiveData().observe(getViewLifecycleOwner(),listWallpaper->{
            mBinding.wallpaperFragmentLoading.setVisibility(View.GONE);
            wallpapers.addAll(listWallpaper);
            adapter.notifyDataSetChanged();
        } );
    }

    private void setCategoryChips(ArrayList<String> categories) {
        for (String category : categories) {
            final Chip mChip = (Chip) this.getLayoutInflater().inflate(R.layout.item_chip_category, null, false);
            mChip.setText(category);
            chipsCategory.addView(mChip);
        }
    }
}