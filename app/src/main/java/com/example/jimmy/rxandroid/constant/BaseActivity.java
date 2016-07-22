package com.example.jimmy.rxandroid.constant;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.jimmy.rxandroid.R;

import butterknife.ButterKnife;

/**
 * Created by Jimmy on 16/7/7.
 */
public class BaseActivity extends AppCompatActivity {

    protected Application mApplication;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setView();
        ButterKnife.bind(this);
        mApplication = this.getApplication();
    }

    protected void setView() {
        setContentView(R.layout.activity_base);
        for (Class c = this.getClass(); c != Context.class; c = c.getSuperclass()) {
            ContentView annotation = (ContentView) c.getAnnotation(ContentView.class);
            if (annotation != null) {
                View content = null;

                try {
                    content = LayoutInflater.from(this).inflate(annotation.value(), null);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
                LinearLayout ll = (LinearLayout) findViewById(R.id.ll_base);
                ll.addView(content, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return;
            }
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
