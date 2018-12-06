package com.example.maianhvu.demomvp.screen.playMusic.serviceMusic;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;
import com.example.maianhvu.demomvp.data.model.Songs;
import java.io.IOException;
import java.util.List;

public class serviceMusic extends Service {
    private MediaPlayer mMediaPlayer;
    private List<Songs> mSongsList;
    private int mPosition;
    private IBinder mIBinder;

    @Override
    public IBinder onBind(Intent intent) {
        mMediaPlayer = new MediaPlayer();
        //        mSongsList = intent.getParcelableArrayListExtra(KEY_LIST_SERVICE);
        //        mPosition = intent.getIntExtra(KEY_POSITION_SERVICE, -1);
        //        Toast.makeText(this, mSongsList.get(mPosition).getNameSong(), Toast
        // .LENGTH_SHORT).show();
        //        playMusic();
        return mIBinder;
    }

    @Override
    public void onCreate() {
        mIBinder = new serviceSongs();
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();

        super.onDestroy();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "unBind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    public void playMusic() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mSongsList.get(mPosition).getUri());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public void autoPlay(int position, List<Songs> songsList) {
        mPosition = position;
        mSongsList = songsList;
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
        }
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mSongsList.get(mPosition).getUri());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.start();
    }

    public int BackPlayer() {
        mPosition--;
        if (mPosition < 0) {
            mPosition = mSongsList.size() - 1;
        }
        playMusic();
        return mPosition;
    }

    public void stopPlayer() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.reset();
        } else {
            playMusic();
        }
    }

    public int nextPlayer() {
        mPosition++;
        if (mPosition > mSongsList.size() - 1) {
            mPosition = 0;
        }
        playMusic();
        return mPosition;
    }

    public void pauseMusic() {
        mMediaPlayer.pause();
    }

    public void resetMusic() {
        if (!mMediaPlayer.isPlaying() && mMediaPlayer.getCurrentPosition() != 0) {
            mMediaPlayer.seekTo(mMediaPlayer.getCurrentPosition());
            mMediaPlayer.start();
        } else {
            mMediaPlayer.seekTo(0);
            mMediaPlayer.start();
        }
    }

    public boolean checked() {
        return mMediaPlayer.isPlaying();
    }

    public class serviceSongs extends Binder {
        public serviceMusic getBoundService() {
            return serviceMusic.this;
        }
    }
}
