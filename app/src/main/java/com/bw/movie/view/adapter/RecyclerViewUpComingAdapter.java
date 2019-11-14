package com.bw.movie.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.homepage.UpComingBean;
import com.bw.movie.view.dateutils.DateUtils;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * function:即将上映RecyclerView列表展示
 */
public class RecyclerViewUpComingAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<UpComingBean.ResultBean> datas = new ArrayList<>();

    public RecyclerViewUpComingAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_upcoming_recyclerview, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_name.setText(datas.get(i).getName());
        String times = DateUtils.times(datas.get(i).getReleaseTime());
        myViewHolder.tv_releaseTime.setText(times+"上映");
        Glide.with(context).load(datas.get(i).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(myViewHolder.iv_imageUrl);
        myViewHolder.tv_wantSeeNum.setText(datas.get(i).getWantSeeNum()+"人想看");

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(i);
            }
        });

        myViewHolder.btn_appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.appointmentClick(datas.get(i).getMovieId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<UpComingBean.ResultBean> upComingResult) {
        if (upComingResult.size()>0 && upComingResult != null){
            datas.addAll(upComingResult);
        }
        notifyDataSetChanged();
    }

    private OnItemClickListener mOnItemClickListener;

    public void onItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
        void appointmentClick(int movieId);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_name;
        private final TextView tv_releaseTime;
        private final TextView tv_wantSeeNum;
        private final ImageView iv_imageUrl;
        private final Button btn_appointment;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_releaseTime = itemView.findViewById(R.id.tv_releaseTime);
            tv_wantSeeNum = itemView.findViewById(R.id.tv_wantSeeNum);
            iv_imageUrl = itemView.findViewById(R.id.iv_imageUrl);
            btn_appointment = itemView.findViewById(R.id.btn_appointment);
        }
    }
}
