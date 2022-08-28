package com.slavik.tdam.ui.directory;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;

public class DirectoryFragment extends Fragment {

    private DirectoryViewModel mViewModel;

    public static DirectoryFragment newInstance() {
        return new DirectoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_directory, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DirectoryViewModel.class);

        Photoset ps = ((MainActivity) requireActivity()).getCurrentPhotoset();
        System.out.println(ps);
        // TODO: Use the ViewModel
    }

}