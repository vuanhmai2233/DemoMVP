package com.example.maianhvu.demomvp.screen.main;

import com.example.maianhvu.demomvp.data.model.Songs;
import java.util.List;

public interface MainContract {
    interface View {
        void getDataSuccess(List<Songs> songsList);
    }

    //
    interface Presenter {
        void getSongs();
    }
}
