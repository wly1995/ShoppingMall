package com.atguigu.shoppingmall.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.atguigu.shoppingmall.MainActivity;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.home.adapter.ExpandableListViewAdapter;
import com.atguigu.shoppingmall.home.adapter.GoodsListAdapter;
import com.atguigu.shoppingmall.home.adapter.HomeAdapter;
import com.atguigu.shoppingmall.home.bean.GoodsBean;
import com.atguigu.shoppingmall.home.bean.TypeListBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Request;

/**
 * 作者：尚硅谷-杨光福 on 2016/12/6 11:14
 * 微信：yangguangfu520
 * QQ号：541433511
 * 作用：频道列表
 */
public class GoodsListActivity extends AppCompatActivity {

    @InjectView(R.id.ib_goods_list_back)
    ImageButton ibGoodsListBack;
    @InjectView(R.id.tv_goods_list_search)
    TextView tvGoodsListSearch;
    @InjectView(R.id.ib_goods_list_home)
    ImageButton ibGoodsListHome;
    @InjectView(R.id.tv_goods_list_sort)
    TextView tvGoodsListSort;
    @InjectView(R.id.tv_goods_list_price)
    TextView tvGoodsListPrice;
    @InjectView(R.id.iv_goods_list_arrow)
    ImageView ivGoodsListArrow;
    @InjectView(R.id.ll_goods_list_price)
    LinearLayout llGoodsListPrice;
    @InjectView(R.id.tv_goods_list_select)
    TextView tvGoodsListSelect;
    @InjectView(R.id.ll_goods_list_head)
    LinearLayout llGoodsListHead;
    @InjectView(R.id.recyclerview)
    RecyclerView recyclerview;
    @InjectView(R.id.dl_left)
    DrawerLayout dlLeft;
    @InjectView(R.id.ib_drawer_layout_back)
    ImageButton ibDrawerLayoutBack;
    @InjectView(R.id.tv_ib_drawer_layout_title)
    TextView tvIbDrawerLayoutTitle;
    @InjectView(R.id.ib_drawer_layout_confirm)
    TextView ibDrawerLayoutConfirm;
    @InjectView(R.id.rb_select_hot)
    RadioButton rbSelectHot;
    @InjectView(R.id.rb_select_new)
    RadioButton rbSelectNew;
    @InjectView(R.id.rg_select)
    RadioGroup rgSelect;
    @InjectView(R.id.tv_drawer_price)
    TextView tvDrawerPrice;
    @InjectView(R.id.tv_drawer_recommend)
    TextView tvDrawerRecommend;
    @InjectView(R.id.rl_select_recommend_theme)
    RelativeLayout rlSelectRecommendTheme;
    @InjectView(R.id.tv_drawer_type)
    TextView tvDrawerType;
    @InjectView(R.id.rl_select_type)
    RelativeLayout rlSelectType;
    @InjectView(R.id.btn_select_all)
    Button btnSelectAll;
    @InjectView(R.id.ll_select_root)
    LinearLayout llSelectRoot;
    @InjectView(R.id.btn_drawer_layout_cancel)
    Button btnDrawerLayoutCancel;
    @InjectView(R.id.btn_drawer_layout_confirm)
    Button btnDrawerLayoutConfirm;
    @InjectView(R.id.iv_price_no_limit)
    ImageView ivPriceNoLimit;
    @InjectView(R.id.rl_price_nolimit)
    RelativeLayout rlPriceNolimit;
    @InjectView(R.id.iv_price_0_15)
    ImageView ivPrice015;
    @InjectView(R.id.rl_price_0_15)
    RelativeLayout rlPrice015;
    @InjectView(R.id.iv_price_15_30)
    ImageView ivPrice1530;
    @InjectView(R.id.rl_price_15_30)
    RelativeLayout rlPrice1530;
    @InjectView(R.id.iv_price_30_50)
    ImageView ivPrice3050;
    @InjectView(R.id.rl_price_30_50)
    RelativeLayout rlPrice3050;
    @InjectView(R.id.iv_price_50_70)
    ImageView ivPrice5070;
    @InjectView(R.id.rl_price_50_70)
    RelativeLayout rlPrice5070;
    @InjectView(R.id.iv_price_70_100)
    ImageView ivPrice70100;
    @InjectView(R.id.rl_price_70_100)
    RelativeLayout rlPrice70100;
    @InjectView(R.id.iv_price_100)
    ImageView ivPrice100;
    @InjectView(R.id.rl_price_100)
    RelativeLayout rlPrice100;
    @InjectView(R.id.et_price_start)
    EditText etPriceStart;
    @InjectView(R.id.v_price_line)
    View vPriceLine;
    @InjectView(R.id.et_price_end)
    EditText etPriceEnd;
    @InjectView(R.id.rl_select_price)
    RelativeLayout rlSelectPrice;
    @InjectView(R.id.ll_price_root)
    LinearLayout llPriceRoot;
    @InjectView(R.id.btn_drawer_theme_cancel)
    Button btnDrawerThemeCancel;
    @InjectView(R.id.btn_drawer_theme_confirm)
    Button btnDrawerThemeConfirm;
    @InjectView(R.id.iv_theme_all)
    ImageView ivThemeAll;
    @InjectView(R.id.rl_theme_all)
    RelativeLayout rlThemeAll;
    @InjectView(R.id.iv_theme_note)
    ImageView ivThemeNote;
    @InjectView(R.id.rl_theme_note)
    RelativeLayout rlThemeNote;
    @InjectView(R.id.iv_theme_funko)
    ImageView ivThemeFunko;
    @InjectView(R.id.rl_theme_funko)
    RelativeLayout rlThemeFunko;
    @InjectView(R.id.iv_theme_gsc)
    ImageView ivThemeGsc;
    @InjectView(R.id.rl_theme_gsc)
    RelativeLayout rlThemeGsc;
    @InjectView(R.id.iv_theme_origin)
    ImageView ivThemeOrigin;
    @InjectView(R.id.rl_theme_origin)
    RelativeLayout rlThemeOrigin;
    @InjectView(R.id.iv_theme_sword)
    ImageView ivThemeSword;
    @InjectView(R.id.rl_theme_sword)
    RelativeLayout rlThemeSword;
    @InjectView(R.id.iv_theme_food)
    ImageView ivThemeFood;
    @InjectView(R.id.rl_theme_food)
    RelativeLayout rlThemeFood;
    @InjectView(R.id.iv_theme_moon)
    ImageView ivThemeMoon;
    @InjectView(R.id.rl_theme_moon)
    RelativeLayout rlThemeMoon;
    @InjectView(R.id.iv_theme_quanzhi)
    ImageView ivThemeQuanzhi;
    @InjectView(R.id.rl_theme_quanzhi)
    RelativeLayout rlThemeQuanzhi;
    @InjectView(R.id.iv_theme_gress)
    ImageView ivThemeGress;
    @InjectView(R.id.rl_theme_gress)
    RelativeLayout rlThemeGress;
    @InjectView(R.id.ll_theme_root)
    LinearLayout llThemeRoot;
    @InjectView(R.id.btn_drawer_type_cancel)
    Button btnDrawerTypeCancel;
    @InjectView(R.id.btn_drawer_type_confirm)
    Button btnDrawerTypeConfirm;
    @InjectView(R.id.expandableListView)
    ExpandableListView expandableListView;
    @InjectView(R.id.ll_type_root)
    LinearLayout llTypeRoot;
    private int position;

