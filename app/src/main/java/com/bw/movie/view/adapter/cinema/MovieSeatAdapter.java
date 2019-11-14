package com.bw.movie.view.adapter.cinema;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.SeatInfoBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Auther:
 * @Date: 2019/10/2 16:16:24
 * @Description:
 */
public class MovieSeatAdapter extends RecyclerView.Adapter<MovieSeatAdapter.MovieVIewHolder> {

    private Context context;
    private List<SeatInfoBean.ResultBean> result = new ArrayList<>();

    public MovieSeatAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MovieVIewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.movie_seat_layout, viewGroup, false);
        return new MovieVIewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final MovieVIewHolder movieVIewHolder, final int i) {
        String row = result.get(i).getRow();
        String seat1 = result.get(i).getSeat();
        String seat = row + "-" + seat1;

        int status = result.get(i).getStatus();
        if (status == 1) {
            movieVIewHolder.cheBox.setChecked(false);
        } else if (status == 2) {
            movieVIewHolder.cheBox.setChecked(true);
            movieVIewHolder.cheBox.setBackgroundColor(R.drawable.myy_shape);
        }
        final ArrayList<String> list = new ArrayList<>();
        movieVIewHolder.cheBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (movieVIewHolder.cheBox.isChecked()) {
                    result.get(i).setStatus(3);
                    String row1 = result.get(i).getRow();
                    String seat2 = result.get(i).getSeat();
                    String s = row1 + "-" + seat2;
                    Log.e("seat",s);
                    list.addAll(Collections.singleton(s));
                    callBack.getStrng(list);
                    callBack.getBack(result.get(i).getRow() + "-" + result.get(i).getSeat());
                } else {
                    result.get(i).setStatus(1);
                    callBack.getBack("取消选座");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }

    public void addData(List<SeatInfoBean.ResultBean> seatInfoBeanResult) {
        if (seatInfoBeanResult != null && seatInfoBeanResult.size() > 0) {
            result.addAll(seatInfoBeanResult);
        }
        notifyDataSetChanged();
    }

    public class MovieVIewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.che_box)
        CheckBox cheBox;

        public MovieVIewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface CallBack {
        void getBack(String s);

        void getStrng(ArrayList<String> list);
    }

    CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }


}
