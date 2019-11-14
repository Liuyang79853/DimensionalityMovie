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
import com.bw.movie.model.bean.homepage.IsHitMovieBean;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * function:正在上映RecyclerView列表展示
 */
public class RecyclerViewIsHitAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<IsHitMovieBean.ResultBean> datas = new ArrayList<>();

    public RecyclerViewIsHitAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_ishit_recyclerview, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_name.setText(datas.get(i).getName());
        Glide.with(context).load(datas.get(i).getImageUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(myViewHolder.iv_imageUrl);
        myViewHolder.tv_score.setText(datas.get(i).getScore()+"分");

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

    public void addData(List<IsHitMovieBean.ResultBean> isHitResult) {
        if (isHitResult.size() > 0 && isHitResult != null) {
            datas.addAll(isHitResult);
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

        private final TextView tv_name;
        private final ImageView iv_imageUrl;
        private final Button btn_buy_ticket;
        private final TextView tv_score;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            iv_imageUrl = itemView.findViewById(R.id.iv_imageUrl);
            btn_buy_ticket = itemView.findViewById(R.id.btn_buy_ticket);
            tv_score = itemView.findViewById(R.id.tv_score);
        }
    }
}
