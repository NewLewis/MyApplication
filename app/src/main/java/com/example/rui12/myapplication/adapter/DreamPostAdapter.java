package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.model.UserModel;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

//import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class DreamPostAdapter extends RecyclerView.Adapter<DreamPostAdapter.NIHolder>{
    private LayoutInflater mInflater;
    private List<DreamModel> dreamModelList;
    private OnItemClickListener mOnItemClickListener = null;
    private Context context;

    public DreamPostAdapter(Context context, List<DreamModel> dreamModelList, int showStyle) {
        super();
        this.dreamModelList = dreamModelList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getItemCount() {
        return dreamModelList.size();
    }

    @NonNull
    @Override
    public NIHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        final NIHolder niHolder = new NIHolder(mInflater.inflate(R.layout.item_dream_post, parent, false));
        //为item设置点击事件
        niHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d(TAG, "onClick: 绑定了点击事件");
                mOnItemClickListener.onItemClick(niHolder.getAdapterPosition());
            }
        });

        niHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Log.d(TAG, "onLongClick: 绑定了长按事件");
                mOnItemClickListener.onItemLongClick(niHolder.getAdapterPosition() );
                return false;
            }
        });
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
                            .placeholder(R.drawable.bg2)
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
                    .placeholder(R.drawable.bg2)
                    .into(holder.first_image);
        }else{
            holder.first_image.setVisibility(View.GONE);
        }

    }

    public void remove(int position){
        dreamModelList.remove(position);
        notifyItemRemoved(position);
    }

    public static interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
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
        }
    }
}