package com.bw.movie.model.okhttp;

import android.util.Log;

import com.bw.movie.model.api.IApi;
import com.bw.movie.model.baseurl.CantantUrl;
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

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**

 * function:
 */
public class OkHttpUtils<B> {

    private final OkHttpClient mOkHttpClient;
    private final Retrofit mRetrofit;
    private final IApi mIApi;
    private static OkHttpUtils mOkHttpUtils = null;

    private OkHttpUtils() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        mRetrofit = new Retrofit.Builder()
                .client(mOkHttpClient)
                .baseUrl(CantantUrl.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        mIApi = mRetrofit.create(IApi.class);
    }

    public static OkHttpUtils getInstance() {
        if (mOkHttpUtils == null) {
            return new OkHttpUtils();
        }
        return mOkHttpUtils;
    }

    //登录
    public void movieLogin(final IOkCallBack iOkCallBack, Class<B> bean, String email, String pwd) {
        final Observable<MovieLoginBean> movieLogin = mIApi.movieLogin(email, pwd);
        movieLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieLoginBean>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieLoginBean movieLoginBean) {
                        iOkCallBack.callSuccess(movieLoginBean);
                    }
                });
    }

    //微信登录
    public void movieWXLogin(final IOkCallBack iOkCallBack, Class<B> bean, String code) {
        Observable<WXLoginBean> movieWXLogin = mIApi.movieWXLogin(code);
        movieWXLogin.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WXLoginBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(WXLoginBean wxLoginBean) {
                        iOkCallBack.callSuccess(wxLoginBean);
                    }
                });
    }

    //注册
    public void movieRegister(final IOkCallBack iOkCallBack, Class<B> bean, String nickName, String pwd, String email, String code) {
        Observable<MovieRegisterBean> movieRegister = mIApi.movieRegister(nickName, pwd, email, code);
        movieRegister.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieRegisterBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieRegisterBean movieRegisterBean) {
                        iOkCallBack.callSuccess(movieRegisterBean);
                    }
                });
    }

    //预约
    public void reserve(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, int movieId) {
        Log.e("MyMessageMoviehahha", movieId + "");
        Observable<ReserveBean> reserve = mIApi.reserve(userId, sessionId, movieId);
        reserve.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ReserveBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ReserveBean reserveBean) {
                        iOkCallBack.callSuccess(reserveBean);
                    }
                });
    }

    //发送验证码
    public void sendCode(final IOkCallBack iOkCallBack, Class<B> bean, String email) {
        Observable<SendCodeBean> sendCode = mIApi.sendCode(email);
        sendCode.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendCodeBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SendCodeBean sendCodeBean) {
                        iOkCallBack.callSuccess(sendCodeBean);
                    }
                });
    }

    //首页展示banner
    public void movieBanner(final IOkCallBack iOkCallBack, Class<B> bean) {
        Observable<MovieFragBannerBean> movieBanner = mIApi.movieBanner();
        movieBanner.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieFragBannerBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieFragBannerBean movieFragBannerBean) {
                        iOkCallBack.callSuccess(movieFragBannerBean);
                    }
                });
    }

    //查询区域列表
    public void regionList(final IOkCallBack iOkCallBack, Class<B> bean) {
        Observable<RegionListBean> regionList = mIApi.regionList();
        regionList.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RegionListBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RegionListBean regionListBean) {
                        iOkCallBack.callSuccess(regionListBean);
                    }
                });
    }

    //根据区域查询影院
    public void cinemaByRegion(final IOkCallBack iOkCallBack, Class<B> bean, int regionId) {
        Observable<CinemaByRegionBean> cinemaByRegion = mIApi.cinemaByRegion(regionId);
        cinemaByRegion.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CinemaByRegionBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CinemaByRegionBean cinemaByRegionBean) {
                        iOkCallBack.callSuccess(cinemaByRegionBean);
                    }
                });
    }

    //首页展示正在热映
    public void isHitMovie(final IOkCallBack iOkCallBack, Class<B> bean, int page, int count) {
        Observable<IsHitMovieBean> hitMovie = mIApi.isHitMovie(page, count);
        hitMovie.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<IsHitMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(IsHitMovieBean isHitMovieBean) {
                        iOkCallBack.callSuccess(isHitMovieBean);
                    }
                });
    }

    //首页展示即将上映
    public void upComingMovie(final IOkCallBack iOkCallBack, Class<B> bean, int page, int count) {
        Observable<UpComingBean> upComingMovie = mIApi.upComingMovie(page, count);
        upComingMovie.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpComingBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpComingBean upComingBean) {
                        iOkCallBack.callSuccess(upComingBean);
                    }
                });
    }

    //首页展示热门电影
    public void hotMovie(final IOkCallBack iOkCallBack, Class<B> bean, int page, int count) {
        Observable<HotMovieBean> hotMovie = mIApi.hotMovie(page, count);
        hotMovie.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HotMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(HotMovieBean hotMovieBean) {
                        iOkCallBack.callSuccess(hotMovieBean);
                    }
                });
    }

    //根据电影ID和影院ID查询电影排期列表
    public void movieSchedule(final IOkCallBack iOkCallBack, Class<B> bean, int movieId, int cinemaId) {
        Observable<MovieScheduleBean> movieSchedule = mIApi.movieSchedule(movieId, cinemaId);
        movieSchedule.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieScheduleBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieScheduleBean movieScheduleBean) {
                        iOkCallBack.callSuccess(movieScheduleBean);
                    }
                });
    }

    //根据影厅id 查询座位信息
    public void seatInfo(final IOkCallBack iOkCallBack, Class<B> bean, int hallId) {
        Observable<SeatInfoBean> seatInfo = mIApi.seatInfo(hallId);
        seatInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SeatInfoBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SeatInfoBean seatInfoBean) {
                        iOkCallBack.callSuccess(seatInfoBean);
                    }
                });
    }

    //电影详情
    public void movieDetails(final IOkCallBack iOkCallBack, Class<B> bean, int movieId) {
        Observable<MovieDetailsBean> movieDetails = mIApi.movieDetails(movieId);
        movieDetails.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieDetailsBean movieDetailsBean) {
                        iOkCallBack.callSuccess(movieDetailsBean);
                    }
                });
    }

    //查询电影信息明细
    public void cinemaInfo(final IOkCallBack iOkCallBack, Class<B> bean, int userId,String sessionId,int cinemaId) {
        Observable<CinemaDetailsBean> cinemaInfo = mIApi.cinemaInfo(userId, sessionId, cinemaId);
        cinemaInfo.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CinemaDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(CinemaDetailsBean cinemaDetailsBean) {
                        iOkCallBack.callSuccess(cinemaDetailsBean);
                    }
                });
    }

    //查询影片评论回复
    public void movieComment(final IOkCallBack iOkCallBack, Class<B> bean, int movieId, int page, int count) {
        Observable<MovieCommentBean> movieComment = mIApi.movieComment(movieId, page, count);
        movieComment.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieCommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieCommentBean movieCommentBean) {
                        iOkCallBack.callSuccess(movieCommentBean);
                    }
                });
    }

    //根据关键字查询电影信息
    public void movieByKeyword(final IOkCallBack iOkCallBack, Class<B> bean, String keyword, int page, int count) {
        Observable<MovieByKeyWordBean> movieByKeyword = mIApi.movieByKeyword(keyword, page, count);
        movieByKeyword.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieByKeyWordBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieByKeyWordBean movieByKeyWordBean) {
                        iOkCallBack.callSuccess(movieByKeyWordBean);
                    }
                });
    }

    //添加用户对影片的评论
    public void addMovieComment(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, int movieId, String commentContent, double score) {
        Observable<AddMovieCommentBean> addMovieComment = mIApi.addMovieComment(userId, sessionId, movieId, commentContent, score);
        addMovieComment.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddMovieCommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddMovieCommentBean addMovieCommentBean) {
                        iOkCallBack.callSuccess(addMovieCommentBean);
                    }
                });
    }

    //购票下单
    public void buyMovieTickets(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, int scheduleId, String seat, String sign) {
        Observable<MovieTicketsBean> buyMovieTickets = mIApi.buyMovieTickets(userId, sessionId, scheduleId, seat, sign);
        buyMovieTickets.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieTicketsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(MovieTicketsBean movieTicketsBean) {
                        iOkCallBack.callSuccess(movieTicketsBean);
                    }
                });
    }

    //支付
    public void pay(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, int payType, String orderId) {
        Observable<PayBean> pay = mIApi.pay(userId, sessionId, payType, orderId);
        pay.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PayBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(PayBean payBean) {
                        iOkCallBack.callSuccess(payBean);
                    }
                });
    }

    //查询推荐影院信息
    public void recommendedCinema(final IOkCallBack iOkCallBack, Class<B> bean, int page, int count) {
        Observable<RecommendedCinemaBean> recommendedCinema = mIApi.recommendedCinema(page, count);
        recommendedCinema.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RecommendedCinemaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RecommendedCinemaBean recommendedCinemaBean) {
                        iOkCallBack.callSuccess(recommendedCinemaBean);
                    }
                });
    }

    //查询附近影院
    public void nearCinema(final IOkCallBack iOkCallBack, Class<B> bean, int page, int count) {
        Observable<NearCinemaBean> nearCinemaBean = mIApi.nearCinemaBean(page, count);
        nearCinemaBean.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NearCinemaBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(NearCinemaBean nearCinemaBean) {
                        iOkCallBack.callSuccess(nearCinemaBean);
                    }
                });
    }

    //查询系统消息列表
    public void sysMsg(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, int page, int count) {
        Observable<SysMsgBean> sysMsg = mIApi.sysMsg(userId, sessionId, page, count);
        sysMsg.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SysMsgBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(SysMsgBean sysMsgBean) {
                        iOkCallBack.callSuccess(sysMsgBean);
                    }
                });
    }

    //查询影院用户评论列表
    public void findAllCinemaComment(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId,int cinemaId, int page, int count) {
        Observable<FindAllCinemaCommentBean> allCinemaComment = mIApi.findAllCinemaComment(userId, sessionId, cinemaId, page, count);
        allCinemaComment.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FindAllCinemaCommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(FindAllCinemaCommentBean findAllCinemaCommentBean) {
                        iOkCallBack.callSuccess(findAllCinemaCommentBean);
                    }
                });
    }

    //查询用户关注电影列表
    public void userFollowMovie(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, int page, int count) {
        Observable<UserFollowMovieBean> userFollowMovie = mIApi.userFollowMovie(userId, sessionId, page, count);
        userFollowMovie.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserFollowMovieBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UserFollowMovieBean userFollowMovieBean) {
                        iOkCallBack.callSuccess(userFollowMovieBean);
                    }
                });
    }

    //查询新版本
    public void update(final IOkCallBack iOkCallBack, Class<B> bean, int userId, String sessionId, String ak) {
        final Observable<UpdateBean> update = mIApi.update(userId, sessionId, ak);
        update.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(UpdateBean updateBean) {
                        iOkCallBack.callSuccess(updateBean);
                    }
                });
    }

    public interface IOkCallBack<B> {
        void callSuccess(B bean);

        void callError(String msg);
    }
}
