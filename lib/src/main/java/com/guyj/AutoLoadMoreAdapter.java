package com.guyj;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;

import com.guyj.base.ItemViewDelegate;
import com.guyj.base.ViewHolder;
import com.guyj.listener.EasyOnLoadMoreListener;

import java.lang.ref.WeakReference;
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
    private int lastItemCount;
    private int advanceCount = 1;//自动加载的提前量
    private EasyOnLoadMoreListener mEasyOnLoadMoreListener;

    private WeakHandler mWeakHandler= new WeakHandler(this);
    static class WeakHandler extends Handler
    {
        WeakReference<AutoLoadMoreAdapter> mWeakReference;
        public WeakHandler(AutoLoadMoreAdapter moreAdapter)
        {
            mWeakReference=new WeakReference<AutoLoadMoreAdapter>(moreAdapter);
        }
        @Override
        public void handleMessage(Message msg)
        {
            AutoLoadMoreAdapter moreAdapter=mWeakReference.get();
            if (moreAdapter != null && moreAdapter.getEasyOnLoadMoreListener() != null)
            {
                moreAdapter.getEasyOnLoadMoreListener().onLoadMore();
            }
        }
    }

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
                return mLayoutId;
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

    public void setLayoutId(int layoutId) {
        this.mLayoutId = layoutId;
        notifyDataSetChanged();
    }

    public AutoLoadMoreAdapter(final Context context, List<T> datas)
    {
        super(context, datas);
        mContext = context;
        mDatas = datas;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if (position+1>= AutoLoadMoreAdapter.this.getItemCount()-advanceCount){
            /**
             * lastItemCount为了让loadMore只在一个itemCount时加载一次
             * 但是网络异常 加载不小心失败后 怎么办？开放一个重置lastItemCount的方法手动调用
             */
            if (lastItemCount!=AutoLoadMoreAdapter.this.getItemCount()){
                lastItemCount=AutoLoadMoreAdapter.this.getItemCount();
                mWeakHandler.sendEmptyMessage(0);
            }
        }
    }

    /**
     * 重置加载更多的状态，在网络加载异常时或者有必要时调用
     */
    public AutoLoadMoreAdapter resetLoadMoreState(){
        lastItemCount=0;
        return this;
    }

    /**
     * 设置加载提前量，默认1
     * @param advanceCount
     */
    public AutoLoadMoreAdapter setAdvanceCount(int advanceCount){
        this.advanceCount=advanceCount;
        return this;
    }

    protected abstract void convert(ViewHolder holder, T t, int position);

    public EasyOnLoadMoreListener getEasyOnLoadMoreListener() {
        return mEasyOnLoadMoreListener;
    }

    public void setEasyOnLoadMoreListener(EasyOnLoadMoreListener mEasyOnLoadMoreListener) {
        this.mEasyOnLoadMoreListener = mEasyOnLoadMoreListener;
    }

}
