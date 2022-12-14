package com.slavik.tdam.ui;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.preference.PreferenceManager;
import androidx.room.Room;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.slavik.tdam.R;
import com.slavik.tdam.data.local.DatabaseTDAM;
import com.slavik.tdam.data.repository.IRepository;
import com.slavik.tdam.data.repository.Repository;
import com.slavik.tdam.model.Photo;
import com.slavik.tdam.model.Photoset;
import com.slavik.tdam.ui.home.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private static final String CONNECTION_LOST_NOTIFY_METHOD = "conection_lost_notify_method";
    private static final String CONNECTION_LOST_NOTIFY = "conection_lost_notify";
    private static final String NOTIFICATIONS_CHANNEL_NAME = "CHANNEL_CONECTION";
    private final MutableLiveData<Boolean> _isConnected = new MutableLiveData<>();
    private RequestQueue queue;
    private IRepository repository;
    private Photoset currentPhotoset;
    private Photo currentPhoto;
    private TextView lblNoConection;
    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager != null
                    ? connectivityManager.getActiveNetworkInfo()
                    : null;

            boolean connected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            lblNoConection.setVisibility(connected ? View.GONE : View.VISIBLE);

            _isConnected.setValue(connected);

            if (!connected) {

                SharedPreferences sharedPreferences =
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                boolean notify = sharedPreferences.getBoolean(CONNECTION_LOST_NOTIFY, true);

                if (!notify) return;

                boolean notifyByPush =
                        sharedPreferences.getBoolean(CONNECTION_LOST_NOTIFY_METHOD, true);

                if (notifyByPush) {

                    NotificationCompat.Builder builder = new NotificationCompat
                            .Builder(MainActivity.this, NOTIFICATIONS_CHANNEL_NAME)
                            .setSmallIcon(R.drawable.ic_camera_wifi)
                            .setContentTitle(getString(R.string.exploring_no_wifi))
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(getString(R.string.no_wifi_message)))
                            .setPriority(NotificationCompat.PRIORITY_MAX);

                    NotificationManagerCompat notificationManager =
                            NotificationManagerCompat.from(MainActivity.this);

                    int id = (int) (Math.random() * 10000);
                    notificationManager.notify(id, builder.build());
                    return;
                }

                Snackbar.make(
                        MainActivity.this.findViewById(android.R.id.content),
                        getString(R.string.exploring_no_wifi),
                        Snackbar.LENGTH_LONG
                ).show();

            }
        }
    };

    public LiveData<Boolean> isConnected() {
        return _isConnected;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
            }, 77);

        }

        createNotificationChannel();
        lblNoConection = findViewById(R.id.lblNoConection);

        DatabaseTDAM db = Room.databaseBuilder(
                        getApplicationContext(),
                        DatabaseTDAM.class,
                        "db-tdam")
                .allowMainThreadQueries()
                .build();

        queue = Volley.newRequestQueue(this);
        repository = new Repository(this, db, queue);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, HomeFragment.class, null)
                    .commit();
        }
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.connection_warning);
        String description = getString(R.string.connection_channel_description);
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel =
                new NotificationChannel(NOTIFICATIONS_CHANNEL_NAME, name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(networkChangeReceiver);
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