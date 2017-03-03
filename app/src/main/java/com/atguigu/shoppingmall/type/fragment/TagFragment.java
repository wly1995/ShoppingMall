package com.atguigu.shoppingmall.type.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.type.adapter.TagGridViewAdapter;
import com.atguigu.shoppingmall.type.bean.TagBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 万里洋 on 2017/3/3.
 */

public class TagFragment extends BaseFragment {

    @InjectView(R.id.gv_tag)
    GridView gvTag;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_tag, null);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        //网络请求数据
        getDataFromNet();
        //设置点击事件
        gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "position=="+ position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDataFromNet() {
        OkHttpUtils.get().url(Constants.TAG_URL).id(100).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
                switch (id) {
                    case 100:
//                    Toast.makeText(mContext, "http", Toast.LENGTH_SHORT).show();
                        if (response != null) {
                            processData(response);
                            adapter = new TagGridViewAdapter(mContext, result);
                            gvTag.setAdapter(adapter);
                        }
                        break;
                    case 101:
                        break;
                }
            }
        });
    }

    private void processData(String response) {
        TagBean tagBean = JSON.parseObject(response,TagBean.class);
        result = tagBean.getResult();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
