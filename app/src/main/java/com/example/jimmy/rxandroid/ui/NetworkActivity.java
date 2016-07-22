package com.example.jimmy.rxandroid.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.jimmy.rxandroid.R;
import com.example.jimmy.rxandroid.adapter.UserListAdapter;
import com.example.jimmy.rxandroid.constant.BaseActivity;
import com.example.jimmy.rxandroid.constant.ContentView;
import com.example.jimmy.rxandroid.util.NetworkWrapper;

import butterknife.Bind;

/**
 * Rx的网络请求方式
 * <p>
 * Created by wangchenlong on 15/12/31.
 */
@ContentView(R.layout.activity_network)
public class NetworkActivity extends BaseActivity {

    @Bind(R.id.network_rv_list)
    RecyclerView mRvList; // 列表

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 设置Layout管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvList.setLayoutManager(layoutManager);

        // 设置适配器
        UserListAdapter adapter = new UserListAdapter(this::gotoDetailPage);
        NetworkWrapper.getUsersInto(adapter);
        mRvList.setAdapter(adapter);
    }

    // 点击的回调
    public interface UserClickCallback {
        void onItemClicked(String name);
    }

    // 跳转到库详情页面
    private void gotoDetailPage(String name) {
        startActivity(NetworkDetailActivity.from(NetworkActivity.this, name));
    }
}
