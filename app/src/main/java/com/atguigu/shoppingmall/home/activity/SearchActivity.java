package com.atguigu.shoppingmall.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.atguigu.shoppingmall.MyApplication;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.greenDao.GreenBean;
import com.atguigu.shoppingmall.greenDao.GreenBeanDao;
import com.atguigu.shoppingmall.home.adapter.SearchAdapetr;
import com.atguigu.shoppingmall.utils.JsonParser;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import okhttp3.Call;

public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.tv_search_home)
    EditText tvSearchHome;
    @InjectView(R.id.tv_message_home)
    TextView tvMessageHome;
    @InjectView(R.id.lv_search)
    ListView lvSearch;
    @InjectView(R.id.iv_voice)
    ImageView ivVoice;
    @InjectView(R.id.remove_search_record)
    TextView removeSearchRecord;
    private SearchAdapetr adapter;
    private String text;
    private ArrayList<String> list = new ArrayList<>();
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private GreenBean mUser;
    private GreenBeanDao greenBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5838f0d9");
        setContentView(R.layout.activity_search);
        ButterKnife.inject(this);
        //获取操作类的对象
        greenBeanDao = MyApplication.getDaoInstant().getGreenBeanDao();

        getData();
        initView();
    }

    private void initView() {
        if (list !=null && list.size()>0){
            removeSearchRecord.setVisibility(View.VISIBLE);
        }else{
            removeSearchRecord.setVisibility(View.GONE);

        }
    }

    //每次进来先获取数据 看数据库中有没有数据
    private void getData() {
        if(greenBeanDao!=null) {
            List<GreenBean> greenBeen = greenBeanDao.loadAll();
            for (int i = 0; i < greenBeen.size(); i++) {
                String name = greenBeen.get(i).getName();
                list.add(name);
            }
            adapter = new SearchAdapetr(this,list);
            lvSearch.setAdapter(adapter);

        }
    }


    @OnClick({R.id.tv_message_home, R.id.remove_search_record,R.id.iv_voice})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_message_home:
                if(TextUtils.isEmpty(tvSearchHome.getText())) {
                    Toast.makeText(SearchActivity.this, "请输入搜索内容", Toast.LENGTH_SHORT).show();
                }else {
                    //添加到数据库中
                    greenBeanDao.insert(new GreenBean(null,tvSearchHome.getText()+""));
                    //把输入的数据拿过来设置适配器显示数据
                    text = tvSearchHome.getText().toString();
                    list.add(text);
                    adapter = new SearchAdapetr(this, list);
                    lvSearch.setAdapter(adapter);
                    //设置点击事件
                    lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            String s = list.get(position);
                            tvSearchHome.setText(s);
                        }
                    });
                    //再进行跳转
                    startActivity(new Intent(this, GoodsListActivity.class));
                }
//                gotoSearch();
                break;
            case R.id.remove_search_record:
                //先从数据库中移除
                greenBeanDao.deleteAll();
                //移除里面所有数据
                list.removeAll(list);
                adapter.notifyDataSetChanged();
                break;
            case R.id.iv_voice:
                showDialogVoice();
                break;
        }
        if (list !=null && list.size()>0){
            removeSearchRecord.setVisibility(View.VISIBLE);
        }else{
            removeSearchRecord.setVisibility(View.GONE);

        }
    }

//    private void gotoSearch() {
//        String word = tvSearchHome.getText().toString().trim();
//        try {
//            word = URLEncoder.encode(word,"UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        if (!TextUtils.isEmpty(word)) {
//
//            String url = Constants.SKIRT_URL;
////            Log.e("TAG","url=="+word);
//            getDataFromNet(url);
//
//
//        } else {
//            //请输入关键字
//            Toast.makeText(this, "请输入关键字", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void showDialogVoice() {
        //1.创建RecognizerDialog对象
        RecognizerDialog mDialog = new RecognizerDialog(this, new MyInitListener());
        //2.设置accent、 language等参数
        mDialog.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mDialog.setParameter(SpeechConstant.ACCENT, "mandarin");
        //若要将UI控件用于语义理解，必须添加以下参数设置，设置之后onResult回调返回将是语义理解
        //结果
        // mDialog.setParameter("asr_sch", "1");
        // mDialog.setParameter("nlp_version", "2.0");
        //3.设置回调接口
        mDialog.setListener(new MyRecognizerDialogListener());
        //4.显示dialog，接收语音输入
        mDialog.show();
    }

    class MyRecognizerDialogListener implements RecognizerDialogListener {

        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            String result = recognizerResult.getResultString();
            System.out.println(result);
            String text = JsonParser.parseIatResult(recognizerResult.getResultString());

            String sn = null;
            // 读取json结果中的sn字段
            try {
                JSONObject resultJson = new JSONObject(recognizerResult.getResultString());
                sn = resultJson.optString("sn");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            mIatResults.put(sn, text);

            StringBuffer resultBuffer = new StringBuffer();
            for (String key : mIatResults.keySet()) {
                resultBuffer.append(mIatResults.get(key));
            }
            String reulst = resultBuffer.toString();
            reulst = reulst.replace("。","");
            tvSearchHome.setText(reulst);
            tvSearchHome.setSelection(tvSearchHome.length());

        }

        @Override
        public void onError(SpeechError speechError) {

            Toast.makeText(SearchActivity.this, "出错了哦", Toast.LENGTH_SHORT).show();
        }
    }

    class MyInitListener implements InitListener {

        @Override
        public void onInit(int i) {


        }
    }
    private void getDataFromNet(String url) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(String response, int id) {
//                processData(response);
            }
        });
    }
    private void processData(String result) {
//        SearchBean searchBean = new Gson().fromJson(result,SearchBean.class);
//        List<SearchBean.ItemsBean> items =  searchBean.getItems();
//        if(items != null && items.size() >0){
//            SearchAdapter searchAdapter = new SearchAdapter(SearchActivity.this,items);
//            listview.setAdapter(searchAdapter);
//        }
//    }
    }
}
