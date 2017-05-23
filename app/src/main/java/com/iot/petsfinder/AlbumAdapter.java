package com.iot.petsfinder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder>
{
    private ArrayList<Album> _albums;

    public AlbumAdapter(ArrayList<Album> albums){
        _albums=albums;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Album album = _albums.get(position);
        holder.albumImage.setBackgroundResource(album.getImageId());
        holder.albumType.setText(album.getType());
        holder.albumAge.setText(album.getAge());
        holder.albumGender.setText(album.getGender());
    }

    @Override
    public int getItemCount()
    {
        return _albums.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView){
            super(itemView);

            albumImage = (ImageView) itemView.findViewById(R.id.img);
            albumType = (TextView) itemView.findViewById(R.id.type);
            albumAge = (TextView) itemView.findViewById(R.id.age);
            albumGender = (TextView) itemView.findViewById(R.id.gender);
        }
        ImageView albumImage;
        TextView albumType;
        TextView albumAge;
        TextView albumGender;
    }
}