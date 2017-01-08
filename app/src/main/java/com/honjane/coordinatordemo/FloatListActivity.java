package com.honjane.coordinatordemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by honjane on 2017/1/8.
 */

public class FloatListActivity extends Activity implements AbsListView.OnScrollListener {
    private ArrayList<String> listData;
    private ListView listView;
    private MyAdapter adapter;
    private View headerPicView;
    private View hideView;
    private View headerTv;
    private View realTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_float);
        initView();
    }

    private void initView() {

        headerPicView = LayoutInflater.from(this).inflate(R.layout.layout_header, null);
        hideView = findViewById(R.id.secondLayout);
        headerTv = findViewById(R.id.header_tv);
        realTab = headerPicView.findViewById(R.id.inc_tab);

        listData = new ArrayList<>(30);

        for (int i = 0; i < 30; i++) {
            listData.add("item" + i);
        }

        adapter = new MyAdapter();

        listView = (ListView) findViewById(R.id.listView);
        //按顺序添加1个view，先添加的在上面。
        listView.addHeaderView(headerPicView);

        listView.setAdapter(adapter);

        //listView监听滑动事件，很重要，因为可以根据滑动的位置
        //决定是否要加载隐藏的视图hideView
        listView.setOnScrollListener(this);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {

    }


    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (hideView.getHeight() + headerTv.getHeight() > headerPicView.getBottom()) {
            hideView.setVisibility(View.VISIBLE);
            realTab.setVisibility(View.INVISIBLE);
            headerTv.setBackgroundColor(getResources().getColor(android.R.color.white));
        } else {
            hideView.setVisibility(View.INVISIBLE);
            realTab.setVisibility(View.VISIBLE);
            headerTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        }
//        if (firstVisibleItem >= 1) {
//            hideView.setVisibility(View.VISIBLE);
//        } else //说明secondLaout没有被屏幕挡住，那就把hideView给收起来。
//        {
//            hideView.setVisibility(View.INVISIBLE);
//
//        }


    }


    private class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return listData.size();
        }

        @Override
        public String getItem(int i) {

            return listData.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }


        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder viewHolder = null;
            if (view == null) {
                view = LayoutInflater.from(FloatListActivity.this).inflate(R.layout.layout_item, null);
                viewHolder = new ViewHolder();
                viewHolder.initView(view);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            String item = getItem(i);
            if (item == null) {
                return view;
            }

            viewHolder.updateView(item);
            return view;
        }

        class ViewHolder {
            TextView textView;

            void initView(View view) {
                textView = (TextView) view.findViewById(R.id.item_tv);
            }

            void updateView(String item) {
                textView.setText(item);
            }

        }
    }


}


