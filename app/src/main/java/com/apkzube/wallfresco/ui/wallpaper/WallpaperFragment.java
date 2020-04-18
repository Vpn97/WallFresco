package com.apkzube.wallfresco.ui.wallpaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.databinding.FragmentWallpaperBinding;

public class WallpaperFragment extends Fragment {

    private WallpaperViewModel model;
    private View rootView;
    private FragmentWallpaperBinding mBinding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_wallpaper,container,false);

        allocation();
        setEvent();
        return rootView;
    }

    private void allocation() {
        model = ViewModelProviders.of(this).get(WallpaperViewModel.class);
        mBinding.wallpaperFragmentLoading.playAnimation();

    }

    private void setEvent() {

    }
}