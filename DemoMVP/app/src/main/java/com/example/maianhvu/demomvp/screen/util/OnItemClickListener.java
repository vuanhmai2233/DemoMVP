package com.example.maianhvu.demomvp.screen.util;

import com.example.maianhvu.demomvp.data.model.Songs;
import java.util.List;

public interface OnItemClickListener {
    void onItemClick(int position, List<Songs> songsList);
}
