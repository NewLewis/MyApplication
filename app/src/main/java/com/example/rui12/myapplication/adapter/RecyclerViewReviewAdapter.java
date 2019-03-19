package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.ReviewModel;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewReviewAdapter extends RecyclerView.Adapter<RecyclerViewReviewAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ReviewModel> reviewModelList;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener = null;

    public RecyclerViewReviewAdapter(Context context, List<ReviewModel> reviewModelList) {
        super();
        this.context = context;
        this.reviewModelList = reviewModelList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RecyclerViewReviewAdapter.ViewHolder(mInflater.inflate(R.layout.item_review,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.username.setText(reviewModelList.get(i).getUsername());
        viewHolder.content.setText(reviewModelList.get(i).getReviewContent());
        viewHolder.num_zan.setText(String.valueOf(reviewModelList.get(i).getNum_zan()));
        Picasso
                .with(context)
                .load("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1551290161697&di=b712005413d62e65be7c085ac8236573&imgtype=0&src=http%3A%2F%2Fpic.k73.com%2Fup%2Farticle%2F2017%2F0110%2F091942_18858530.jpg")
                .placeholder(R.drawable.bg2)
                .into(viewHolder.header);
    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }

    public enum ViewName{
        ITEM,
        HEADER
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, RecyclerViewReviewAdapter.ViewName VIEW, int position);
        void onItemLongClick(int position);
    }

    public void setmOnItemClickListener(RecyclerViewReviewAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private TextView content;
        private TextView num_zan;
        private CircleImageView header;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username);
            content = itemView.findViewById(R.id.tv_content);
            header = itemView.findViewById(R.id.civ_header);
            num_zan = itemView.findViewById(R.id.tv_like);

            setOnClickListener();
        }

        void setOnClickListener(){
            itemView.setOnClickListener(RecyclerViewReviewAdapter.this);
            username.setOnClickListener(RecyclerViewReviewAdapter.this);
            header.setOnClickListener(RecyclerViewReviewAdapter.this);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
