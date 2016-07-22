package com.example.jimmy.rxandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.jimmy.rxandroid.R;
import com.example.jimmy.rxandroid.constant.BaseActivity;
import com.example.jimmy.rxandroid.constant.ContentView;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // 跳转简单的页面
    public void gotoSimpleModule(View view) {
        startActivity(new Intent(this, JavaSimpleActivity.class));
    }

    // 跳转更多的页面
    public void gotoMoreModule(View view) {
        startActivity(new Intent(this, MoreActivity.class));
    }

    //跳转到RxAndroid页面
    public void gotoRxAndroidModule(View view) {
        startActivity(new Intent(this, RxAndroidActivity.class));
    }

    public void gotoSafeActivity(View view){
        startActivity(new Intent(this, SafeActivity.class));
    }
}
