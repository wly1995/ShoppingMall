package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import java.util.ArrayList;

/**
 * Created by 万里洋 on 2017/3/8.
 */

public class SearchAdapetr extends BaseAdapter {
    private final Context mContext;
    private final ArrayList<String> datas;

    public SearchAdapetr(Context mContext, ArrayList<String> datas) {
        this.mContext = mContext;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(mContext, R.layout.search_list_item, null);
        String s = datas.get(position);
        TextView tv_item = (TextView) convertView.findViewById(R.id.tv_item);
        tv_item.setText(s);
        return convertView;
    }

}
