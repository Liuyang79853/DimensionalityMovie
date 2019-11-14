package com.bw.movie.model.api;


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

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;


public interface IApi {

    //登录
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.LOGIN_URL)
    Observable<MovieLoginBean> movieLogin(@Field("email") String email,
                                          @Field("pwd") String pwd);

    //微信登录
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.WXLOGIN_URL)
    Observable<WXLoginBean> movieWXLogin(@Field("code") String code);

    //发送验证码
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.SENDCODE_URL)
    Observable<SendCodeBean> sendCode(@Field("email") String email);

    //注册
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.REGISTER_URL)
    Observable<MovieRegisterBean> movieRegister(@Field("nickName") String nickName,
                                                @Field("pwd") String pwd,
                                                @Field("email") String email,
                                                @Field("code") String code);

    //首页展示Banner
    @GET(com.bw.movie.model.baseurl.CantantUrl.MOVIEBANNER_URL)
    Observable<MovieFragBannerBean> movieBanner();

    //查询区域列表
    @GET(com.bw.movie.model.baseurl.CantantUrl.RegionList_URL)
    Observable<RegionListBean> regionList();

    //首页展示正在热映
    @GET(com.bw.movie.model.baseurl.CantantUrl.ISHITMOVIE_URL)
    Observable<IsHitMovieBean> isHitMovie(
            @Query("page") int page,
            @Query("count") int count);

    //首页展示即将上映
    @GET(com.bw.movie.model.baseurl.CantantUrl.UPCOMING_URL)
    Observable<UpComingBean> upComingMovie(@Query("page") int page,
                                           @Query("count") int count);

    //根据区域查询影院
    @GET(com.bw.movie.model.baseurl.CantantUrl.CinemaByRegion_URL)
    Observable<CinemaByRegionBean> cinemaByRegion(@Query("regionId") int regionId);

    //首页展示热门电影
    @GET(com.bw.movie.model.baseurl.CantantUrl.HOT_URL)
    Observable<HotMovieBean> hotMovie(@Query("page") int page,
                                      @Query("count") int count);

    //根据电影ID和影院ID查询电影排期列表
    @GET(com.bw.movie.model.baseurl.CantantUrl.MovieSchedule_URL)
    Observable<MovieScheduleBean> movieSchedule(@Query("movieId") int movieId,
                                                @Query("cinemaId") int cinemaId);

    //根据影厅id 查询座位信息
    @GET(com.bw.movie.model.baseurl.CantantUrl.SeatInfo_URL)
    Observable<SeatInfoBean> seatInfo(@Query("hallId") int hallId);

    //电影详情页
    @GET(com.bw.movie.model.baseurl.CantantUrl.MOVIEDETAILS_URL)
    Observable<MovieDetailsBean> movieDetails(@Query("movieId") int movieId);

    //查询影片评论回复
    @GET(com.bw.movie.model.baseurl.CantantUrl.MovieComment_URL)
    Observable<MovieCommentBean> movieComment(@Query("movieId") int movieId,
                                              @Query("page") int page,
                                              @Query("count") int count);

    //根据关键字查询电影信息
    @GET(com.bw.movie.model.baseurl.CantantUrl.MovieByKeyword_URL)
    Observable<MovieByKeyWordBean> movieByKeyword(@Query("keyword") String keyword,
                                                  @Query("page") int page,
                                                  @Query("count") int count);

    //添加用户对影片的评论
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.AddMovieComment_URL)
    Observable<AddMovieCommentBean> addMovieComment(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,
                                                    @Field("movieId") int movieId,
                                                    @Field("commentContent") String commentContent,
                                                    @Field("score") double score);

    //购票下单
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.buyMovieTickets_URL)
    Observable<MovieTicketsBean> buyMovieTickets(@Header("userId") int userId,
                                                 @Header("sessionId") String sessionId,
                                                 @Field("scheduleId") int scheduleId,
                                                 @Field("seat") String seat,
                                                 @Field("sign") String sign);

    //支付
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.Pay_URL)
    Observable<PayBean> pay(@Header("userId") int userId,
                            @Header("sessionId") String sessionId,
                            @Field("payType") int payType,
                            @Field("orderId") String orderId);

    //查询推荐影院信息
    @GET(com.bw.movie.model.baseurl.CantantUrl.RecommendCinema_URL)
    Observable<RecommendedCinemaBean> recommendedCinema(@Query("page") int page,
                                                        @Query("count") int count);

    //查询附近影院
    @GET(com.bw.movie.model.baseurl.CantantUrl.NearbyCinema_URL)
    Observable<NearCinemaBean> nearCinemaBean(@Query("page") int page,
                                              @Query("count") int count);

    //预约
    @FormUrlEncoded
    @POST(com.bw.movie.model.baseurl.CantantUrl.Reserve_URL)
    Observable<ReserveBean> reserve(@Header("userId") int userId,
                                    @Header("sessionId") String sessionId,
                                    @Field("movieId") int movieId);

    //查询影院用户评论列表
    @GET(com.bw.movie.model.baseurl.CantantUrl.findAllCinemaComment_URL)
    Observable<FindAllCinemaCommentBean> findAllCinemaComment(@Header("userId") int userId,
                                                              @Header("sessionId") String sessionId,
                                                              @Query("cinemaId") int cinemaId,
                                                              @Query("page") int page,
                                                              @Query("count") int count);

    //查询系统消息列表
    @GET(com.bw.movie.model.baseurl.CantantUrl.SysMsg_URL)
    Observable<SysMsgBean> sysMsg(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId,
                                  @Query("page") int page,
                                  @Query("count") int count);

    //查询用户关注电影列表
    @GET(com.bw.movie.model.baseurl.CantantUrl.UserFollowMovie_URL)
    Observable<UserFollowMovieBean> userFollowMovie(@Header("userId") int userId,
                                                    @Header("sessionId") String sessionId,
                                                    @Query("page") int page,
                                                    @Query("count") int count);

    //查询新版本
    @GET(com.bw.movie.model.baseurl.CantantUrl.Update_URL)
    Observable<UpdateBean> update(@Header("userId") int userId,
                                  @Header("sessionId") String sessionId,
                                  @Header("ak") String ak);

    //查询电影信息明细
    @GET(com.bw.movie.model.baseurl.CantantUrl.CinemaInfo_URL)
    Observable<CinemaDetailsBean> cinemaInfo(@Header("userId") int userId,
                                             @Header("sessionId") String sessionId,
                                             @Query("cinemaId") int cinemaId);
}
