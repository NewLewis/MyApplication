package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.MessageModel;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<RecyclerViewMessageAdapter.ViewHolder> implements View.OnClickListener{

    private LayoutInflater mInflater;
    private List<MessageModel> messageModelList;
    private Context context;
    private OnItemClickListener mOnItemClickListener = null;

    public RecyclerViewMessageAdapter(Context context, List<MessageModel> messageModelList) {
        super();
        this.context = context;
        this.messageModelList = messageModelList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewMessageAdapter.ViewHolder(mInflater.inflate(R.layout.item_message,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        //设置username
        viewHolder.user_name.setText(messageModelList.get(i).getUser_name());
        //设置头像
        Picasso
                .with(context)
                .load(messageModelList.get(i).getHeader_url())
                .placeholder(R.drawable.message)
                .into(viewHolder.circleImageView);
        //显示最新的一条消息
        viewHolder.latest_msg.setText(messageModelList.get(i).getLatest_msg());

        //设置点击事件需要用到的view的tag
        viewHolder.circleImageView.setTag(i);
        viewHolder.itemView.setTag(i);
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public void remove(int position){
        messageModelList.remove(position);
        notifyItemRemoved(position);
    }

    //用一个枚举类型来表示不同的view
    public enum ViewName{
        ITEM,
        HEADER
    }

    public static interface OnItemClickListener {
        void onItemClick(View view,ViewName VIEW,int position);
        void onItemLongClick(int position);
    }

    public void setmOnItemClickListener(RecyclerViewMessageAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView user_name;
        private TextView latest_msg;
        private CircleImageView circleImageView;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            latest_msg = itemView.findViewById(R.id.latest_msg);
            circleImageView = itemView.findViewById(R.id.civ_header);

            //设置子view的点击事件
            setOnClickListener();
        }
        //设置子view的点击事件
        void setOnClickListener(){
            itemView.setOnClickListener(RecyclerViewMessageAdapter.this);
            circleImageView.setOnClickListener(RecyclerViewMessageAdapter.this);
        }
    }
    @Override
    public void onClick(View v){
        int position = (int)v.getTag();
        switch (v.getId()){
            case R.id.civ_header:
                //这是头像的点击事件
                mOnItemClickListener.onItemClick(v,ViewName.HEADER,position);
                break;
            default:
                //默认是整个item的点击事件
                mOnItemClickListener.onItemClick(v,ViewName.ITEM,position);
                break;
        }
    }
}
