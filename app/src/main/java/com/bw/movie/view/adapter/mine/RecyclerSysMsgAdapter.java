package com.bw.movie.view.adapter.mine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.model.bean.mine.SysMsgBean;
import com.bw.movie.view.dateutils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**

 * function:
 */
public class RecyclerSysMsgAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<SysMsgBean.ResultBean> datas = new ArrayList<>();

    public RecyclerSysMsgAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_sysmsg_recycler, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.tv_sysmsg_title.setText(datas.get(i).getTitle());
        myViewHolder.tv_sysmsg_content.setText(datas.get(i).getContent());
        String times = DateUtils.times(datas.get(i).getPushTime());
        myViewHolder.tv_sysmsg_pushTime.setText(times);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<SysMsgBean.ResultBean> sysMsgBeanResult) {
        if (sysMsgBeanResult != null && sysMsgBeanResult.size() > 0) {
            datas.addAll(sysMsgBeanResult);
        }
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_sysmsg_pushTime;
        private final TextView tv_sysmsg_title;
        private final TextView tv_sysmsg_content;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_sysmsg_pushTime = itemView.findViewById(R.id.tv_sysmsg_pushTime);
            tv_sysmsg_title = itemView.findViewById(R.id.tv_sysmsg_title);
            tv_sysmsg_content = itemView.findViewById(R.id.tv_sysmsg_content);
        }
    }
}
