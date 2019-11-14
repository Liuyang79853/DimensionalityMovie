package com.bw.movie.view.adapter.attention;

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
import com.bw.movie.model.bean.mine.UserFollowMovieBean;

import java.util.ArrayList;
import java.util.List;

/**

 * function:
 */
public class RecyclerMovieAttentionAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<UserFollowMovieBean.ResultBean> datas = new ArrayList<>();

    public RecyclerMovieAttentionAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_movie_attention, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_name.setText(datas.get(i).getName());
        myViewHolder.tv_director.setText("导演："+datas.get(i).getDirector());
        myViewHolder.tv_starring.setText("演员："+datas.get(i).getStarring());
        myViewHolder.tv_score.setText("评分："+datas.get(i).getScore());
        Glide.with(context).load(datas.get(i).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(myViewHolder.iv_imageUrl);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<UserFollowMovieBean.ResultBean> userFollowMovieResult) {
        if (userFollowMovieResult.size()>0 && userFollowMovieResult != null){
            datas.addAll(userFollowMovieResult);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_name;
        private final TextView tv_score;
        private final TextView tv_starring;
        private final TextView tv_director;
        private final ImageView iv_imageUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_score = itemView.findViewById(R.id.tv_score);
            tv_starring = itemView.findViewById(R.id.tv_starring);
            tv_director = itemView.findViewById(R.id.tv_director);
            iv_imageUrl = itemView.findViewById(R.id.iv_imageUrl);
        }
    }
}
