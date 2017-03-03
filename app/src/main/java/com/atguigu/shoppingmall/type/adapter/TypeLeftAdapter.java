package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 万里洋 on 2017/3/3.
 */

public class TypeLeftAdapter extends BaseAdapter {
    private final Context mContext;
    private final String[] titles;
    private int position;

    public TypeLeftAdapter(Context mContext, String[] titles) {
        this.mContext = mContext;
        this.titles = titles;
    }

    @Override
    public int getCount() {
        return titles.length;
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
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_type, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle.setText(titles[position]);


        if (this.position == position){
            convertView.setBackgroundResource(R.drawable.type_item_background_selector);
            //选中项背景
            viewHolder.tvTitle.setTextColor(Color.parseColor("#fd3f3f"));
        }else{
            //设置默认
            convertView.setBackgroundResource(R.drawable.bg2);  //其他项背景
            viewHolder.tvTitle.setTextColor(Color.parseColor("#323437"));
        }
        return convertView;
    }

    public void selectChange(int position) {
        this.position = position;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
