package com.example.maianhvu.demomvp.screen.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.maianhvu.demomvp.R;
import com.example.maianhvu.demomvp.data.model.Songs;
import com.example.maianhvu.demomvp.screen.util.OnItemClickListener;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
    private ArrayList<Songs> mSongsList;
    private LayoutInflater mInflater;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public MainAdapter(Context context) {
        mContext = context;
        mSongsList = new ArrayList<>();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void updateList(List<Songs> songsList) {
        mSongsList.addAll(songsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mInflater = LayoutInflater.from(viewGroup.getContext());
        View view = mInflater.inflate(R.layout.item_listview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext).load(mSongsList.get(i).getImage()).into(viewHolder.mImageView);
        viewHolder.mTextViewTitle.setText(mSongsList.get(i).getNameSong());
        viewHolder.mTextViewArtist.setText(mSongsList.get(i).getNameArtist());
    }

    @Override
    public int getItemCount() {
        return mSongsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTextViewTitle, mTextViewArtist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mTextViewTitle = itemView.findViewById(R.id.textViewTitle);
            mTextViewArtist = itemView.findViewById(R.id.textViewArtist);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    mOnItemClickListener.onItemClick(position, mSongsList);
                }
            }
        }
    }
}
