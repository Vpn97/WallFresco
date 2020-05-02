package com.apkzube.wallfresco.ui.wallpaper;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.adapter.WallpaperPagedAdapter;
import com.apkzube.wallfresco.databinding.FragmentWallpaperBinding;
import com.apkzube.wallfresco.db.entity.Wallpaper;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.Arrays;

public class WallpaperFragment extends Fragment implements ChipGroup.OnCheckedChangeListener {

    private WallpaperViewModel model;
    private RecyclerView rvWallpaper;
    private FragmentWallpaperBinding mBinding;
    private ChipGroup chipsCategory;
    private ArrayList<String> category;

    //pagination
    private PagedList<Wallpaper>  wallpaperPagedList;
    private WallpaperPagedAdapter  pagedAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_wallpaper,container,false);

        allocation();
        setEvent();
        return mBinding.getRoot();
    }

    private void allocation() {
        model = ViewModelProviders.of(this).get(WallpaperViewModel.class);
        mBinding.setModel(model);
        rvWallpaper=mBinding.rvWallpaper;
        chipsCategory=mBinding.chipsCategory;

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(getResources().getInteger(R.integer.column_count),
                LinearLayoutManager.VERTICAL);
        rvWallpaper.setLayoutManager(manager);

        category = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.wallpaper_category)));
        setCategoryChips(category);

        //pagination
        pagedAdapter=new WallpaperPagedAdapter(mBinding.getRoot().getContext());
        rvWallpaper.setAdapter(pagedAdapter);
    }

    private void setEvent() {
        chipsCategory.setOnCheckedChangeListener(this);
        model.getSearchLiveData().setValue("");
        //new pagination
        model.getWallpaperPagedList().observe(getViewLifecycleOwner(), wallpaperLiveData -> {
            if(wallpaperLiveData.size()!=0){
                mBinding.wallpaperFragmentLoading.setVisibility(View.GONE);
            }
                wallpaperPagedList=wallpaperLiveData;
                pagedAdapter.submitList(wallpaperPagedList);

        });

    }

    private void setCategoryChips(ArrayList<String> categories) {
        for (String category : categories) {
            final Chip mChip = (Chip) this.getLayoutInflater().inflate(R.layout.item_chip_category, null, false);
            mChip.setText(category);
            chipsCategory.addView(mChip);
        }
    }

    @Override
    public void onCheckedChanged(ChipGroup chipGroup, int chipId) {
        mBinding.wallpaperFragmentLoading.setVisibility(View.VISIBLE);
        if (chipId == View.NO_ID) {
            model.getSearchLiveData().setValue("");
        }else{
            Chip currentChip = chipGroup.findViewById(chipId);
            String currentChipText = currentChip.getText() +" Wallpaper";
            model.getSearchLiveData().setValue(currentChipText);
        }
    }

}