package com.example.rui12.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rui12.myapplication.R;
import com.example.rui12.myapplication.model.ConversationModel;
import com.example.rui12.myapplication.model.DreamModel;
import com.example.rui12.myapplication.utils.CommonUtils;
import com.example.rui12.myapplication.view.CircleImageView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ConversationAdapter extends RecyclerView.Adapter<ConversationAdapter.ViewHolder>  {
    private LayoutInflater mInflater;
    private List<ConversationModel> conversationModelList;
    private ConversationAdapter.OnItemClickListener mOnItemClickListener = null;
    private Context context;

    public ConversationAdapter(Context context, List<ConversationModel> conversationModelList) {
        super();
        this.conversationModelList = conversationModelList;
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getItemCount() {
        return conversationModelList.size();
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, DreamPostAdapter.ViewName VIEW, int position);
        void onItemLongClick(int position);
    }

    @NonNull
    @Override
    public ConversationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ConversationAdapter.ViewHolder(mInflater.inflate(R.layout.item_conversation,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final ConversationAdapter.ViewHolder viewHolder, int i) {
        if(conversationModelList.get(i).getType() == 1){
            viewHolder.ll_right.setVisibility(View.GONE);
            viewHolder.ll_left.setVisibility(View.VISIBLE);
            viewHolder.tv_left.setText(conversationModelList.get(i).getContent());
            if(conversationModelList.get(i).getHeader() != null && !conversationModelList.get(i).getHeader().isEmpty()){
                Picasso
                        .with(context)
                        .load(conversationModelList.get(i).getHeader())
                        .placeholder(R.drawable.bk_gray)
                        .into(viewHolder.left_header);
            }
        }else{
            viewHolder.ll_left.setVisibility(View.GONE);
            viewHolder.ll_right.setVisibility(View.VISIBLE);
            viewHolder.tv_right.setText(conversationModelList.get(i).getContent());
        }
    }

    public void setmOnItemClickListener(ConversationAdapter.OnItemClickListener onItemClickListener){
        this.mOnItemClickListener = onItemClickListener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private LinearLayout ll_left;
        private LinearLayout ll_right;
        private CircleImageView left_header;
        private TextView tv_left;
        private TextView tv_right;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            ll_left = itemView.findViewById(R.id.left_layout);
            tv_left = itemView.findViewById(R.id.left_msg);
            left_header = itemView.findViewById(R.id.civ_header);
            ll_right = itemView.findViewById(R.id.right_layout);
            tv_right = itemView.findViewById(R.id.right_msg);
        }
    }
}
