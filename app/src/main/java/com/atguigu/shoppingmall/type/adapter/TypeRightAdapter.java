package com.atguigu.shoppingmall.type.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.activity.GoodsInfoActivity;
import com.atguigu.shoppingmall.home.adapter.HomeAdapter;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.type.bean.TypeBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.atguigu.shoppingmall.utils.DensityUtil;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by 万里洋 on 2017/3/3.
 */

public class TypeRightAdapter extends RecyclerView.Adapter {
    private final Context mContext;
    private final List<TypeBean.ResultBean> result;
    /**
     * 热卖推荐
     */
    private static final int HOT = 0;
    /**
     * 常用分类
     */
    private static final int COMMON = 1;
    private final List<TypeBean.ResultBean.ChildBean> child;
    private final List<TypeBean.ResultBean.HotProductListBean> hot_product_list;



    /**
     * 当前类型
     */
    private int currentType = HOT;

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        this.result = result;
        child = result.get(0).getChild();
        hot_product_list = result.get(0).getHot_product_list();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HOT) {
            return new HotViewHolder(View.inflate(mContext, R.layout.item_hot_right, null));
        } else if (viewType == COMMON) {
            return new CommomViewHolder(View.inflate(mContext, R.layout.item_common_right, null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == HOT) {
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            //设置数据
            hotViewHolder.setData(hot_product_list);
        } else if (getItemViewType(position) == COMMON){
            CommomViewHolder commomViewHolder = (CommomViewHolder) holder;
            //要减1
            int realPosition   = position -1;
            commomViewHolder.setData(child.get(realPosition),realPosition);
        }
    }

    @Override
    public int getItemCount() {
        return 1 + child.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == HOT) {
            currentType = HOT;
        } else if (position == COMMON) {
            currentType = COMMON;
        }
        return currentType;
    }

    class HotViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.ll_hot_right)
        LinearLayout llHotRight;
        @InjectView(R.id.hsl_hot_right)
        HorizontalScrollView hslHotRight;

        public HotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }

        public void setData(final List<TypeBean.ResultBean.HotProductListBean> hot_product_list) {
            for (int i = 0; i < hot_product_list.size(); i++) {
                //遍历取出数据
                TypeBean.ResultBean.HotProductListBean listBean = hot_product_list.get(i);
                //设置每一个的线性布局
                LinearLayout linearLayout = new LinearLayout(mContext);
                //设置参数
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
                params.setMargins(DensityUtil.dip2px(mContext, 5), 0, DensityUtil.dip2px(mContext, 5), DensityUtil.dip2px(mContext, 20));

                linearLayout.setOrientation(LinearLayout.VERTICAL);
                linearLayout.setGravity(Gravity.CENTER);

                //记录每一个里层的LinearLayout的位置
                linearLayout.setTag(i);

                //图片
                ImageView imageView = new ImageView(mContext);
                //至于为什么要设置线性布局的参数是因为它要添加到线性布局中去
                LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(DensityUtil.dip2px(mContext, 80), DensityUtil.dip2px(mContext, 80));
                ivParams.setMargins(0, 0, 0, DensityUtil.dip2px(mContext, 10));
                Glide.with(mContext)
                        .load(Constants.BASE_URL_IMAGE + listBean.getFigure())
                        .placeholder(R.drawable.afy)
                        .into(imageView);
                linearLayout.addView(imageView, ivParams);

                //文字
                LinearLayout.LayoutParams tvParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
//                textView.setTextColor(Color.RED);
                textView.setTextColor(Color.parseColor("#ed3f3f"));
                textView.setText("￥" + listBean.getCover_price());

                linearLayout.addView(textView, tvParams);
//
//最外层的线性布局
                llHotRight.addView(linearLayout, params);

                //设置点击事件
                linearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //取出位置可以确定到底是哪一个Linearlayout
                        int position = (int) v.getTag();
//                        Toast.makeText(mContext, "position==" + position, Toast.LENGTH_SHORT).show();
                        String cover_price = hot_product_list.get(position).getCover_price();
                        String name = hot_product_list.get(position).getName();
                        String figure = hot_product_list.get(position).getFigure();
                        String product_id = hot_product_list.get(position).getProduct_id();
                        GoodsBean goodsBean = new GoodsBean();
                        goodsBean.setProduct_id(product_id);
                        goodsBean.setFigure(figure);
                        goodsBean.setCover_price(cover_price);
                        goodsBean.setName(name);

                        Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                        intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

    class CommomViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.iv_ordinary_right)
        ImageView ivOrdinaryRight;
        @InjectView(R.id.tv_ordinary_right)
        TextView tvOrdinaryRight;
        @InjectView(R.id.ll_root)
        LinearLayout llRoot;
        public CommomViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);
        }

        public void setData(TypeBean.ResultBean.ChildBean child, final int realPosition) {
            //设置图片
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + child.getPic()).placeholder(R.drawable.new_img_loading_2)
                    .error(R.drawable.afy).into(ivOrdinaryRight);

            //设置名称
            tvOrdinaryRight.setText(child.getName());

            //设置点击事件
            llRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "realPostion=="+realPosition, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
