package com.example.jimmy.rxandroid.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.example.jimmy.rxandroid.R;
import com.example.jimmy.rxandroid.constant.BaseActivity;
import com.example.jimmy.rxandroid.constant.ContentView;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by Jimmy on 16/7/17.
 */

@ContentView(R.layout.activity_simple)
public class JavaSimpleActivity extends BaseActivity {

    @Bind(R.id.txt_base)
    protected TextView baseTextView;
    @Bind(R.id.txt_simple)
    protected TextView simpleTextView;
    @Bind(R.id.txt_lambda)
    protected TextView lambdaTextView;
    @Bind(R.id.txt_just)
    protected TextView justTextView;

    private Observable<String> myObservable = Observable.create(
            new Observable.OnSubscribe<String>() {
                @Override
                public void call(Subscriber<? super String> sub) {
                    sub.onNext("飞哥请吃饭!");
                    sub.onCompleted();
                }
            }
    );

    private Subscriber<String> mySubscriber = new Subscriber<String>() {
        @Override
        public void onNext(String s) {
            baseTextView.setText(s);
        }

        @Override
        public void onCompleted() {
        }

        @Override
        public void onError(Throwable e) {
        }
    };

    //just写法
    private Observable<String> justObserver = Observable.just("HamersEventbus star过5000");
    private Action1<String> onNextAction = new Action1<String>() {
        @Override
        public void call(String s) {
            simpleTextView.setText(s);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //基础写法
        myObservable.subscribe(mySubscriber);
        //just写法
        justObserver.subscribe(onNextAction);

        //just简便写法
        Observable.just("飞哥肏屄")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        justTextView.setText(s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        justTextView.setText(throwable.getMessage());
                    }
                });

        //lambda写法
        Observable.just("Hello, zjm!")
                .subscribe(sb -> setText(sb),
                        e -> setText(e.getMessage()));
    }

    private void setText(String s) {
        lambdaTextView.setText(s);
    }
}
