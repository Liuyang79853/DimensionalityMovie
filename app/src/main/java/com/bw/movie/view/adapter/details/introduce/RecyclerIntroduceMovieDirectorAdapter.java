package com.bw.movie.view.adapter.details.introduce;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class RecyclerIntroduceMovieDirectorAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MovieDetailsBean.ResultBean.MovieDirectorBean> datas = new ArrayList<>();

    public RecyclerIntroduceMovieDirectorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_introduce_director, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_introduce_name.setText(datas.get(i).getName());
        Glide.with(context).load(datas.get(i).getPhoto())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(myViewHolder.iv_introduce_photo);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<MovieDetailsBean.ResultBean.MovieDirectorBean> movieDirector) {
        if (movieDirector.size()>0 && movieDirector!=null){
            datas.addAll(movieDirector);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_introduce_name;
        private final ImageView iv_introduce_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_introduce_name = itemView.findViewById(R.id.tv_introduce_name);
            iv_introduce_photo = itemView.findViewById(R.id.iv_introduce_photo);
        }
    }
}
