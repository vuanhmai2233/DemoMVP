package com.example.maianhvu.demomusicapp.screen.view.listMusic;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.maianhvu.demomusicapp.R;
import com.example.maianhvu.demomusicapp.data.model.Songs;
import java.util.ArrayList;
import java.util.List;

public class SAdapter extends RecyclerView.Adapter<SAdapter.ViewHolder> {
    private ArrayList<Songs> mSongsList;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position,List<Songs> songsList);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.mListener = listener;
    }

    public SAdapter(Context context) {
        mContext= context;
        mSongsList = new ArrayList<>();

    }

    public void updateList(List<Songs> songsList) {
        mSongsList.addAll(songsList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        mLayoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = mLayoutInflater.inflate(R.layout.item_listview, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(mContext)
                .load(mSongsList.get(i).getImage())
                .into(viewHolder.mImageView);
        viewHolder.mTextViewTitle.setText(mSongsList.get(i).getNameSong());
        viewHolder.mTextViewArtist.setText(mSongsList.get(i).getNameArtist());

    }

    @Override
    public int getItemCount() {
        return mSongsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView mImageView;
        TextView mTextViewTitle, mTextViewArtist;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextViewArtist = itemView.findViewById(R.id.textViewArtist);
            mTextViewTitle = itemView.findViewById(R.id.textViewTitle);
            mImageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

            if(mListener !=null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    mListener.onItemClick(position,mSongsList);
                }
            }
        }
    }

}
