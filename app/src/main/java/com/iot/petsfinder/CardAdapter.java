package com.iot.petsfinder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;


public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>
{

    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;

    //List to store all superheroes
    List<Doggy> doggies;

    //Constructor of this class
    public CardAdapter(List<Doggy> doggies, Context context){
        super();
        //Getting all superheroes
        this.doggies = doggies;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_array, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        Doggy doggy =  doggies.get(position);

        //Loading image from url
//        imageLoader.get(doggy.getImageUrl(),
//                ImageLoader.getImageListener(holder.imageView,
//                        R.drawable.image,
//                        android.R.drawable.ic_dialog_alert));

        //Showing data on the views
//        holder.imageView.setImageUrl(doggy.getImageUrl(), imageLoader);
        holder.textViewType.setText(doggy.getType());
        holder.textViewGender.setText(doggy.getGender());
        holder.textViewAge.setText(doggy.getAge());

    }

    @Override
    public int getItemCount() {
        return doggies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewType;
        public TextView textViewGender;
        public TextView textViewAge;

        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
//            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewType = (TextView) itemView.findViewById(R.id.txtCardVDogType);
            textViewGender = (TextView) itemView.findViewById(R.id.txtCardVDogType);
            textViewAge = (TextView) itemView.findViewById(R.id.txtCardVDogType);
        }
    }


//
//
//    private ArrayList<Doggy> _doggies;
//
//    private Context context;
//    private ArrayList<Doggy> items;
//    private int lastPosition = -1;
//
//    public CardAdapter(ArrayList<Doggy> doggies){
//        _doggies = doggies;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
//    {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_view, parent, false);
//
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder holder, int position)
//    {
//        Doggy doggy = _doggies.get(position);
//        holder.albumImage.setBackgroundResource(doggy.getImageId());
//        holder.albumType.setText(doggy.getType());
//        holder.albumAge.setText(doggy.getAge());
//        holder.albumGender.setText(doggy.getGender());
//
//        setAnimation(holder.albumImage, position);
//    }
//
//    @Override
//    public int getItemCount()
//    {
//        return _doggies.size();
//    }
//
//    class ViewHolder extends RecyclerView.ViewHolder{
//        public ViewHolder(View itemView){
//            super(itemView);
//
//            albumImage = (ImageView) itemView.findViewById(R.id.img);
//            albumType = (TextView) itemView.findViewById(R.id.type);
//            albumAge = (TextView) itemView.findViewById(R.id.age);
//            albumGender = (TextView) itemView.findViewById(R.id.gender);
//        }
//        ImageView albumImage;
//        TextView albumType;
//        TextView albumAge;
//        TextView albumGender;
//    }
//    private void setAnimation(View viewToAnimate, int position) {
//        if (position > lastPosition) {
//            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//            viewToAnimate.setAnimation(animation);
//            lastPosition = position;
//        }
//    }
}
