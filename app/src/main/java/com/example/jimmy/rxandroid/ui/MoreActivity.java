package com.example.jimmy.rxandroid.ui;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimmy.rxandroid.R;
import com.example.jimmy.rxandroid.constant.BaseActivity;
import com.example.jimmy.rxandroid.constant.ContentView;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Jimmy on 16/7/17.
 */
@ContentView(R.layout.activity_more)
public class MoreActivity extends BaseActivity {

    @Bind(R.id.txt_hash)
    protected TextView textViewHash;
    @Bind(R.id.txt_reduce_flatmap)
    protected TextView txtReduceFlat;

    private String[] fromString = {"I", "am", "Loner"};
    private List<String> flatArray = Arrays.asList(fromString);

    // 设置映射函数
    private Func1<List<String>, Observable<String>> mOneLetterFunc = new Func1<List<String>, Observable<String>>() {
        @Override
        public Observable<String> call(List<String> strings) {
            return Observable.from(strings); // 映射字符串
        }
    };

    private Func2<String, String, String> mMergeStringFunc = new Func2<String, String, String>() {
        @Override
        public String call(String s, String s2) {
            return String.format("%s %s", s, s2); // 空格连接字符串
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.just("return string")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(s -> "int to string" + Integer.toString(s))
                .subscribe(s -> textViewHash.setText(s));

        Observable.from(fromString)
                .map(s -> s.toUpperCase())
                .subscribe(s -> Toast.makeText(MoreActivity.this, s, Toast.LENGTH_SHORT).show());

        Observable.just(flatArray)
                .flatMap(mOneLetterFunc)
                .reduce(mMergeStringFunc)
                .subscribe(s -> txtReduceFlat.setText(s));
    }
}
