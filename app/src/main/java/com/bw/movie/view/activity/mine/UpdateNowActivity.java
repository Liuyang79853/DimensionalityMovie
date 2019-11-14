package com.bw.movie.view.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class UpdateNowActivity extends AppCompatActivity {

    @BindView(R.id.btn_update_now)
    Button btnUpdateNow;
    private Unbinder bind;
    private String downloadUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_now);
        bind = ButterKnife.bind(this);
        Intent intent = getIntent();
        downloadUrl = intent.getStringExtra("downloadUrl");
    }

    @OnClick(R.id.btn_update_now)
    public void onClick() {
        Toast.makeText(this,downloadUrl, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
