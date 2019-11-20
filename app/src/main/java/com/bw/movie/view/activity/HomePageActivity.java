package com.bw.movie.view.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.presenter.home.HomeFragPresenter;
import com.bw.movie.view.activity.base.BaseActivity;
import com.bw.movie.view.adapter.paging.HomePageFragmentAdapter;
import com.bw.movie.view.fragment.CinemaFragment;
import com.bw.movie.view.fragment.MoiveFragment;
import com.bw.movie.view.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomePageActivity extends AppCompatActivity {


    private FrameLayout frame;
    private RadioGroup radio_group;
    private TextView text_dianying;
    private TextView text_yingyuan;
    private TextView text_wode;
    private RadioButton radio_dianying;
    private RadioButton radio_yingyuan;
    private RadioButton radio_wode;
    private MoiveFragment fragmentMovie;

    private FragmentManager supportFragmentManager;
    private CinemaFragment fragmentCinema;
    private MyFragment fragmentMy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        frame = findViewById(R.id.frame);
        text_dianying = findViewById(R.id.text_dianying);
        text_yingyuan = findViewById(R.id.text_yingyuan);
        text_wode = findViewById(R.id.text_wode);
        radio_dianying = findViewById(R.id.radio_dianying);
        radio_yingyuan = findViewById(R.id.radio_yingyuan);
        radio_wode = findViewById(R.id.radio_wode);
        radio_group = findViewById(R.id.radio_group);
        fragmentMovie = new MoiveFragment();
        fragmentCinema = new CinemaFragment();
        fragmentMy = new MyFragment();
        supportFragmentManager = getSupportFragmentManager();
        Bitmap a = null;
        radio_dianying.setButtonDrawable(new BitmapDrawable(a));
        radio_yingyuan.setButtonDrawable(new BitmapDrawable(a));
        radio_wode.setButtonDrawable(new BitmapDrawable(a));
        supportFragmentManager.beginTransaction()
                .add(R.id.frame, fragmentMovie)
                .add(R.id.frame, fragmentCinema)
                .add(R.id.frame, fragmentMy)
                .show(fragmentMovie)
                .hide(fragmentCinema)
                .hide(fragmentMy)
                .commit();
        radio_dianying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_dianying.setVisibility(View.VISIBLE);
                text_yingyuan.setVisibility(View.INVISIBLE);
                text_wode.setVisibility(View.INVISIBLE);
                radio_dianying.setChecked(true);
                radio_yingyuan.setChecked(false);
                radio_wode.setChecked(false);
                supportFragmentManager.beginTransaction()
                        .show(fragmentMovie)
                        .hide(fragmentCinema)
                        .hide(fragmentMy)
                        .commit();
            }
        });
        radio_yingyuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_yingyuan.setVisibility(View.VISIBLE);
                text_dianying.setVisibility(View.INVISIBLE);
                text_wode.setVisibility(View.INVISIBLE);
                radio_yingyuan.setChecked(true);
                radio_dianying.setChecked(false);
                radio_wode.setChecked(false);
                supportFragmentManager.beginTransaction()
                        .show(fragmentCinema)
                        .hide(fragmentMovie)
                        .hide(fragmentMy)
                        .commit();
            }
        });
        radio_wode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text_wode.setVisibility(View.VISIBLE);
                text_yingyuan.setVisibility(View.INVISIBLE);
                text_dianying.setVisibility(View.INVISIBLE);
                radio_wode.setChecked(true);
                radio_yingyuan.setChecked(false);
                radio_dianying.setChecked(false);
                supportFragmentManager.beginTransaction()
                        .show(fragmentMy)
                        .hide(fragmentMovie)
                        .hide(fragmentCinema)
                        .commit();
            }
        });
    }
}
