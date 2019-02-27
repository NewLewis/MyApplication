package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.MessageModel;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;


public class RecyclerViewMessageAdapter extends RecyclerView.Adapter<RecyclerViewMessageAdapter.ViewHolder> {

    private LayoutInflater mInflater;
    private List<MessageModel> messageModelList;
    private Context context;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
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
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public void remove(int position){
        messageModelList.remove(position);
        notifyItemRemoved(position);
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
        }
    }
}
