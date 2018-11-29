package com.example.maianhvu.demomusicapp.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import com.example.maianhvu.demomusicapp.data.model.Songs;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



public class ServiceMusic extends Service {
    private MediaPlayer mediaPlayer;
    private List<Songs> mSongsList;
    private int mPosition;

    private IBinder mIBinder = new serviceMusic();
    @Override
    public IBinder onBind(Intent intent) {

        mSongsList = intent.getParcelableArrayListExtra("aaaa");
        mPosition = intent.getIntExtra("aaa",0);
        playMusic();
        return mIBinder;
    }
    @Override
    public void onCreate() {
//        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
        super.onCreate();
        mSongsList = new ArrayList<>();
        mediaPlayer = new MediaPlayer();
    }

    @Override
    public boolean onUnbind(Intent intent) {
//        Toast.makeText(this, "onUnbind", Toast.LENGTH_SHORT).show();
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
        mediaPlayer.stop();
    }

    public void playMusic() {
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            }
        mediaPlayer.reset();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(mSongsList.get(mPosition).getUri());
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }
    public class serviceMusic extends Binder {
        public ServiceMusic getBoundService() {
            return ServiceMusic.this;
        }
    }
    public void stopPlayer(){
        if(mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        } else {
            mediaPlayer.start();
        }

    }
    public int nextPlayer() {
        mPosition++;
        if(mPosition > mSongsList.size()-1)
        { mPosition = 0;}

            play();

        return mPosition;

    }
    public int BackPlayer() {

            mPosition--;
            if (mPosition<0){
                mPosition = mSongsList.size()-1;
            }
            play();
        return mPosition;
    }
    public boolean checked(){
       return mediaPlayer.isPlaying();
    }
    public void pauseMusic(){
            mediaPlayer.pause();
    }
    public void resetMusic(){
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition());
        mediaPlayer.start();
    }
    public void play(){
        if(mediaPlayer !=null){
            if (mediaPlayer.isPlaying()){
                mediaPlayer.stop();
                mediaPlayer.reset();
            }
            try {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDataSource(mSongsList.get(mPosition).getUri());
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.start();
        }
    }
}
