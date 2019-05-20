package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.ReviewModel;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> implements View.OnClickListener {

    private Context context;
    private List<ReviewModel> reviewModelList;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener = null;

    public ReviewAdapter(Context context, List<ReviewModel> reviewModelList) {
        super();
        this.context = context;
        this.reviewModelList = reviewModelList;
        this.mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ReviewAdapter.ViewHolder(mInflater.inflate(R.layout.item_review,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.username.setText(reviewModelList.get(i).getUsername());
        viewHolder.content.setText(reviewModelList.get(i).getContent());
        viewHolder.num_zan.setText(String.valueOf(reviewModelList.get(i).getNum_of_laud()));

        if(reviewModelList.get(i).getToSb().isEmpty()){
            viewHolder.toOther.setVisibility(View.GONE);
        }else{
            viewHolder.toOther.setVisibility(View.VISIBLE);
            viewHolder.toOther.setText("@" + reviewModelList.get(i).getToSb());
        }

        Picasso
                .with(context)
                .load("http://img1.imgtn.bdimg.com/it/u=281710409,2677816040&fm=26&gp=0.jpg")
                .placeholder(R.drawable.bk_gray)
                .into(viewHolder.header);

        //设置tag
        viewHolder.review.setTag(i);
    }

    @Override
    public int getItemCount() {
        return reviewModelList.size();
    }

    public enum ViewName{
        ITEM,
        REVIEW
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, ReviewAdapter.ViewName VIEW, int position);
        void onItemLongClick(int position);
    }

    public void setmOnItemClickListener(ReviewAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView username;
        private TextView content;
        private TextView num_zan;
        private CircleImageView header;
        private TextView toOther;
        private ImageButton review;
        private ImageButton ib_zan;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.tv_username);
            content = itemView.findViewById(R.id.tv_content);
            header = itemView.findViewById(R.id.civ_header);
            num_zan = itemView.findViewById(R.id.tv_like);

            toOther = itemView.findViewById(R.id.tv_other);
            review = itemView.findViewById(R.id.ib_review);
            ib_zan = itemView.findViewById(R.id.ib_like);

            setOnClickListener();
        }

        void setOnClickListener(){
            itemView.setOnClickListener(ReviewAdapter.this);
            username.setOnClickListener(ReviewAdapter.this);
            header.setOnClickListener(ReviewAdapter.this);
            toOther.setOnClickListener(ReviewAdapter.this);
            review.setOnClickListener(ReviewAdapter.this);
            ib_zan.setOnClickListener(ReviewAdapter.this);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int)v.getTag();
        switch (v.getId()){
            case R.id.ib_review:
                //这是头像的点击事件
                mOnItemClickListener.onItemClick(v, ViewName.REVIEW,position);
                break;

            default:
                //默认是整个item的点击事件
                mOnItemClickListener.onItemClick(v, ViewName.ITEM,position);
                break;
        }
    }
}
