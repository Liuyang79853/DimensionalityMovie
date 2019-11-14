package com.bw.movie.view.adapter.cinema;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.CinemaByRegionBean;

import java.util.ArrayList;
import java.util.List;


public class RecyclerRegionCinemaAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<CinemaByRegionBean.ResultBean> datas = new ArrayList<>();

    public RecyclerRegionCinemaAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_region_cinema, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_region_cinema.setText(datas.get(i).getName());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(i,datas.get(i).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }


    public void addData(List<CinemaByRegionBean.ResultBean> cinemaByRegionList) {
        if (cinemaByRegionList != null && cinemaByRegionList.size() > 0) {
            datas.addAll(cinemaByRegionList);
        }
        notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;

    public void onItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position,int cinemaId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_region_cinema;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_region_cinema = itemView.findViewById(R.id.tv_region_cinema);
        }
    }
}
