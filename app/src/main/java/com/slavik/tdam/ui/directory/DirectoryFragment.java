package com.slavik.tdam.ui.directory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.ui.MainActivity;
import com.slavik.tdam.ui.image.ImageFragment;

public class DirectoryFragment extends Fragment {

    private DirectoryViewModel mViewModel;
    private PhotoAdapter adapter;

    public static DirectoryFragment newInstance() {
        return new DirectoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_directory, container, false);

        RecyclerView rv = v.findViewById(R.id.listPhotos);
        adapter = new PhotoAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DirectoryViewModel.class);
        mViewModel.init(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel.photos()
                .observe(getViewLifecycleOwner(), photos -> adapter.setPhotos(photos));
    }

    public void onClickPhoto(Photo photo) {
        ((MainActivity) requireActivity()).setCurrentPhoto(photo);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, ImageFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}