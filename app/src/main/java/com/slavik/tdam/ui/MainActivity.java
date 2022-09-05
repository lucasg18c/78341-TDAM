package com.slavik.tdam.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.slavik.tdam.R;
import com.slavik.tdam.data.local.DatabaseTDAM;
import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.data.repository.Repository;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private IRepository repository;
    private Photoset currentPhotoset;
    private Photo currentPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseTDAM db = Room.databaseBuilder(
                getApplicationContext(),
                DatabaseTDAM.class,
                "db-tdam")
                .allowMainThreadQueries()
                .build();

        queue = Volley.newRequestQueue(this);
        repository = new Repository(queue, db, getContentResolver());
//        repository = new MockRepository();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, HomeFragment.class, null)
                    .commit();
        }
    }

    public IRepository getRepository() {
        return repository;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll("");
        }
    }

    public Photoset getCurrentPhotoset() {
        return currentPhotoset;
    }

    public void setCurrentPhotoset(Photoset photoset) {
        currentPhotoset = photoset;
    }

    public Photo getCurrentPhoto() {
        return currentPhoto;
    }

    public void setCurrentPhoto(Photo currentPhoto) {
        this.currentPhoto = currentPhoto;
    }
}