package com.slavik.tdam.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;
import com.slavik.tdam.ui.SettingsFragment;
import com.slavik.tdam.ui.directory.DirectoryFragment;
import com.slavik.tdam.ui.error.ErrorFragment;

import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel mViewModel;
    private PhotosetsAdapter photosetsAdapter;
    private SwipeRefreshLayout strHome;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        LinearLayoutManager lm = new LinearLayoutManager(requireContext());
        photosetsAdapter = new PhotosetsAdapter(this);

        strHome = v.findViewById(R.id.strHome);
        strHome.setRefreshing(true);

        RecyclerView rv = v.findViewById(R.id.listPhotosets);
        rv.setLayoutManager(lm);
        rv.setAdapter(photosetsAdapter);

        v.findViewById(R.id.btnSettings)
                .setOnClickListener(b -> navigateTo(SettingsFragment.class, true));

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        mViewModel.init(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel.photosets().observe(getViewLifecycleOwner(),
                p -> {
                    photosetsAdapter.setPhotosets(p);
                    if (p.size() > 0)
                        strHome.setRefreshing(false);
                });

        strHome.setOnRefreshListener(() -> mViewModel.fetchPhotosets());

        mViewModel.error().observe(getViewLifecycleOwner(), error -> {
            if (error.length() == 0) return;
            strHome.setRefreshing(false);
            List<Photoset> photosets = mViewModel.photosets().getValue();
            if (photosets == null || photosets.size() == 0) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, ErrorFragment.class, null)
                        .commit();

            }
        });
    }

    public void onPhotosetClicked(Photoset photoset) {
        ((MainActivity) requireActivity()).setCurrentPhotoset(photoset);
        navigateTo(DirectoryFragment.class, true);
    }

    public void navigateTo(Class<? extends Fragment> destination, boolean animate) {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                )
                .replace(R.id.fragment_container_view, destination, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}