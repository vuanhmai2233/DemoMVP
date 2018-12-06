package com.example.maianhvu.demomvp.screen.main;

import com.example.maianhvu.demomvp.data.model.Songs;
import com.example.maianhvu.demomvp.data.source.SongsRepository;
import com.example.maianhvu.demomvp.data.source.local.SongsLocal;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {
    SongsRepository mSongsRepository;
    MainContract.View mView;

    public MainPresenter(SongsRepository songsRepository, MainContract.View view) {
        mSongsRepository = songsRepository;
        mView = view;
    }

    @Override
    public void getSongs() {
        mSongsRepository.getData(new SongsLocal() {
            @Override
            public void onSuccess(List<Songs> data) {
                mView.getDataSuccess(data);
            }
        });
    }
}
