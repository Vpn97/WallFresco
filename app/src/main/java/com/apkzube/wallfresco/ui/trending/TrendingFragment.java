package com.apkzube.wallfresco.ui.trending;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TabWidget;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.apkzube.wallfresco.R;
import com.apkzube.wallfresco.databinding.FragmentTrendingBinding;
import com.google.android.material.card.MaterialCardView;

public class TrendingFragment extends Fragment {

    private FragmentTrendingBinding mBinding;

    private TrendingViewModel trendingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        trendingViewModel = ViewModelProviders.of(this).get(TrendingViewModel.class);
        mBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_trending,container,false);
        return mBinding.getRoot();
    }
}