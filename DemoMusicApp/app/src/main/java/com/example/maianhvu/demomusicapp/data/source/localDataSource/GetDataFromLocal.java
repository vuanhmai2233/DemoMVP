package com.example.maianhvu.demomusicapp.data.source.localDataSource;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.example.maianhvu.demomusicapp.data.model.Songs;
import java.util.ArrayList;
import java.util.List;

public class GetDataFromLocal {
    private Context mContext;
    private List<Songs> mSongsList;

    public GetDataFromLocal(Context context) {
        mContext = context;
        mSongsList = new ArrayList<>();
    }

    public List<Songs> getSongsData() {
        ContentResolver musicResolver = mContext.getContentResolver();
        Uri URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(URI, null, null, null, null);
        if (musicCursor != null && musicCursor.moveToFirst()) {
            int uri = musicCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int nameSong = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.TITLE);
            int nameArtist = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media.ARTIST);
            int image = musicCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int id = musicCursor.getColumnIndex(android.provider.MediaStore.Audio.Media._ID);
            int duration =
                    musicCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                String currenURI = musicCursor.getString(uri);
                String currenNameSong = musicCursor.getString(nameSong);
                String currenNameArtist = musicCursor.getString(nameArtist);
                String currentImage = String.valueOf(ContentUris.withAppendedId(
                        Uri.parse("content://media/external/audio/albumart"),
                        musicCursor.getInt(image)));
                String currenId = musicCursor.getString(id);
                String currenDuration = musicCursor.getString(duration);

                mSongsList.add(new Songs(currenNameSong, currenNameArtist, currentImage, currenDuration, currenURI, currenId));
            } while (musicCursor.moveToNext());
        }
        musicCursor.close();
        return mSongsList;
    }
}
