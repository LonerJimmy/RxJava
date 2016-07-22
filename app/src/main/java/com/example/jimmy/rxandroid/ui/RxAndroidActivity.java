package com.example.jimmy.rxandroid.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimmy.rxandroid.R;
import com.example.jimmy.rxandroid.constant.BaseActivity;
import com.example.jimmy.rxandroid.constant.ContentView;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxTextView;

import butterknife.Bind;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Jimmy on 16/7/18.
 */
@ContentView(R.layout.activity_android)
public class RxAndroidActivity extends BaseActivity {

    @Bind(R.id.txt_rx_android)
    protected TextView textRxAndroid;
    @Bind(R.id.progress_bar)
    protected ProgressBar progressBar;
    @Bind(R.id.btn_start)
    protected Button btnStart;
    @Bind(R.id.btn_binding)
    protected Button btnBinding;
    @Bind(R.id.edit_binding)
    protected EditText editBinding;
    @Bind(R.id.txt_binding)
    protected TextView txtBinding;

    private Subscription subscription;

    private String[] fromString = {"I", "am", "Loner"};

    public class SomeType {
        private String value;

        public void setValue(String value) {
            this.value = value;
        }
//
        public Observable<String> valueObservable() {
            return Observable.just(value);
        }

//        public Observable<String> valueObservable() {
//            return Observable.create(new Observable.OnSubscribe<String>() {
//                @Override
//                public void call(Subscriber<? super String> subscriber) {
//                    subscriber.onNext(value);
//                    subscriber.onCompleted();
//                }
//            });
//        }
    }

    Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext(longRunningOperation());
            subscriber.onCompleted();
        }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


    private String longRunningOperation() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            Log.e("DEBUG", e.toString());
        }

        return "running!";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.from(fromString)
                .map(s -> s.toUpperCase())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> Toast.makeText(RxAndroidActivity.this, s, Toast.LENGTH_SHORT).show());
//
        SomeType instance = new SomeType();
        Observable<String> value = instance.valueObservable();
        instance.setValue("create some Value");
        value.subscribe(s -> textRxAndroid.setText(s));

        //异步处理
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                subscription = observable.subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                        textRxAndroid.setText("error");
                    }

                    @Override
                    public void onNext(String s) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(RxAndroidActivity.this, s, Toast.LENGTH_SHORT).show();
                        textRxAndroid.setText(s);
                    }
                });
            }
        });

        RxView.clicks(btnBinding).subscribe(view -> btnClick(view));

        RxTextView.textChanges(editBinding).subscribe(view -> textChange(view));
    }

    private void btnClick(Void v) {
        textRxAndroid.setText("click success");
    }

    private void textChange(CharSequence v) {
        txtBinding.setText("edit text changed is " + v.toString());
    }

    @Override
    protected void onDestroy() {
        if (subscription != null) {
            this.subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
