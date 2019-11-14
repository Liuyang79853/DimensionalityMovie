package com.bw.movie.view.adapter.details.still;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bw.movie.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecyclerStillMovieActorAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<String> datas = new ArrayList<>();

    public RecyclerStillMovieActorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_still_listurl, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        for (int j = 0; j < datas.size(); j++) {
            Glide.with(context).load(datas.get(i))
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(myViewHolder.iv_still_imageUrl);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<String> listImageUrl) {
        if (listImageUrl.size()>0 && listImageUrl!=null){
            datas.addAll(listImageUrl);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_still_imageUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_still_imageUrl = itemView.findViewById(R.id.iv_still_imageUrl);
        }
    }
}
