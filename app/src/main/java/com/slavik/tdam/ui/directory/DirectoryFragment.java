package com.slavik.tdam.ui.directory;

import android.annotation.SuppressLint;
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

import com.google.android.material.chip.Chip;
import com.slavik.tdam.R;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.MainActivity;
import com.slavik.tdam.ui.image.ImageFragment;

import java.util.Calendar;
import java.util.List;

public class DirectoryFragment extends Fragment {

    private RecyclerView rv;
    private DirectoryViewModel mViewModel;
    private PhotoAdapter adapter;
    private SwipeRefreshLayout strDirectory;
    private Chip chipOrderBy;
    private Chip chipOrderType;
    private View imgNoWifi;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_directory, container, false);

        strDirectory = v.findViewById(R.id.strDirectory);
        strDirectory.setRefreshing(true);
        chipOrderBy = v.findViewById(R.id.chipOrderBy);
        chipOrderType = v.findViewById(R.id.chipOrderType);

        rv = v.findViewById(R.id.listPhotos);
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

        imgNoWifi = v.findViewById(R.id.imgNoWifi);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DirectoryViewModel.class);
        mViewModel.init(this);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        chipOrderBy.setOnCheckedChangeListener((compoundButton, b) -> {
            mViewModel.setOrderByName(b);
            chipOrderBy.setText(b ? "Nombre" : "Fecha");
            chipOrderBy.setChipIcon(getResources().getDrawable(b
                    ? R.drawable.ic_bookmark
                    : R.drawable.ic_calendar));
        });

        chipOrderType.setOnCheckedChangeListener((compoundButton, b) -> {
            mViewModel.setOrderAsc(b);
            chipOrderType.setText(b ? "Ascendente" : "Descendente");
            chipOrderType.setChipBackgroundColorResource(b
                    ? R.color.mid_blue
                    : R.color.light_blue);
            chipOrderType.setChipStrokeWidth(b ? 0 : .5f);
        });

        mViewModel.photos()
                .observe(getViewLifecycleOwner(), photos -> {
                    adapter.setPhotos(photos);
                    if (photos.size() > 0) {
                        strDirectory.setRefreshing(false);
                        rv.setVisibility(View.VISIBLE);
                    }
                });

        mViewModel.error().observe(getViewLifecycleOwner(), error -> {
            if (error.length() == 0) return;

            strDirectory.setRefreshing(false);
            List<Photo> photos = mViewModel.photos().getValue();

            if (photos == null || photos.size() == 0) {
                imgNoWifi.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            }

            boolean showError = false;
            for (Photo p : photos) {
                if (p.getLowQuality() == null) {
                    showError = true;
                    break;
                }
            }

            boolean isConnected = Boolean.TRUE.equals(((MainActivity) requireActivity()).isConnected().getValue());

            if (showError) {
                imgNoWifi.setVisibility(View.VISIBLE);
                rv.setVisibility(View.GONE);
            }
            
        });

        ((MainActivity) requireActivity()).isConnected().observe(getViewLifecycleOwner(), connected -> {
            if (connected) {
                imgNoWifi.setVisibility(View.GONE);
                rv.setVisibility(View.VISIBLE);
                mViewModel.fetchPhotos();
            }
        });

        strDirectory.setOnRefreshListener(() -> mViewModel.fetchPhotos());
    }

    public void onClickPhoto(Photo photo) {
        ((MainActivity) requireActivity()).setCurrentPhoto(photo);
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .setCustomAnimations(
                        R.anim.slide_in,  // enter
                        R.anim.fade_out,  // exit
                        R.anim.fade_in,   // popEnter
                        R.anim.slide_out  // popExit
                )
                .replace(R.id.fragment_container_view, ImageFragment.class, null)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }
}