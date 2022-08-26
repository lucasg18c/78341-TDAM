package com.slavik.tdam.ui.home;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.slavik.tdam.R;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.Init(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView lblDirectories = view.findViewById(R.id.lblDirectories);
        mViewModel.directories().observe(getViewLifecycleOwner(), lblDirectories::setText);

        TextView lblImages = view.findViewById(R.id.lblImages);
        mViewModel.images().observe(getViewLifecycleOwner(), lblImages::setText);

        ImageView img = view.findViewById(R.id.imgView);
        mViewModel.image().observe(getViewLifecycleOwner(), img::setImageBitmap);

        mViewModel.fetchDirectories();
        mViewModel.fetchImages();
        mViewModel.fetchImage();
    }
}