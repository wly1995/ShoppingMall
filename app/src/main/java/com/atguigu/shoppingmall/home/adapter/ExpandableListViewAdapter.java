package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 万里洋 on 2017/3/7.
 */

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {
    private final Context mContext;
    private final ArrayList<String> group;
    private final ArrayList<List<String>> child;
    private int childP;
    private int groupP;

    public ExpandableListViewAdapter(Context mContext, ArrayList<String> group,
                                     ArrayList<List<String>> child) {
        this.mContext = mContext;
        this.group = group;
        this.child = child;
    }

    /**
     * 得到多少组
     * @return
     */
    @Override
    public int getGroupCount() {
        return group.size();
    }

    /**
     * 得到每一个孩子里面元素的个数
     * @param groupPosition
     * @return
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    /**
     * 根据位置获取某一组
     * @param groupPosition
     * @return
     */
    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    /**
     * 根据组的位置，和每一个位置来获取某一个孩子中某一个元素
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    /**
     * 获取组的id
     * @param groupPosition
     * @return
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取孩子的id
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.group_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(group.get(groupPosition));//根据位置取数据（全部，上衣，下装，外套）
        //  holder.textView.setTextSize(20);
        holder.textView.setPadding(0, 10, 0, 10);//设置间距
        if (isExpanded) {//是否可以展开，如果可以展开那么箭头的图片发生变化
            holder.imageView.setImageResource(R.drawable.filter_list_selected);
        } else {
            holder.imageView.setImageResource(R.drawable.filter_list_unselected);
        }
        return convertView;
    }

    /**
     * 获取孩子的视图及其数据绑定
     * @param groupPosition
     * @param childPosition
     * @param isLastChild
     * @param convertView
     * @param parent
     * @return
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.child_list_item, null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.textView);
            holder.childImageView = (ImageView) convertView.findViewById(R.id.childImageView);
            holder.ll_child_root = (LinearLayout) convertView.findViewById(R.id.ll_child_root);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (groupPosition != 0) {//第一个里面没有数据
            holder.textView.setText(child.get(groupPosition).get(childPosition));
        }
//        isChildSelectable(childP,groupP);
        if (childP == childPosition && groupP == groupPosition) {
            holder.childImageView.setVisibility(View.VISIBLE);//如果位置正确，则对号显示
            notifyDataSetChanged();
        } else {
            holder.childImageView.setVisibility(View.GONE);
            notifyDataSetChanged();
        }
        return convertView;
    }

    /**
     * 获取孩子被选中时的组的位置和孩子的位置
     * @param groupPosition
     * @param childPosition
     * @return
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        childP = childPosition;
        groupP = groupPosition;
        return true;
    }
    class ViewHolder {
        TextView textView;
        ImageView imageView;
        ImageView childImageView;
        LinearLayout ll_child_root;
    }
}
