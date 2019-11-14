package com.bw.movie.view.adapter.details.trailer;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.model.bean.details.MovieDetailsBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

/**

 * function:预告适配器
 */
public class RecyclerTrailerMovieActorAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<MovieDetailsBean.ResultBean.ShortFilmListBean> datas = new ArrayList<>();

    public RecyclerTrailerMovieActorAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_trailer_video, null);
        MyViewHolder myViewHolder = new MyViewHolder(inflate);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        final MyViewHolder myViewHolder = (MyViewHolder) viewHolder;
        myViewHolder.jcp_trailer_videoUrl.setUp(datas.get(i).getVideoUrl(),"预告"+"("+(i+1)+")");
        Glide.with(context).load(datas.get(i).getImageUrl())
                .into(myViewHolder.jcp_trailer_videoUrl.ivThumb);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void addData(List<MovieDetailsBean.ResultBean.ShortFilmListBean> shortFilmList) {
        if (shortFilmList.size()>0 && shortFilmList!=null){
            datas.addAll(shortFilmList);
        }
        notifyDataSetChanged();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private final JCVideoPlayer jcp_trailer_videoUrl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            jcp_trailer_videoUrl = itemView.findViewById(R.id.jcp_trailer_videoUrl);
        }
    }

}
