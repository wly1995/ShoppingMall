package com.atguigu.shoppingmall.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.bean.TypeListBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.atguigu.shoppingmall.R.id.iv_hot;
import static com.atguigu.shoppingmall.R.id.tv_name;
import static com.atguigu.shoppingmall.R.id.tv_price;

/**
 * Created by 万里洋 on 2017/3/6.
 */

public class GoodsListAdapter extends RecyclerView.Adapter<GoodsListAdapter.MyViewHolder> {
    private final Context mContext;
    private final TypeListBean datas;
    private final List<TypeListBean.ResultBean.PageDataBean> page_data;


    public GoodsListAdapter(Context mContext, TypeListBean typeListBean) {
        this.mContext = mContext;
        this.datas = typeListBean;
        page_data = datas.getResult().getPage_data();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(mContext, R.layout.item_goods_list, null));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {//每一个item的位置
        holder.setData(page_data.get(position));//根据位置获取数据
    }

    @Override
    public int getItemCount() {
        return page_data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TypeListBean.ResultBean.PageDataBean data;
        @InjectView(iv_hot)
        ImageView ivHot;
        @InjectView(tv_name)
        TextView tvName;
        @InjectView(tv_price)
        TextView tvPrice;
        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this,itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.setOnItemClickListener(data);
                    }
                }
            });
        }

        public void setData(TypeListBean.ResultBean.PageDataBean pageDataBean) {
            this.data = pageDataBean;
            Glide.with(mContext).load(Constants.BASE_URL_IMAGE + data.getFigure()).into(ivHot);
            tvName.setText(data.getName());
            tvPrice.setText("￥" + data.getCover_price());

        }
    }

    private OnItemClickListener onItemClickListener;

    /**
     * 设置item的点击事件的监听
     */
    public interface OnItemClickListener {
        void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data);
    }

    /**
     * 设置监听
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
