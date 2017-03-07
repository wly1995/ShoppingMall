package com.atguigu.shoppingmall.home.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.adapter.HomeAdapter;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

public class GenrateActivity extends AppCompatActivity {

    private GoodsBean goodsBean;
    private ImageView imageView;
    private ImageView iv_logo;
    private String result;
    private Bitmap image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genrate);
        imageView = (ImageView) findViewById(R.id.iv_genrate);
        iv_logo = (ImageView) findViewById(R.id.iv_logo);
        getData();
        setData();
    }

    private void setData() {
        Glide.with(this)
                .load(Constants.BASE_URL_IMAGE+goodsBean.getFigure())
                .asBitmap()
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        Bitmap bitmap = EncodingUtils.createQRCode(Constants.BASE_URL_IMAGE + goodsBean.getFigure(),
                                500, 500, resource);
                        imageView.setImageBitmap(bitmap);
                    }
                });
    }

    private void getData() {
        goodsBean = (GoodsBean) getIntent().getSerializableExtra(HomeAdapter.GOODS_BEAN);
//        result = getIntent().getStringExtra("result");
//        Log.e("TAG",result);
    }
}
