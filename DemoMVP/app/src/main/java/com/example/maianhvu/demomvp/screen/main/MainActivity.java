package com.example.maianhvu.demomvp.screen.main;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import com.example.maianhvu.demomvp.R;
import com.example.maianhvu.demomvp.data.model.Songs;
import com.example.maianhvu.demomvp.data.source.SongsRepository;
import com.example.maianhvu.demomvp.data.source.local.SongsLocalDataSource;
import com.example.maianhvu.demomvp.screen.main.adapter.MainAdapter;
import com.example.maianhvu.demomvp.screen.playMusic.PlayMusicActivity;
import com.example.maianhvu.demomvp.screen.playMusic.serviceMusic.serviceMusic;
import com.example.maianhvu.demomvp.screen.util.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements MainContract.View, OnItemClickListener {

    public static final String KEY_POSITION_SERVICE = "keyposition";
    public static final String KEY_LIST_SERVICE = "keylist";
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    public static final String KEY_LIST = "LISTSONGS";
    public static final String KEY_POSITION = "KEYPOSITION";
    public static serviceMusic mService;
    private List<Songs> mSongsList;
    private int mPosition;
    private boolean mIRunning;
    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            serviceMusic.serviceSongs serviceSongs = (serviceMusic.serviceSongs) service;
            mService = serviceSongs.getBoundService();
            mIRunning = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mIRunning = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermissionREAD_EXTERNAL_STORAGE(this);
        initData();
        initView();
    }

    private void initData() {
        SongsLocalDataSource songsLocalDataSource = new SongsLocalDataSource(this);
        SongsRepository songsRepository = new SongsRepository(songsLocalDataSource);
        MainContract.Presenter presenter = new MainPresenter(songsRepository, this);
        presenter.getSongs();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        MainAdapter mainAdapter = new MainAdapter(this);
        mainAdapter.setOnItemClickListener(MainActivity.this);
        recyclerView.setAdapter(mainAdapter);
        mainAdapter.updateList(mSongsList);
    }

    @Override
    public void getDataSuccess(List<Songs> songsList) {
        mSongsList = songsList;
    }

    @Override
    public void onItemClick(int position, List<Songs> songsList) {
        mPosition = position;
        Intent intentSerVice = new Intent(this, serviceMusic.class);
        intentSerVice.putExtra(KEY_POSITION_SERVICE, mPosition);
        intentSerVice.putParcelableArrayListExtra(KEY_LIST_SERVICE,
                (ArrayList<? extends Parcelable>) mSongsList);
        bindService(intentSerVice, mServiceConnection, BIND_AUTO_CREATE);
        Intent intent = new Intent(getApplicationContext(), PlayMusicActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LIST, (ArrayList<? extends Parcelable>) mSongsList);
        bundle.putInt(KEY_POSITION, mPosition);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context,
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions((Activity) context, new String[] { permission },
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
            int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "GET_ACCOUNTS Denied", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