    /**
     * 请求网络
     */
    private String[] urls = new String[]{
            Constants.CLOSE_STORE,
            Constants.GAME_STORE,
            Constants.COMIC_STORE,
            Constants.COSPLAY_STORE,
            Constants.GUFENG_STORE,
            Constants.STICK_STORE,
            Constants.WENJU_STORE,
            Constants.FOOD_STORE,
            Constants.SHOUSHI_STORE,
    };
    private TypeListBean typeListBean;
    private GoodsListAdapter adapter;
    private int click_count;
    private ArrayList<String> group;
    private ArrayList<List<String>> child;
    private ExpandableListViewAdapter expandableListViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        ButterKnife.inject(this);
        getData();
        initData();
        initView();
    }

    private void initView() {
        //设置综合排序高亮显示
        tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
        //价格设置默认
        tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
        //筛选设置默认
        tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

        showSelectorLayout();//默认显示筛选页面
    }

    private void initData() {
        getDataFromNet(urls[position]);
    }

    private void getDataFromNet(String url) {
        OkHttpUtils
                .get()
                .url(url)//根据位置获取不同的联网地址
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {


        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
//                    Toast.makeText(GoodsListActivity.this, "http", Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        processData(response);

                    }
                    break;
                case 101:
                    Toast.makeText(GoodsListActivity.this, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }

    }

    private void processData(String response) {
        //解析数据
        typeListBean = JSON.parseObject(response, TypeListBean.class);
//        Toast.makeText(this, ""+typeListBean.getResult().getPage_data().get(0).getName(), Toast.LENGTH_SHORT).show();

        GridLayoutManager manager = new GridLayoutManager(GoodsListActivity.this, 2);
        recyclerview.setLayoutManager(manager);
        adapter = new GoodsListAdapter(GoodsListActivity.this, typeListBean);
        recyclerview.addItemDecoration(new SpaceItemDecoration(10));
        recyclerview.setAdapter(adapter);

        //设置点击事件
        //设置item的点击事件,用到接口的回调
        adapter.setOnItemClickListener(new GoodsListAdapter.OnItemClickListener() {
            @Override
            public void setOnItemClickListener(TypeListBean.ResultBean.PageDataBean data) {
                String name = data.getName();
                String cover_price = data.getCover_price();
                String figure = data.getFigure();
                String product_id = data.getProduct_id();

                GoodsBean goodsBean = new GoodsBean();
                goodsBean.setName(name);
                goodsBean.setFigure(figure);
                goodsBean.setCover_price(cover_price);
                goodsBean.setProduct_id(product_id);

                Intent intent = new Intent(GoodsListActivity.this, GoodsInfoActivity.class);
                intent.putExtra(HomeAdapter.GOODS_BEAN, goodsBean);
                startActivity(intent);
            }
        });

    }


    /**
     * 设置分界线
     */
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int space;

        public SpaceItemDecoration(int space) {
            this.space = space;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            //不是第一个的格子都设一个左边和底部的间距
            outRect.left = space;
            outRect.bottom = space;
            //由于每行都只有3个，所以第一个都是3的倍数，把左边距设为0
            if (parent.getChildLayoutPosition(view) % 2 == 0) {
                outRect.left = 0;
            }
        }
    }

    private void getData() {
        Intent intent = getIntent();
        position = intent.getIntExtra("position", -1);
    }

    @OnClick({R.id.btn_drawer_type_cancel,R.id. btn_drawer_theme_confirm,R.id. btn_drawer_theme_cancel,
            R.id. btn_drawer_layout_cancel,R.id. rl_select_price,R.id. rl_select_recommend_theme,R.id. rl_select_type,
            R.id.ib_drawer_layout_back,R.id.ib_goods_list_back, R.id.tv_goods_list_search, R.id.ib_goods_list_home,
            R.id.tv_goods_list_sort, R.id.tv_goods_list_price, R.id.tv_goods_list_select,R.id.ib_drawer_layout_confirm,
            R.id.btn_drawer_layout_confirm,R.id.btn_drawer_type_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_goods_list_back:
                finish();
                break;
            case R.id.tv_goods_list_search:
                Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ib_goods_list_home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("checkId", R.id.rb_home);
                startActivity(intent);
//                Toast.makeText(this, "主页", Toast.LENGTH_SHORT).show();
                break;
            case R.id.tv_goods_list_sort:
//                Toast.makeText(this, "综合搜索", Toast.LENGTH_SHORT).show();
                //综合排序点击事件
                click_count = 0;
                //设置价格箭头向下
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);

                //价格文字变成默认黑色
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));

                //设置默认
                tvGoodsListSort.setTextColor(Color.parseColor("#ed4141"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));

                break;
            case R.id.tv_goods_list_price:
