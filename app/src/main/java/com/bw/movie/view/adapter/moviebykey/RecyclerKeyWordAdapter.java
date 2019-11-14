package com.bw.movie.view.adapter.moviebykey;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.model.bean.moviebykey.MovieByKeyWordBean;

import java.util.ArrayList;
import java.util.List;


public class RecyclerKeyWordAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MovieByKeyWordBean.ResultBean> datas = new ArrayList<>();

    public RecyclerKeyWordAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recycler_movie_more, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_more_name.setText(datas.get(i).getName());
        myViewHolder.tv_more_director.setText("导演："+datas.get(i).getDirector());
        myViewHolder.tv_more_score.setText("评分："+datas.get(i).getScore());
        myViewHolder.tv_more_starring.setText("主演："+datas.get(i).getStarring());
        Glide.with(context).load(datas.get(i).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(myViewHolder.iv_more_imageUrl);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<MovieByKeyWordBean.ResultBean> keyWordBeanResult) {
        if (keyWordBeanResult.size()>0&&keyWordBeanResult!=null){
            datas.addAll(keyWordBeanResult);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_more_name;
        private final TextView tv_more_director;
        private final TextView tv_more_score;
        private final TextView tv_more_starring;
        private final ImageView iv_more_imageUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_more_name = itemView.findViewById(R.id.tv_more_name);
            tv_more_director = itemView.findViewById(R.id.tv_more_director);
            tv_more_score = itemView.findViewById(R.id.tv_more_score);
            tv_more_starring = itemView.findViewById(R.id.tv_more_starring);
            iv_more_imageUrl = itemView.findViewById(R.id.iv_more_imageUrl);
        }
    }
}
