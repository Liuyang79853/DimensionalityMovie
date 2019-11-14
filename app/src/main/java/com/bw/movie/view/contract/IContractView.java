package com.bw.movie.view.contract;

import com.bw.movie.model.bean.cinema.CinemaByRegionBean;
import com.bw.movie.model.bean.cinema.CinemaDetailsBean;
import com.bw.movie.model.bean.cinema.FindAllCinemaCommentBean;
import com.bw.movie.model.bean.cinema.MovieScheduleBean;
import com.bw.movie.model.bean.cinema.NearCinemaBean;
import com.bw.movie.model.bean.cinema.PayBean;
import com.bw.movie.model.bean.cinema.RecommendedCinemaBean;
import com.bw.movie.model.bean.cinema.RegionListBean;
import com.bw.movie.model.bean.cinema.SeatInfoBean;
import com.bw.movie.model.bean.details.AddMovieCommentBean;
import com.bw.movie.model.bean.homepage.ReserveBean;
import com.bw.movie.model.bean.mine.SysMsgBean;
import com.bw.movie.model.bean.mine.UpdateBean;
import com.bw.movie.model.bean.mine.UserFollowMovieBean;
import com.bw.movie.model.bean.moviebykey.MovieByKeyWordBean;
import com.bw.movie.model.bean.details.MovieCommentBean;
import com.bw.movie.model.bean.homepage.HotMovieBean;
import com.bw.movie.model.bean.homepage.IsHitMovieBean;
import com.bw.movie.model.bean.details.MovieDetailsBean;
import com.bw.movie.model.bean.homepage.MovieFragBannerBean;
import com.bw.movie.model.bean.moviebykey.MovieTicketsBean;
import com.bw.movie.model.bean.user.MovieLoginBean;
import com.bw.movie.model.bean.user.MovieRegisterBean;
import com.bw.movie.model.bean.user.SendCodeBean;
import com.bw.movie.model.bean.homepage.UpComingBean;
import com.bw.movie.model.bean.user.WXLoginBean;
import com.bw.movie.view.interfaces.IBaseView;
import com.bw.movie.view.interfaces.IPresenter;


public interface IContractView {
    //登录回调接口
    interface ILoginView extends IBaseView{
        void loginSuccess(MovieLoginBean movieLoginBean);
        void loginWXSuccess(WXLoginBean wxLoginBean);
        void loginError(String msg);
    }

    //注册回调接口
    interface IRegisterView extends IBaseView{
        void registerSuccess(MovieRegisterBean movieRegisterBean);
        void sendCode(SendCodeBean sendCodeBean);
        void registerError(String msg);
    }

    //首页展示回调接口
    interface IMovieView extends IBaseView{
        void movieBannerSuccess(MovieFragBannerBean movieFragBannerBean);
        //正在热映
        void IsHitSuccess(IsHitMovieBean isHitMovieBean);

        //正在热映
        void upComingSuccess(UpComingBean upComingBean);

        //热门电影
        void hotSuccess(HotMovieBean hotMovieBean);

        void reserveSuccess(ReserveBean reserveBean);
    }

    //查询区域列表
    interface IRegionListView extends IBaseView{
        void regionSuccess(RegionListBean regionListBean);
        void cinemaByRegionSuccess(CinemaByRegionBean cinemaByRegionBean);
    }

    //查询影院用户评论列表
    interface IAllCinemaCommentView extends IBaseView{
        void allCinemaCommentSuccess(FindAllCinemaCommentBean findAllCinemaCommentBean);
        void allCinemaCommentError(String msg);
    }

    //根据电影ID和影院ID查询电影排期列表
    interface IMovieScheduleView extends IBaseView{
        //查看电影排期
        void movieScheduleSuccess(MovieScheduleBean movieScheduleBean);
        //下单
        void buyMovieTicketsSuccess(MovieTicketsBean movieTicketsBean);
        //选座
        void seatInfoSuccess(SeatInfoBean seatInfoBean);
        //支付
        void paySuccess(PayBean payBean);
        void movieScheduleError(String msg);
    }

    //电影详情回调接口
    interface IMovieDetailsView extends IBaseView{
        void movieDetailsSuccess(MovieDetailsBean movieDetailsBean);
        void movieDetailsError(String msg);
    }

    //根据关键字查询电影信息
    interface IMovieByKeyWordView extends IBaseView{
        void movieByKeyWordSuccess(MovieByKeyWordBean movieByKeyWordBean);
        void movieByKeyWordError(String msg);
    }

    //根据关键字查询电影信息
    interface ICinemaDetailsView extends IBaseView{
        void cinemaDetailsSuccess(CinemaDetailsBean cinemaDetailsBean);
        void cinemaDetailsError(String msg);
    }

    //电影评论
    interface IMovieCommentView extends IBaseView{
        void movieCommentSuccess(MovieCommentBean movieCommentBean);
    }

    //电影评论
    interface ICinemaView extends IBaseView{
        void recommendedCinemaSuccess(RecommendedCinemaBean recommendedCinemaBean);
        void nearCinemaSuccess(NearCinemaBean nearCinemaBean);
    }

    //添加用户对影片的评论
    interface IAddMovieCommentView extends IBaseView{
        //电影评论
        void addMovieCommentSuccess(AddMovieCommentBean addMovieCommentBean);
    }

    //查询系统消息列表
    interface ISysMsgView extends IBaseView{
        void sysMsgSuccess(SysMsgBean sysMsgBean);
    }

    //查询系统消息列表
    interface IUserFollowMovieView extends IBaseView{
        void userFollowMovieSuccess(UserFollowMovieBean userFollowMovieBean);
    }

    //查询新版本
    interface IUpdateView extends IBaseView{
        void updateSuccess(UpdateBean updateBean);
    }

    interface Presenter extends IPresenter {
        void movieLogin(String email,String pwd);
        void movieRegister(String nickName,String pwd,String email,String code);
        void movieWXLogin(String code);
        void sendCode(String email);
    }
}
