package com.bw.movie.view.adapter.cinema;

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
import com.bw.movie.model.bean.cinema.RecommendedCinemaBean;

import java.util.ArrayList;
import java.util.List;


public class RecyclerRecommendedCinemaAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<RecommendedCinemaBean.ResultBean> datas = new ArrayList<>();

    public RecyclerRecommendedCinemaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recommended, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_address.setText(datas.get(i).getAddress());
        myViewHolder.tv_name.setText(datas.get(i).getName());
        Glide.with(context).load(datas.get(i).getLogo())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(myViewHolder.iv_logo);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(i,datas.get(i).getId());
            }
        });
    }

    private OnItemClickListener mOnItemClickListener;

    public void onItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position,int cinemaId);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<RecommendedCinemaBean.ResultBean> recommendedCinemaResult) {
        if (recommendedCinemaResult.size()>0 && recommendedCinemaResult!=null){
            datas.addAll(recommendedCinemaResult);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView iv_logo;
        private final TextView tv_address;
        private final TextView tv_name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_logo = itemView.findViewById(R.id.iv_logo);
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }
}
