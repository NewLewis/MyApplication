package com.example.rui12.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.PostModel;
import com.example.rui12.myapplication.utils.CommonUtils;

import java.util.List;

public class SelfPostAdapter extends RecyclerView.Adapter<SelfPostAdapter.ViewHolder> implements View.OnClickListener{
    private Context context;
    private OnItemClickListener mOnItemClickListener;
    private List<PostModel> postModelList;
    private LayoutInflater mInflater;

    public SelfPostAdapter(Context context, List<PostModel> postModelList) {
        super();
        this.context = context;
        this.postModelList = postModelList;
        this.mInflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public SelfPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SelfPostAdapter.ViewHolder(mInflater.inflate(R.layout.item_mypost,viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    //用一个枚举类型来表示不同的view
    public enum ViewName{
        ITEM
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, SelfPostAdapter.ViewName VIEW, int position);
        void onItemLongClick(int position);
    }

    public void setmOnItemClickListener(SelfPostAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull final SelfPostAdapter.ViewHolder viewHolder, int position) {
        viewHolder.dream_name.setText(postModelList.get(position).getDream_name());
        //根据status设置显示样式
        if(postModelList.get(position).getDream_status() == (new PostModel()).Success){
            viewHolder.dream_status.setText("成功");
            viewHolder.dream_status.setTextColor((new CommonUtils()).colorToInt((Activity) context,R.color.green_teal));
            viewHolder.circle.setBackground((new CommonUtils()).toDrawable((Activity)context,R.drawable.circle_green));
        }else if(postModelList.get(position).getDream_status() == (new PostModel()).Fail){
            viewHolder.dream_status.setText("失败");
            viewHolder.dream_status.setTextColor((new CommonUtils()).colorToInt((Activity)context,R.color.red));
            viewHolder.circle.setBackground((new CommonUtils()).toDrawable((Activity)context,R.drawable.circle_red));
        }else{
            viewHolder.dream_status.setText("在路上");
            viewHolder.dream_status.setTextColor((new CommonUtils()).colorToInt((Activity)context,R.color.colorOrange));
            viewHolder.circle.setBackground((new CommonUtils()).toDrawable((Activity)context,R.drawable.circle_orange));
        }
        //设置发布时间
        viewHolder.publish_time.setText("发布时间：2018-02-17");
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView dream_name;
        private TextView publish_time;
        private ImageView circle;
        private TextView dream_status;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            dream_name = itemView.findViewById(R.id.dream_name);
            publish_time = itemView.findViewById(R.id.publish_time);
            circle = itemView.findViewById(R.id.circle);
            dream_status = itemView.findViewById(R.id.dream_status);

            itemView.setOnClickListener(SelfPostAdapter.this);
        }
    }

    @Override
    public void onClick(View v){

    }
}
