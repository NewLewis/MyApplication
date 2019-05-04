package com.example.rui12.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.ShowPostActivity;
import com.example.rui12.myapplication.model.CollectModel;
import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.model.LaudModel;
import com.example.rui12.myapplication.model.UserModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

//import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class DreamPostAdapter extends RecyclerView.Adapter<DreamPostAdapter.NIHolder> implements View.OnClickListener{
    private LayoutInflater mInflater;
    private List<DreamModel> dreamModelList;
    private OnItemClickListener mOnItemClickListener = null;
    private Context context;
    private CommonUtils commonUtils = new CommonUtils();

    public DreamPostAdapter(Context context, List<DreamModel> dreamModelList, int showStyle) {
        super();
        this.dreamModelList = dreamModelList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getItemCount() {
        return dreamModelList.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view,ViewName VIEW,int position);
        void onItemLongClick(int position);
    }

    //用一个枚举类型来表示不同的view
    public enum ViewName{
        ITEM,
        HEADER,
        COLLECT,
        LIKE
    }

    @Override
    public void onClick(View v){
        int position = (int)v.getTag();
        switch (v.getId()){
            case R.id.civ_header:
                //这是头像的点击事件
                mOnItemClickListener.onItemClick(v,ViewName.HEADER,position);
                break;
            case R.id.ib_collect:
                //收藏按钮的点击事件
                mOnItemClickListener.onItemClick(v,ViewName.COLLECT,position);
                break;
            case R.id.ib_like:
                //点赞按钮的点击事件
                mOnItemClickListener.onItemClick(v,ViewName.LIKE,position);
                break;
            default:
                //默认是整个item的点击事件
                mOnItemClickListener.onItemClick(v,ViewName.ITEM,position);
                break;
        }
    }

    @NonNull
    @Override
    public NIHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        final NIHolder niHolder = new NIHolder(mInflater.inflate(R.layout.item_dream_post, parent, false));
        return niHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final DreamPostAdapter.NIHolder holder, int position) {
        holder.user_name.setText(dreamModelList.get(position).getUser());
        holder.dream_title.setText(dreamModelList.get(position).getTitle());
        holder.dream_content.setText((dreamModelList.get(position).getContent()));
        holder.post_time.setText(dreamModelList.get(position).getCreatedAt());

        int num_like = dreamModelList.get(position).getNum_of_laud();
        int num_review = dreamModelList.get(position).getNum_of_review();
        if(num_like < 1000){
            holder.num_like.setText("" + num_like);
        }else{
            int t1 = num_like / 100;
            double t2 = (double)t1 / (double)10;
            holder.num_like.setText("" + t2 + "k");
        }
        if(num_review < 1000){
            holder.num_review.setText("" + num_review);
        }else{
            int t1 = num_review / 100;
            double t2 = (double)t1 / (double)10;
            holder.num_review.setText("" + t2 + "k");
        }

        BmobQuery<UserModel> query = new BmobQuery<>();
        query.addWhereEqualTo("username",dreamModelList.get(position).getUser()).findObjects(new FindListener<UserModel>() {
            @Override
            public void done(List<UserModel> list, BmobException e) {
                if(e == null){
                    Picasso
                            .with(context)
                            .load(list.get(0).getAvatar())
                            .placeholder(R.drawable.bk_gray)
                            .into(holder.header);

                }else{
                    Log.d("Square","加载头像失败");
                }
            }
        });

        if(!dreamModelList.get(position).getImages().isEmpty()){
            holder.first_image.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(dreamModelList.get(position).getImages().get(0))
                    .placeholder(R.drawable.bk_gray)
                    .into(holder.first_image);
        }else{
            holder.first_image.setVisibility(View.GONE);
        }

        SharedPreferences local_user = context.getSharedPreferences("local_user",0);
        String username = local_user.getString("username",null);

        //查询是否已经被点赞
        BmobQuery<LaudModel> bmobQuery = new BmobQuery<>();
        bmobQuery.addWhereEqualTo("username",username).addWhereEqualTo("dreamID",dreamModelList.get(position).getObjectId()).findObjects(new FindListener<LaudModel>() {
            @Override
            public void done(List<LaudModel> list, BmobException e) {
                if(!list.isEmpty()){
                    holder.ib_like.setBackground(commonUtils.toDrawable((Activity) context,R.drawable.like_orange));
                }else{
                    holder.ib_like.setBackground(commonUtils.toDrawable((Activity) context,R.drawable.like_black));
                }
            }
        });

        BmobQuery<CollectModel> bmobQuery1 = new BmobQuery<>();
        bmobQuery1.addWhereEqualTo("username",username).addWhereEqualTo("dreamID",dreamModelList.get(position).getObjectId()).findObjects(new FindListener<CollectModel>() {
            @Override
            public void done(List<CollectModel> list, BmobException e) {
                if(!list.isEmpty()){
                    holder.ib_collect.setBackground(commonUtils.toDrawable((Activity) context,R.drawable.collect_orange));
                }else{
                    holder.ib_collect.setBackground(commonUtils.toDrawable((Activity) context,R.drawable.collect_black));
                }
            }
        });

        ///设置点击事件需要用到的view的tag
        holder.header.setTag(position);
        holder.itemView.setTag(position);
        holder.ib_like.setTag(position);
        holder.ib_collect.setTag(position);
    }

    public void remove(int position){
        dreamModelList.remove(position);
        notifyItemRemoved(position);
    }

    public void setmOnItemClickListener(OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    class NIHolder extends RecyclerView.ViewHolder {
        private TextView user_name;
        private TextView dream_title;
        private TextView dream_content;
        private TextView post_time;
        private CircleImageView header;
        private TextView num_like;
        private TextView num_review;
        private ImageView first_image;
        private ImageButton ib_collect;
        private ImageButton ib_like;

        NIHolder(View itemView) {
            super(itemView);
            user_name = itemView.findViewById(R.id.user_name);
            dream_title = itemView.findViewById(R.id.tv_title);
            dream_content = itemView.findViewById(R.id.tv_content);
            post_time = itemView.findViewById(R.id.post_time);
            header = itemView.findViewById(R.id.civ_header);
            num_like = itemView.findViewById(R.id.tv_like);
            num_review = itemView.findViewById(R.id.tv_review);
            first_image = itemView.findViewById(R.id.iv_first_image);
            ib_collect = itemView.findViewById(R.id.ib_collect);
            ib_like = itemView.findViewById(R.id.ib_like);

            setOnClickListener();
        }

        //设置子view的点击事件
        void setOnClickListener(){
            itemView.setOnClickListener(DreamPostAdapter.this);
            header.setOnClickListener(DreamPostAdapter.this);
            ib_collect.setOnClickListener(DreamPostAdapter.this);
            ib_like.setOnClickListener(DreamPostAdapter.this);
        }
    }
}