package com.slavik.tdam.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photoset;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private PhotosetsAdapter photosetsAdapter;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager lm = new LinearLayoutManager(requireContext());
        photosetsAdapter = new PhotosetsAdapter(this);

        RecyclerView rv = v.findViewById(R.id.listPhotosets);
        rv.setLayoutManager(lm);
        rv.setAdapter(photosetsAdapter);

        return v;
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

        mViewModel.photosets().observe(getViewLifecycleOwner(),
                p -> photosetsAdapter.setPhotosets(p));

        mViewModel.fetchPhotosets();
    }

    public void onPhotosetClicked(Photoset photoset) {
        System.out.println(photoset.getTitle());
    }
}