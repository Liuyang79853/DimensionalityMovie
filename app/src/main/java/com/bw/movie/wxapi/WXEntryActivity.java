package com.bw.movie.wxapi;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bw.movie.applications.App;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        App.mWxApi.handleIntent(getIntent(), this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                EventBus.getDefault().postSticky(baseResp);
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Toast.makeText(this, "取消微信登陆", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        finish();
    }
}
