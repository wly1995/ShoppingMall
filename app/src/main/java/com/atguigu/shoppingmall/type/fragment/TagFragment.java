package com.atguigu.shoppingmall.type.fragment;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.type.adapter.TagGridViewAdapter;
import com.atguigu.shoppingmall.type.bean.TagBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import okhttp3.Call;

/**
 * Created by 万里洋 on 2017/3/3.
 */

public class TagFragment extends BaseFragment {

    @InjectView(R.id.id_flowlayout)
    TagFlowLayout idFlowlayout;
    private TagGridViewAdapter adapter;
    private List<TagBean.ResultBean> result;
    private ArrayList<String> names = new ArrayList<>();
    private int[] colors = {
            Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"),
            Color.parseColor("#4ba5e2"), Color.parseColor("#f0839a"), Color.parseColor("#f0a420"),
            Color.parseColor("#f0839a"), Color.parseColor("#f0a420"), Color.parseColor("#4ba5e2")
    };
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
//        //设置点击事件
//        gvTag.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
//            }
//        });
        idFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener()
        {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent)
            {
                Toast.makeText(getActivity(), names.get(position), Toast.LENGTH_SHORT).show();
                return true;
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
//                            adapter = new TagGridViewAdapter(mContext, result);
                        }
                        break;
                    case 101:
                        break;
                }
            }
        });
    }

    private void processData(String response) {
        TagBean tagBean = JSON.parseObject(response, TagBean.class);
        result = tagBean.getResult();
        for (int i=0;i<result.size();i++){
            String name = result.get(i).getName();
            names.add(name);

        }
        idFlowlayout.setAdapter(new TagAdapter<String>(names)
        {
            @Override
            public View getView(FlowLayout parent, int position, String s)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.tv, idFlowlayout, false);
//                tv.setBackgroundResource();
                tv.setText(s);
                tv.setTextColor(colors[position % colors.length]);
                return tv;
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

}
