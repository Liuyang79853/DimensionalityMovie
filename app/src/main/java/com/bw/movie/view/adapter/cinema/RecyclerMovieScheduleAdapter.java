package com.bw.movie.view.adapter.cinema;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.MovieScheduleBean;

import java.util.ArrayList;
import java.util.List;


public class RecyclerMovieScheduleAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MovieScheduleBean.ResultBean> datas = new ArrayList<>();

    public RecyclerMovieScheduleAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_recycler_movie_schedule, viewGroup, false);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_screeningHall.setText(datas.get(i).getScreeningHall());
        myViewHolder.tv_beginTime_endTime.setText(datas.get(i).getBeginTime()+"---"+datas.get(i).getEndTime());

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<MovieScheduleBean.ResultBean> result) {
        if (result.size() >0 && result != null){
            datas.addAll(result);
        }
        notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;

    public void onItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_screeningHall;
        private final TextView tv_beginTime_endTime;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_screeningHall = itemView.findViewById(R.id.tv_screeningHall);
            tv_beginTime_endTime = itemView.findViewById(R.id.tv_beginTime_endTime);
        }
    }
}
