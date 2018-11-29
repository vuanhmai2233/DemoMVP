package com.example.maianhvu.demomusicapp.screen.view.PlayMusic;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.maianhvu.demomusicapp.R;
import com.example.maianhvu.demomusicapp.data.model.Songs;
import com.example.maianhvu.demomusicapp.screen.view.listMusic.SAdapter;
import com.example.maianhvu.demomusicapp.service.ServiceMusic;
import java.util.ArrayList;
import java.util.List;

import static com.example.maianhvu.demomusicapp.screen.view.listMusic.MainActivity.KEY_LIST;
import static com.example.maianhvu.demomusicapp.screen.view.listMusic.MainActivity.KEY_POSITION;


public class playMusicActivity extends AppCompatActivity
        implements View.OnClickListener, SAdapter.OnItemClickListener {
    ImageButton mButtonPlay, mButtonStop,mButtonBack,mButtonNext,mButtonPause;
    private ServiceMusic mMyService;
    private boolean mIRunning = false;
    private int mPosition;
    private List<Songs> mSongsList;
    TextView nameTextView;

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ServiceMusic.serviceMusic muSic = (ServiceMusic.serviceMusic) service;
            mMyService = muSic.getBoundService();
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
        setContentView(R.layout.activity_play_music);

        mSongsList = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSongsList = bundle.getParcelableArrayList(KEY_LIST);
            mPosition = bundle.getInt(KEY_POSITION);
//            Log.d("xxx", "onCreate: " + mPosition);
        }

        nameTextView = findViewById(R.id.textViewName);
        mButtonPlay = findViewById(R.id.btnPlay);
        mButtonPause = findViewById(R.id.btnPause);
        mButtonStop = findViewById(R.id.btnStop);
        mButtonBack = findViewById(R.id.btnBack);
        mButtonNext = findViewById(R.id.btnNext);
        mButtonPause.setOnClickListener(this);
        mButtonPlay.setOnClickListener(this);
        mButtonStop.setOnClickListener(this);
        mButtonBack.setOnClickListener(this);
        mButtonNext.setOnClickListener(this);
        nameTextView.setText(mSongsList.get(mPosition).getNameSong());
        Intent i = new Intent(this, ServiceMusic.class);
        i.putExtra("aaa",mPosition);
        i.putParcelableArrayListExtra("aaaa", (ArrayList<? extends Parcelable>) mSongsList);
        bindService(i, mServiceConnection, BIND_AUTO_CREATE);
        mButtonPause.setVisibility(View.VISIBLE);
        mButtonPlay.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnPlay:
                mMyService.resetMusic();
                playPause();
                break;
            case R.id.btnPause:
                mMyService.pauseMusic();
                playPause();
                break;
            case R.id.btnStop:

                mMyService.stopPlayer();
                break;
            case R.id.btnBack:
                mPosition= mMyService.BackPlayer();
                nameTextView.setText(mSongsList.get(mPosition).getNameSong());
                break;
            case R.id.btnNext:
                mPosition= mMyService.nextPlayer();
                nameTextView.setText(mSongsList.get(mPosition).getNameSong());
                break;
        }

    }@Override
    public void onItemClick(int position, List<Songs> songsList) {
        mPosition = position;
        mSongsList = songsList;
    }
    private void playPause(){
        if(mMyService.checked()){
            mButtonPause.setVisibility(View.VISIBLE);
            mButtonPlay.setVisibility(View.GONE);
            }else {
            mButtonPlay.setVisibility(View.VISIBLE);
            mButtonPause.setVisibility(View.GONE);
        }
    }
}
