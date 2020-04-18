package com.apkzube.wallfresco.ui.wallpaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.apkzube.wallfresco.R;

public class WallpaperFragment extends Fragment {

    private WallpaperViewModel wallpaperViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        wallpaperViewModel =
                ViewModelProviders.of(this).get(WallpaperViewModel.class);
        View root = inflater.inflate(R.layout.fragment_wallpaper, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard);
        wallpaperViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}