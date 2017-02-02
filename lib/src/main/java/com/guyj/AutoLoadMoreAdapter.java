package com.guyj;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;

import com.guyj.base.ItemViewDelegate;
import com.guyj.base.ViewHolder;
import com.guyj.listener.EasyOnLoadMoreListener;
import com.guyj.wrapper.LoadMoreWrapper;

import java.util.List;

/**
 * 作　　者: guyj
 * 修改日期: 2017/2/1
 * 描　　述:
 * 备　　注:
 */

public abstract class AutoLoadMoreAdapter<T> extends MultiItemTypeAdapter<T> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private EasyOnLoadMoreListener easyOnLoadMoreListener;
    private int lastItemCount;
    private int advanceCount=4;//自动加载的提前量

    public AutoLoadMoreAdapter(final Context context, final int layoutId, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                AutoLoadMoreAdapter.this.convert(holder, t, position);
            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position>= AutoLoadMoreAdapter.this.getItemCount()-advanceCount){
            /**
             * lastItemCount为了让loadMore只在一个itemCount时加载一次
             * 但是网络异常 加载不小心失败后 怎么办？开放一个重置lastItemCount的方法手动调用
             */
            if (easyOnLoadMoreListener!=null&&lastItemCount!=AutoLoadMoreAdapter.this.getItemCount()){
                lastItemCount=AutoLoadMoreAdapter.this.getItemCount();
                easyOnLoadMoreListener.onLoadMore();
            }
        }
    }

    public void resetLoadMoreState(){
        lastItemCount=0;
    }

    public AutoLoadMoreAdapter setOnLoadMoreListener(EasyOnLoadMoreListener loadMoreListener)
    {
        if (loadMoreListener != null)
        {
            easyOnLoadMoreListener = loadMoreListener;
        }
        return this;
    }

    protected abstract void convert(ViewHolder holder, T t, int position);




}
