package com.guyj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.guyj.base.ItemViewDelegate;
import com.guyj.base.ItemViewDelegateManager;
import com.guyj.base.ViewHolder;
import com.guyj.listener.EasyOnItemChildCheckChangeListener;
import com.guyj.listener.EasyOnItemChildClickListener;
import com.guyj.listener.EasyOnItemChildLongClickListener;
import com.guyj.listener.EasyOnItemChildTouchListener;

import java.util.List;

/**
 * Created by zhy on 16/4/9.
 */
public class MultiItemTypeAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;

    protected ItemViewDelegateManager mItemViewDelegateManager;
    protected EasyOnItemChildClickListener easyOnItemChildClickListener;
    protected EasyOnItemChildLongClickListener easyOnItemChildLongClickListener;
    protected EasyOnItemChildTouchListener easyOnItemChildTouchListener;
    protected EasyOnItemChildCheckChangeListener easyOnItemChildCheckChangeListener;
//    protected OnItemClickListener mOnItemClickListener;


    public MultiItemTypeAdapter(Context context, List<T> datas) {
        mContext = context;
        mDatas = datas;
        mItemViewDelegateManager = new ItemViewDelegateManager();
    }

    @Override
    public int getItemViewType(int position) {
        if (!useItemViewDelegateManager()) return super.getItemViewType(position);
        return mItemViewDelegateManager.getItemViewType(mDatas.get(position), position);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewDelegate itemViewDelegate = mItemViewDelegateManager.getItemViewDelegate(viewType);
        int layoutId = itemViewDelegate.getItemViewLayoutId();
        ViewHolder holder = ViewHolder.createViewHolder(mContext, parent, layoutId);
        onViewHolderCreated(holder,holder.getConvertView());
//        setListener(parent, holder, viewType);
        holder.setEasyOnItemChildClickListener(easyOnItemChildClickListener);
        holder.setEasyOnItemChildLongClickListener(easyOnItemChildLongClickListener);
        holder.setEasyOnItemChildTouchListener(easyOnItemChildTouchListener);
        holder.setEasyOnItemChildCheckChangeListener(easyOnItemChildCheckChangeListener);
        return holder;
    }

    public void onViewHolderCreated(ViewHolder holder,View itemView){

    }

    public void convert(ViewHolder holder, T t) {
        mItemViewDelegateManager.convert(holder, t, holder.getAdapterPosition());
    }

    protected boolean isEnabled(int viewType) {
        return true;
    }


//    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
//        if (!isEnabled(viewType)) return;
//        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = viewHolder.getAdapterPosition();
//                    mOnItemClickListener.onItemClick(v, viewHolder , position);
//                }
//            }
//        });
//
//        viewHolder.getConvertView().setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View v) {
//                if (mOnItemClickListener != null) {
//                    int position = viewHolder.getAdapterPosition();
//                    return mOnItemClickListener.onItemLongClick(v, viewHolder, position);
//                }
//                return false;
//            }
//        });
//    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        convert(holder, mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        if (null==mDatas){
            throw new RuntimeException("列表数据为null，集合未初始化");
        }else{
            int itemCount = mDatas.size();
            return itemCount;
        }
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDatas(List<T> list){
        mDatas=list;
        notifyDataSetChanged();
    }

    public MultiItemTypeAdapter addItemViewDelegate(ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(itemViewDelegate);
        return this;
    }

    public MultiItemTypeAdapter addItemViewDelegate(int viewType, ItemViewDelegate<T> itemViewDelegate) {
        mItemViewDelegateManager.addDelegate(viewType, itemViewDelegate);
        return this;
    }

    protected boolean useItemViewDelegateManager() {
        return mItemViewDelegateManager.getItemViewDelegateCount() > 0;
    }

//    public interface OnItemClickListener {
//        void onItemClick(View view, RecyclerView.ViewHolder holder,  int position);
//
//        boolean onItemLongClick(View view, RecyclerView.ViewHolder holder,  int position);
//    }

//    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
//        this.mOnItemClickListener = onItemClickListener;
//    }

    public void setEasyOnItemChildClickListener(EasyOnItemChildClickListener easyOnItemChildClickListener){
        this.easyOnItemChildClickListener=easyOnItemChildClickListener;
    }
    public void setEasyOnItemChildLongClickListener(EasyOnItemChildLongClickListener easyOnItemChildLongClickListener){
        this.easyOnItemChildLongClickListener=easyOnItemChildLongClickListener;
    }
    public void setEasyOnItemChildTouchListener(EasyOnItemChildTouchListener easyOnItemChildTouchListener){
        this.easyOnItemChildTouchListener=easyOnItemChildTouchListener;
    }
    public void setEasyOnItemChildCheckChangeListener(EasyOnItemChildCheckChangeListener easyOnItemChildCheckChangeListener){
        this.easyOnItemChildCheckChangeListener=easyOnItemChildCheckChangeListener;
    }
}