//                Toast.makeText(this, "价格搜索", Toast.LENGTH_SHORT).show();
                //价格点击事件

                //综合排序变为默认

                //价格红
                tvGoodsListPrice.setTextColor(Color.parseColor("#ed4141"));
                //设置默认
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSelect.setTextColor(Color.parseColor("#333538"));
                click_count++;
                if (click_count % 2 == 1) {
                    // 箭头向下红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_desc);
                } else {
                    // 箭头向上红
                    ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_asc);
                }
                break;
            case R.id.tv_goods_list_select:
//                Toast.makeText(this, "筛选搜索", Toast.LENGTH_SHORT).show();
                click_count = 0;
//                Toast.makeText(this, "筛选", Toast.LENGTH_SHORT).show();
                ivGoodsListArrow.setBackgroundResource(R.drawable.new_price_sort_normal);
                //筛选的点击事件
                tvGoodsListSelect.setTextColor(Color.parseColor("#ed4141"));

                //设置默认
                tvGoodsListPrice.setTextColor(Color.parseColor("#333538"));
                tvGoodsListSort.setTextColor(Color.parseColor("#333538"));
                //显示筛选菜单
                dlLeft.openDrawer(Gravity.RIGHT);
                break;
            case R.id.ib_drawer_layout_back://点击返回
                //关闭筛选菜单
                dlLeft.closeDrawers();
                break;

            case R.id.ib_drawer_layout_confirm:
                Toast.makeText(this, "筛选-确定", Toast.LENGTH_SHORT).show();
                break;
            case R.id. rl_select_price://价格
                //价格筛选的页面
                llPriceRoot.setVisibility(View.VISIBLE);

                showPriceLayout();
                break;
            case R.id. rl_select_recommend_theme://推荐主题
                llThemeRoot.setVisibility(View.VISIBLE);

                showThemeLayout();
                break;
            case R.id. rl_select_type://类别
                llTypeRoot.setVisibility(View.VISIBLE);

                showTypeLayout();
                break;

            case R.id. btn_drawer_layout_cancel:
