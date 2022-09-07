package com.slavik.tdam.ui.directory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.slavik.tdam.R;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;
import com.slavik.tdam.ui.image.ImageFragment;

import java.util.Calendar;

public class DirectoryFragment extends Fragment {

    private DirectoryViewModel mViewModel;
    private PhotoAdapter adapter;
    private SwipeRefreshLayout strDirectory;

    public static DirectoryFragment newInstance() {
        return new DirectoryFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_directory, container, false);

        strDirectory = v.findViewById(R.id.strDirectory);

        RecyclerView rv = v.findViewById(R.id.listPhotos);
        adapter = new PhotoAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2);

        rv.setLayoutManager(layoutManager);
        rv.setAdapter(adapter);
        rv.setNestedScrollingEnabled(false);

        Photoset photoset = ((MainActivity) requireActivity()).getCurrentPhotoset();

        Calendar c = photoset.getCreated();
        String date = c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.MONTH) + "/" + c.get(Calendar.YEAR);

        ((TextView) v.findViewById(R.id.lblTitle)).setText(photoset.getTitle());

        TextView lblDescription = v.findViewById(R.id.lblDescription);
        lblDescription.setText(photoset.getDescription());
        if (photoset.getDescription().length() == 0) {
            lblDescription.setVisibility(View.GONE);
        }

        ((TextView) v.findViewById(R.id.lblCreated)).setText("Creada " + date);
        ((TextView) v.findViewById(R.id.lblViewsCount)).setText(String.valueOf(photoset.viewsCount()));
        ((TextView) v.findViewById(R.id.lblCommentsCount)).setText(String.valueOf(photoset.commentsCount()));
        ((TextView) v.findViewById(R.id.lblPhotosCount)).setText(photoset.getPhotos().size() + " fotos");

        v.findViewById(R.id.btnBack).setOnClickListener(
                b -> requireActivity().getSupportFragmentManager().popBackStack());


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
                .observe(getViewLifecycleOwner(), photos -> {
                    adapter.setPhotos(photos);
                    strDirectory.setRefreshing(false);
                });

        strDirectory.setOnRefreshListener(() -> mViewModel.fetchPhotos());
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