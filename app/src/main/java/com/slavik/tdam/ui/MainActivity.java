package com.slavik.tdam.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
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
    private TextView lblNoConection;
    private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
            boolean connected = activeNetworkInfo != null && activeNetworkInfo.isConnected();
            lblNoConection.setVisibility(connected ? View.GONE : View.VISIBLE);

            if (!connected) {
                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, "CHANNEL_CONECTION")
                        .setSmallIcon(R.drawable.ic_camera_wifi)
                        .setContentTitle("Navegando sin conexión")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText("Podrás acceder a contenido nuevo cuando la conexión se restablezca."))
                        .setPriority(NotificationCompat.PRIORITY_MAX);

                NotificationManagerCompat notificationManager =
                        NotificationManagerCompat.from(MainActivity.this);

                int id = (int) (Math.random() * 10000);
                notificationManager.notify(id, builder.build());
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();
        lblNoConection = findViewById(R.id.lblNoConection);

        DatabaseTDAM db = Room.databaseBuilder(
                        getApplicationContext(),
                        DatabaseTDAM.class,
                        "db-tdam")
                .allowMainThreadQueries()
                .build();

        queue = Volley.newRequestQueue(this);
        repository = new Repository(queue, db, getContentResolver());

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, HomeFragment.class, null)
                    .commit();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Alerta de conexión";
            String description = "Notificaciones sobre el estado de conexión del dispositivo en tiempo de ejecución";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("CHANNEL_CONECTION", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
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