//                Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_layout_confirm:
                Toast.makeText(this, "价格-确定", Toast.LENGTH_SHORT).show();
                break;
            case R.id. btn_drawer_type_cancel:
//                Toast.makeText(GoodsListActivity.this, "取消", Toast.LENGTH_SHORT).show();
                llSelectRoot.setVisibility(View.VISIBLE);
//                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case  R.id. btn_drawer_theme_confirm:
                Toast.makeText(GoodsListActivity.this, "主题-确认", Toast.LENGTH_SHORT).show();
                break;
            case R.id. btn_drawer_theme_cancel:
                llSelectRoot.setVisibility(View.VISIBLE);
//                ibDrawerLayoutBack.setVisibility(View.VISIBLE);
                showSelectorLayout();
                break;
            case R.id.btn_drawer_type_confirm:
                Toast.makeText(this, "类别-确定", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    //筛选页面
    private void showSelectorLayout() {
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }


    //价格页面 (相应的其他三个页面要隐藏，因为他们是同时放在相对布局中，一层层的覆盖)
    private void showPriceLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //主题页面
    private void showThemeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llTypeRoot.setVisibility(View.GONE);
    }

    //类别页面
    private void showTypeLayout() {
        llSelectRoot.setVisibility(View.GONE);
        llPriceRoot.setVisibility(View.GONE);
        llThemeRoot.setVisibility(View.GONE);

        //初始化ExpandableListView
        initExpandableListView();
    }

    private void initExpandableListView() {
//创建集合
        group = new ArrayList<>();//放的是每个组的名字
        child = new ArrayList<>();//child是一个集合里面的每一个元素又是一个集合，
        // 所以最外面的集合是一个一个的组，里面的每一个集合就代表没一个组里面的东西

        //添加数据
        addInfo("全部", new String[]{});
        addInfo("上衣", new String[]{"古风", "和风", "lolita", "日常"});
        addInfo("下装", new String[]{"日常", "泳衣", "汉风", "lolita", "创意T恤"});
        addInfo("外套", new String[]{"汉风", "古风", "lolita", "胖次", "南瓜裤", "日常"});


        //设置适配器
        expandableListViewAdapter = new ExpandableListViewAdapter(this, group, child);
        expandableListView.setAdapter(expandableListViewAdapter);

        // 这里是控制如果列表没有孩子菜单不展开的效果
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent,
                                        View v, int groupPosition, long id) {
                if (child.get(groupPosition).isEmpty()) {// isEmpty没有，这个组的数据为空
                    return true;
                } else {
                    return false;
                }
            }
        });

        //设置点击孩子
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosiion, long id) {
//                Toast.makeText(GoodsListActivity.this,"_"+group.get(groupPosition)+"_"+child.get(groupPosition).
//                        get(childPosiion)+"被点击了",Toast.LENGTH_SHORT).show();
                expandableListViewAdapter.isChildSelectable(groupPosition,childPosiion);//把点击的组位置和孩子位置传过去

                //进行刷新
                expandableListViewAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }
    private void addInfo(String g, String[] c) {
        group.add(g);
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < c.length; i++) {
            list.add(c[i]);
        }
        child.add(list);
    }

}


