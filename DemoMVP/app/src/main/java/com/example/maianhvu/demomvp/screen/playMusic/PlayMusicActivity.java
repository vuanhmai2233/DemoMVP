package com.example.maianhvu.demomvp.screen.playMusic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.maianhvu.demomvp.R;
import com.example.maianhvu.demomvp.data.model.Songs;
import java.util.ArrayList;
import java.util.List;

import static com.example.maianhvu.demomvp.screen.main.MainActivity.KEY_LIST;
import static com.example.maianhvu.demomvp.screen.main.MainActivity.KEY_POSITION;
import static com.example.maianhvu.demomvp.screen.main.MainActivity.mService;

public class PlayMusicActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton mImageButtonPlay, mImageButtonPause, mImageButtonBack, mImageButtonNext,
            mImageButtonStop;
    private TextView mTextViewNameSongs;
    private ImageView mImageViewSongs;
    private int mPosition;
    private List<Songs> mSongsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        mSongsList = new ArrayList<>();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mSongsList = bundle.getParcelableArrayList(KEY_LIST);
            mPosition = bundle.getInt(KEY_POSITION);
        }

        mService.autoPlay(mPosition, mSongsList);
        mTextViewNameSongs = findViewById(R.id.textviewNameSong);
        mImageButtonBack = findViewById(R.id.buttonBack);
        mImageButtonPlay = findViewById(R.id.buttonPlay);
        mImageButtonPause = findViewById(R.id.buttonPause);
        mImageButtonStop = findViewById(R.id.buttonStop);
        mImageButtonNext = findViewById(R.id.buttonNext);
        mImageViewSongs = findViewById(R.id.imageViewSong);
        mImageButtonPlay.setVisibility(View.GONE);
        mImageButtonPause.setVisibility(View.VISIBLE);
        mTextViewNameSongs.setText(mSongsList.get(mPosition).getNameSong());
        Glide.with(getApplicationContext())
                .load(mSongsList.get(mPosition).getImage())
                .into(mImageViewSongs);
        mImageButtonBack.setOnClickListener(this);
        mImageButtonPlay.setOnClickListener(this);
        mImageButtonPause.setOnClickListener(this);
        mImageButtonStop.setOnClickListener(this);
        mImageButtonNext.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBack:
                mPosition = mService.BackPlayer();
                mTextViewNameSongs.setText(mSongsList.get(mPosition).getNameSong());
                Glide.with(getApplicationContext())
                        .load(mSongsList.get(mPosition).getImage())
                        .into(mImageViewSongs);
                break;
            case R.id.buttonPlay:
                mService.resetMusic();
                setButtonPlayPause();
                break;
            case R.id.buttonPause:
                mService.pauseMusic();
                setButtonPlayPause();
                break;
            case R.id.buttonStop:
                mService.stopPlayer();
                break;
            case R.id.buttonNext:
                mPosition = mService.nextPlayer();
                mTextViewNameSongs.setText(mSongsList.get(mPosition).getNameSong());
                Glide.with(getApplicationContext())
                        .load(mSongsList.get(mPosition).getImage())
                        .into(mImageViewSongs);
                break;
        }
    }

    private void setButtonPlayPause() {
        if (mService.checked()) {
            mImageButtonPause.setVisibility(View.VISIBLE);
            mImageButtonPlay.setVisibility(View.GONE);
        } else {
            mImageButtonPlay.setVisibility(View.VISIBLE);
            mImageButtonPause.setVisibility(View.GONE);
        }
    }
}
