package com.bw.movie.view.fragment.cinema;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.model.bean.cinema.FindAllCinemaCommentBean;
import com.bw.movie.model.bean.user.UserBean;
import com.bw.movie.presenter.cinema.AllCinemaCommentPresenter;
import com.bw.movie.view.adapter.cinema.RecyclerAllCinemaCommentAdapter;
import com.bw.movie.view.contract.IContractView;
import com.bw.movie.view.customview.SyLinearLayoutManager;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**

 * function:影院评价
 */
public class CinemaEvaluationFragment extends Fragment implements IContractView.IAllCinemaCommentView {
    @BindView(R.id.recycler_AllCinemaComment)
    RecyclerView recyclerAllCinemaComment;
    Unbinder unbinder;
    private AllCinemaCommentPresenter allCinemaCommentPresenter;
    //用户登录的信息
    private String sessionId;
    private int userId;
    //设置条目与页数
    private int page = 1;
    private int count = 10;
    private List<FindAllCinemaCommentBean.ResultBean> result;
    private RecyclerAllCinemaCommentAdapter recyclerAllCinemaCommentAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_cinema_evaluation, null);
        unbinder = ButterKnife.bind(this, inflate);
        Intent intent = getActivity().getIntent();
        int cinemaId = intent.getIntExtra("cinemaId", 0);
        allCinemaCommentPresenter = new AllCinemaCommentPresenter();
        allCinemaCommentPresenter.attachView(this);
        allCinemaCommentPresenter.allCinemaComment(userId,sessionId,cinemaId,page,count);
        return inflate;
    }

    @Subscribe(threadMode = ThreadMode.ASYNC,sticky = true)
    public void Message(UserBean userBean){
        userId = userBean.getUserId();
        sessionId = userBean.getSessionId();
    }

    @Override
    public void allCinemaCommentSuccess(FindAllCinemaCommentBean findAllCinemaCommentBean) {
        result = findAllCinemaCommentBean.getResult();
        SyLinearLayoutManager syLinearLayoutManager = new SyLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        //查询影院评论适配器
        recyclerAllCinemaCommentAdapter = new RecyclerAllCinemaCommentAdapter(getContext());
        recyclerAllCinemaCommentAdapter.addData(result);
        recyclerAllCinemaComment.setLayoutManager(syLinearLayoutManager);
        recyclerAllCinemaComment.setAdapter(recyclerAllCinemaCommentAdapter);
    }

    @Override
    public void allCinemaCommentError(String msg) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        allCinemaCommentPresenter.detachView();
    }
}
