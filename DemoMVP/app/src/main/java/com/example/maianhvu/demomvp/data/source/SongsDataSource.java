package com.example.maianhvu.demomvp.data.source;

import com.example.maianhvu.demomvp.data.source.local.SongsLocal;

public interface SongsDataSource {
    interface LocalDataSource {
        void getData(SongsLocal songsLocal);
    }

    interface RemoteDataSource {

    }
}
