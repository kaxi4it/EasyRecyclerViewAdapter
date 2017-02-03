package com.easy.easyrecyclerviewadapter.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.easy.easyrecyclerviewadapter.delagate.MsgComingItemDelagate;
import com.easy.easyrecyclerviewadapter.delagate.MsgSendItemDelagate;
import com.easy.easyrecyclerviewadapter.delagate.MsgTimeItemDelagate;
import com.guyj.AutoLoadMoreAdapter;
import com.guyj.MultiItemTypeAdapter;
import com.guyj.base.ViewHolder;

import java.util.List;

/**
 * Created by zhy on 15/9/4.
 */
public class ChatAdapterForRv2 extends AutoLoadMoreAdapter<Object>
{
    private Context mContext;
    public ChatAdapterForRv2(Context context, List<Object> datas)
    {
        super(context, datas);
        mContext=context;
        addItemViewDelegate(new MsgSendItemDelagate());
        addItemViewDelegate(new MsgComingItemDelagate());
        addItemViewDelegate(new MsgTimeItemDelagate());
    }

    @Override
    protected void convert(ViewHolder holder, Object o, int position) {
        Log.e("convert","convert");
    }

    @Override
    protected void loadMore() {
        Log.e("mul","loadmore");
        Toast.makeText(mContext,"自动加载更多",Toast.LENGTH_SHORT).show();
    }
}
