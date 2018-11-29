package com.example.maianhvu.demomusicapp.screen.view.listMusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.maianhvu.demomusicapp.R;
import com.example.maianhvu.demomusicapp.data.model.Songs;
import com.example.maianhvu.demomusicapp.data.source.localDataSource.GetDataFromLocal;
import com.example.maianhvu.demomusicapp.screen.view.PlayMusic.playMusicActivity;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SAdapter.OnItemClickListener {
    public static final String KEY_POSITION ="nameMusic";
    public static final String KEY_LIST = "LISTMUSIC";
    private int mPosition;

    private List<Songs> mSongsList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSongsList = new ArrayList<>();

        initView();

    }

    private void initView() {
        GetDataFromLocal getDataFromLocal = new GetDataFromLocal(this);
        RecyclerView recyclerView = findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager
                layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        SAdapter    mAdapter = new SAdapter(this);
        mAdapter.setOnItemClickListener(MainActivity.this);
        recyclerView.setAdapter(mAdapter);
        mSongsList = getDataFromLocal.getSongsData();
        mAdapter.updateList(mSongsList);
    }

    @Override
    public void onItemClick(int position, List<Songs> songsList) {
        mPosition = position;
        Intent i = new Intent(getApplicationContext(), playMusicActivity.class);
        Bundle b = new Bundle();
        b.putParcelableArrayList(KEY_LIST, (ArrayList<? extends Parcelable>) mSongsList);
        b.putInt(KEY_POSITION,mPosition);
        i.putExtras(b);
        startActivity(i);
    }
}

