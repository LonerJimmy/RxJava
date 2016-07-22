package com.example.jimmy.rxandroid.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.example.jimmy.rxandroid.R;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


public class SafeActivity extends RxAppCompatActivity {

    @Bind(R.id.simple_tv_text) TextView mTvText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);
        ButterKnife.bind(this);

        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle()) // 管理生命周期, 防止内存泄露
                .subscribe(t -> showTime(t));
    }

    private void showTime(Long time) {
        mTvText.setText(String.valueOf("时间计数: " + time));
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
