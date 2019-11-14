package com.bw.movie.applications;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class App extends Application {
    public static IWXAPI mWxApi;
    public static String APP_ID = "wxb3852e6a6b7d9516";

    @Override
    public void onCreate() {
        super.onCreate();
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, APP_ID, true);
        mWxApi.registerApp(APP_ID);
    }
}
