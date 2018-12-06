package com.example.maianhvu.demomvp.data.source;

import com.example.maianhvu.demomvp.data.source.local.SongsLocal;

public class SongsRepository {
    private SongsDataSource.LocalDataSource mLocalDataSource;

    public SongsRepository(SongsDataSource.LocalDataSource localDataSource) {
        mLocalDataSource = localDataSource;
    }

    public void getData(SongsLocal songsLocal) {
        mLocalDataSource.getData(songsLocal);
    }
}
