package com.bw.movie.view.fragment.attention;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.model.bean.mine.UserFollowMovieBean;
import com.bw.movie.presenter.mine.UserFollowMoviePresenter;
import com.bw.movie.view.adapter.attention.RecyclerMovieAttentionAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * function:我关注的电影
 */
public class MyAttentionMovieFragment extends Fragment implements IContractView.IUserFollowMovieView {
    @BindView(R.id.recycler_movie_attention)
    RecyclerView recyclerMovieAttention;
    Unbinder unbinder;
    private String sessionId;
    private int userId;
    private int page = 1;
    private int count = 10;
    private UserFollowMoviePresenter userFollowMoviePresenter;
    private RecyclerMovieAttentionAdapter recyclerMovieAttentionAdapter;
    private List<UserFollowMovieBean.ResultBean> userFollowMovieResult;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_movie_attention, null);
        unbinder = ButterKnife.bind(this, inflate);

        Intent intent = getActivity().getIntent();
        userId = intent.getIntExtra("userId", 0);
        sessionId = intent.getStringExtra("sessionId");
        userFollowMoviePresenter = new UserFollowMoviePresenter();
        userFollowMoviePresenter.attachView(this);
        userFollowMoviePresenter.userFollowMovie(userId,sessionId,page,count);
        return inflate;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        userFollowMoviePresenter.detachView();
    }

    @Override
    public void userFollowMovieSuccess(UserFollowMovieBean userFollowMovieBean) {

        Log.e("MyMessageUserFollow",userFollowMovieBean.getResult().get(0).getName());

        if (userFollowMovieBean.getMessage().equals("查询成功")){
            userFollowMovieResult = userFollowMovieBean.getResult();
            SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            recyclerMovieAttentionAdapter = new RecyclerMovieAttentionAdapter(getContext());
            recyclerMovieAttentionAdapter.addData(userFollowMovieResult);
            recyclerMovieAttention.setLayoutManager(syLinearLayoutManager);
            recyclerMovieAttention.setAdapter(recyclerMovieAttentionAdapter);
        }else {
            Toast.makeText(getContext(),userFollowMovieBean.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
