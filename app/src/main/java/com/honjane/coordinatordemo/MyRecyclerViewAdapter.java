package com.honjane.coordinatordemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 晓勇 on 2015/7/12 0012.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Integer> datas;
    private Context context;
    private List<Integer> lists;

    public MyRecyclerViewAdapter(Context context, List<Integer> datas) {
        this.datas = datas;
        this.context = context;
        getRandomHeights(datas);
    }


    private void getRandomHeights(List<Integer> datas) {
        lists = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            lists.add((int) (200 + Math.random() * 400));
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


            View  view = LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;


    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        params.height = lists.get(position);//把随机的高度赋予item布局
        holder.itemView.setLayoutParams(params);
        holder.mTextView.setText(position+"");
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {
    TextView mTextView;
    LineTextView mMoreTextView;

    public MyViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.item_tv);
        mMoreTextView = (LineTextView) itemView.findViewById(R.id.ltv_text);

    }
}